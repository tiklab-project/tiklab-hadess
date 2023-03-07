package io.tiklab.xpack.repository.service;

import io.tiklab.xpack.repository.dao.RepositoryGroupDao;
import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.repository.entity.RepositoryGroupEntity;
import io.tiklab.xpack.repository.model.RepositoryGroup;
import io.tiklab.xpack.repository.model.RepositoryGroupQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
* RepositoryGroupServiceImpl-组合库关联
*/
@Service
public class RepositoryGroupServiceImpl implements RepositoryGroupService {

    @Autowired
    RepositoryGroupDao repositoryGroupDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createRepositoryGroup(@NotNull @Valid RepositoryGroup repositoryGroup) {
        RepositoryGroupEntity repositoryGroupEntity = BeanMapper.map(repositoryGroup, RepositoryGroupEntity.class);
        repositoryGroupEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        repositoryGroupEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return repositoryGroupDao.createRepositoryGroup(repositoryGroupEntity);
    }

    @Override
    public void updateRepositoryGroup(@NotNull @Valid RepositoryGroup repositoryGroup) {
        RepositoryGroupEntity repositoryGroupEntity = BeanMapper.map(repositoryGroup, RepositoryGroupEntity.class);
        repositoryGroupEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        repositoryGroupDao.updateRepositoryGroup(repositoryGroupEntity);
    }

    @Override
    public void deleteRepositoryGroup(@NotNull String id) {
        repositoryGroupDao.deleteRepositoryGroup(id);
    }

    @Override
    public void deleteGroupItemsByRepositoryId(String repositoryId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(RepositoryGroupEntity.class)
                .eq("repositoryId", repositoryId)
                .get();
        repositoryGroupDao.deleteRepositoryGroup(deleteCondition);
    }

    @Override
    public RepositoryGroup findOne(String id) {
        RepositoryGroupEntity repositoryGroupEntity = repositoryGroupDao.findRepositoryGroup(id);

        RepositoryGroup repositoryGroup = BeanMapper.map(repositoryGroupEntity, RepositoryGroup.class);
        return repositoryGroup;
    }

    @Override
    public List<RepositoryGroup> findList(List<String> idList) {
        List<RepositoryGroupEntity> repositoryGroupEntityList =  repositoryGroupDao.findRepositoryGroupList(idList);

        List<RepositoryGroup> repositoryGroupList =  BeanMapper.mapList(repositoryGroupEntityList,RepositoryGroup.class);
        return repositoryGroupList;
    }

    @Override
    public RepositoryGroup findRepositoryGroup(@NotNull String id) {
        RepositoryGroup repositoryGroup = findOne(id);

        joinTemplate.joinQuery(repositoryGroup);

        return repositoryGroup;
    }

    @Override
    public List<RepositoryGroup> findAllRepositoryGroup() {
        List<RepositoryGroupEntity> repositoryGroupEntityList =  repositoryGroupDao.findAllRepositoryGroup();

        List<RepositoryGroup> repositoryGroupList =  BeanMapper.mapList(repositoryGroupEntityList,RepositoryGroup.class);

        joinTemplate.joinQuery(repositoryGroupList);

        return repositoryGroupList;
    }

    @Override
    public List<RepositoryGroup> findRepositoryGroupList(RepositoryGroupQuery repositoryGroupQuery) {
        List<RepositoryGroupEntity> repositoryGroupEntityList = repositoryGroupDao.findRepositoryGroupList(repositoryGroupQuery);

        List<RepositoryGroup> repositoryGroupList = BeanMapper.mapList(repositoryGroupEntityList,RepositoryGroup.class);

        joinTemplate.joinQuery(repositoryGroupList);

        return repositoryGroupList;
    }

    @Override
    public Pagination<RepositoryGroup> findRepositoryGroupPage(RepositoryGroupQuery repositoryGroupQuery) {
        Pagination<RepositoryGroupEntity>  pagination = repositoryGroupDao.findRepositoryGroupPage(repositoryGroupQuery);

        List<RepositoryGroup> repositoryGroupList = BeanMapper.mapList(pagination.getDataList(),RepositoryGroup.class);

        joinTemplate.joinQuery(repositoryGroupList);

        return PaginationBuilder.build(pagination,repositoryGroupList);
    }

    @Override
    public String compileRepositoryGroup(RepositoryGroup repositoryGroup) {
        List<RepositoryGroup> repositoryGroupList = this.findRepositoryGroupList(new RepositoryGroupQuery().
                setRepositoryGroupId(repositoryGroup.getRepositoryGroup().getId())
                .setRepositoryId(repositoryGroup.getRepository().getId()));
        if (CollectionUtils.isEmpty(repositoryGroupList)){
            return  this.createRepositoryGroup(repositoryGroup);
        }
        return null;
    }
}