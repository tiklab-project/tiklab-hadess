package io.thoughtware.hadess.pushcentral.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;

import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;

import io.thoughtware.hadess.pushcentral.dao.PushGroupDao;
import io.thoughtware.hadess.pushcentral.entity.PushGroupEntity;
import io.thoughtware.hadess.pushcentral.entity.PushLibraryEntity;
import io.thoughtware.hadess.pushcentral.model.PushGroup;
import io.thoughtware.hadess.pushcentral.model.PushGroupQuery;
import io.thoughtware.hadess.pushcentral.model.PushLibrary;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.toolkit.join.JoinTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
* PushGroupServiceImpl-推送组
*/
@Service
public class PushGroupServiceImpl implements PushGroupService {

    @Autowired
    PushGroupDao pushGroupDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PushLibraryService pushLibraryService;


    @Override
    public String createPushGroup(@NotNull @Valid PushGroup pushGroup) {
        PushGroupEntity pushGroupEntity = BeanMapper.map(pushGroup, PushGroupEntity.class);
        pushGroupEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return pushGroupDao.createPushGroup(pushGroupEntity);
    }

    @Override
    public void updatePushGroup(@NotNull @Valid PushGroup pushGroup) {
        PushGroupEntity pushGroupEntity = BeanMapper.map(pushGroup, PushGroupEntity.class);

        pushGroupDao.updatePushGroup(pushGroupEntity);
    }

    @Override
    public void deletePushGroup(@NotNull String id) {
        pushGroupDao.deletePushGroup(id);

        Thread thread = new Thread() {
            public void run() {
                pushLibraryService.deleteVersionByCondition("pushGroupId",id);
            }};
        thread.start();
    }

    @Override
    public void deleteVersionByCondition(String field, String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(PushLibraryEntity.class)
                .eq(field, value)
                .get();
        pushGroupDao.deletePushGroup(deleteCondition);


    }

    @Override
    public PushGroup findOne(String id) {
        PushGroupEntity pushGroupEntity = pushGroupDao.findPushGroup(id);

        PushGroup pushGroup = BeanMapper.map(pushGroupEntity, PushGroup.class);
        return pushGroup;
    }

    @Override
    public List<PushGroup> findList(List<String> idList) {
        List<PushGroupEntity> pushGroupEntityList =  pushGroupDao.findPushGroupList(idList);

        List<PushGroup> pushGroupList =  BeanMapper.mapList(pushGroupEntityList,PushGroup.class);

        return pushGroupList;
    }

    @Override
    public PushGroup findPushGroup(@NotNull String id) {
        PushGroup pushGroup = findOne(id);

        joinTemplate.joinQuery(pushGroup);

        return pushGroup;
    }

    @Override
    public List<PushGroup> findAllPushGroup() {
        List<PushGroupEntity> pushGroupEntityList =  pushGroupDao.findAllPushGroup();

        List<PushGroup> pushGroupList =  BeanMapper.mapList(pushGroupEntityList,PushGroup.class);

        joinTemplate.joinQuery(pushGroupList);

        return pushGroupList;
    }

    @Override
    public List<PushGroup> findPushGroupList(PushGroupQuery pushGroupQuery) {
        List<PushGroupEntity> pushGroupEntityList = pushGroupDao.findPushGroupList(pushGroupQuery);

        List<PushLibrary> allPushLibrary = pushLibraryService.findAllPushLibrary();

        List<PushGroup> pushGroupList = BeanMapper.mapList(pushGroupEntityList,PushGroup.class);


        if (CollectionUtils.isNotEmpty(pushGroupList)){
            //查询推送组下面的推送制品
            for (PushGroup pushGroup:pushGroupList){
                List<PushLibrary> collect = allPushLibrary.stream().filter(a -> pushGroup.getId().equals(a.getPushGroupId())).collect(Collectors.toList());
                int pushNum = CollectionUtils.isNotEmpty(collect) ? collect.size() : 0;
                if (CollectionUtils.isNotEmpty(collect)){
                   // List<PushLibrary> failPushLibrary = collect.stream().filter(a -> ("fail").equals(a.getLastPushResult())).collect(Collectors.toList());

                    //推送时间排序
                    List<PushLibrary> pushLibraries = collect.stream().filter(a-> !ObjectUtils.isEmpty(a.getLastPushTime()))
                            .sorted(Comparator.comparing(PushLibrary::getLastPushTime).reversed()).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(pushLibraries)){
                        pushGroup.setLastPushTime(pushLibraries.get(0).getLastPushTime());
                    }
                }

                pushGroup.setPushLibraryNum(pushNum);


            }
        }
        joinTemplate.joinQuery(pushGroupList);
        return pushGroupList;
    }

    @Override
    public Pagination<PushGroup> findPushGroupPage(PushGroupQuery pushGroupQuery) {
        Pagination<PushGroupEntity>  pagination = pushGroupDao.findPushGroupPage(pushGroupQuery);

        List<PushGroup> pushGroupList = BeanMapper.mapList(pagination.getDataList(),PushGroup.class);

        joinTemplate.joinQuery(pushGroupList);

        return PaginationBuilder.build(pagination,pushGroupList);
    }

}