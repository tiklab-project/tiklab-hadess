package io.thoughtware.hadess.repository.service;

import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.hadess.repository.model.RepositoryGroup;
import io.thoughtware.hadess.repository.model.RepositoryGroupQuery;
import io.thoughtware.hadess.repository.dao.RepositoryGroupDao;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.join.JoinTemplate;
import io.thoughtware.hadess.repository.entity.RepositoryGroupEntity;

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
    public void deleteGroupItemByCondition(String field, String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(RepositoryGroupEntity.class)
                .eq(field, value)
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
    public void compileRepositoryGroup(RepositoryGroupQuery repositoryGroupQuery) {
        List<RepositoryGroup> repositoryGroupList = this.findRepositoryGroupList(new RepositoryGroupQuery().
                setRepositoryGroupId(repositoryGroupQuery.getRepositoryGroupId()));

        if (CollectionUtils.isNotEmpty(repositoryGroupList)){
            DeleteCondition deleteCondition = DeleteBuilders.createDelete(RepositoryGroupEntity.class)
                    .eq("repositoryGroupId", repositoryGroupQuery.getRepositoryGroupId())
                    .get();
            repositoryGroupDao.deleteRepositoryGroup(deleteCondition);
        }
        List<Repository> repositoryList = repositoryGroupQuery.getRepositoryList();
        for (Repository repository:repositoryList){
            RepositoryGroup repositoryGroup = new RepositoryGroup();
            Repository groupRepository = new Repository();
            groupRepository.setId(repositoryGroupQuery.getRepositoryGroupId());
            repositoryGroup.setRepositoryGroup(groupRepository);
            repositoryGroup.setRepository(repository);
            this.createRepositoryGroup(repositoryGroup);
        }
    }
}