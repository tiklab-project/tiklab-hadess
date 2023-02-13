package net.tiklab.xpack.repository.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import net.tiklab.join.JoinTemplate;
import net.tiklab.xpack.repository.dao.RepositoryGroupItemsDao;
import net.tiklab.xpack.repository.entity.RepositoryGroupItemsEntity;
import net.tiklab.xpack.repository.model.RepositoryGroupItems;
import net.tiklab.xpack.repository.model.RepositoryGroupItemsQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
* RepositoryGroupItemsServiceImpl
*/
@Service
public class RepositoryGroupItemsServiceImpl implements RepositoryGroupItemsService {

    @Autowired
    RepositoryGroupItemsDao repositoryGroupItemsDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createRepositoryGroupItems(@NotNull @Valid RepositoryGroupItems repositoryGroupItems) {
        RepositoryGroupItemsEntity repositoryGroupItemsEntity = BeanMapper.map(repositoryGroupItems, RepositoryGroupItemsEntity.class);
        repositoryGroupItemsEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        repositoryGroupItemsEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return repositoryGroupItemsDao.createRepositoryGroupItems(repositoryGroupItemsEntity);
    }

    @Override
    public void updateRepositoryGroupItems(@NotNull @Valid RepositoryGroupItems repositoryGroupItems) {
        RepositoryGroupItemsEntity repositoryGroupItemsEntity = BeanMapper.map(repositoryGroupItems, RepositoryGroupItemsEntity.class);
        repositoryGroupItemsEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        repositoryGroupItemsDao.updateRepositoryGroupItems(repositoryGroupItemsEntity);
    }

    @Override
    public void deleteRepositoryGroupItems(@NotNull String id) {
        repositoryGroupItemsDao.deleteRepositoryGroupItems(id);
    }

    @Override
    public void deleteGroupItemsByRepositoryId(String repositoryId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(RepositoryGroupItemsEntity.class)
                .eq("repositoryId", repositoryId)
                .get();
        repositoryGroupItemsDao.deleteRepositoryGroupItems(deleteCondition);
    }

    @Override
    public RepositoryGroupItems findOne(String id) {
        RepositoryGroupItemsEntity repositoryGroupItemsEntity = repositoryGroupItemsDao.findRepositoryGroupItems(id);

        RepositoryGroupItems repositoryGroupItems = BeanMapper.map(repositoryGroupItemsEntity, RepositoryGroupItems.class);
        return repositoryGroupItems;
    }

    @Override
    public List<RepositoryGroupItems> findList(List<String> idList) {
        List<RepositoryGroupItemsEntity> repositoryGroupItemsEntityList =  repositoryGroupItemsDao.findRepositoryGroupItemsList(idList);

        List<RepositoryGroupItems> repositoryGroupItemsList =  BeanMapper.mapList(repositoryGroupItemsEntityList,RepositoryGroupItems.class);
        return repositoryGroupItemsList;
    }

    @Override
    public RepositoryGroupItems findRepositoryGroupItems(@NotNull String id) {
        RepositoryGroupItems repositoryGroupItems = findOne(id);

        joinTemplate.joinQuery(repositoryGroupItems);

        return repositoryGroupItems;
    }

    @Override
    public List<RepositoryGroupItems> findAllRepositoryGroupItems() {
        List<RepositoryGroupItemsEntity> repositoryGroupItemsEntityList =  repositoryGroupItemsDao.findAllRepositoryGroupItems();

        List<RepositoryGroupItems> repositoryGroupItemsList =  BeanMapper.mapList(repositoryGroupItemsEntityList,RepositoryGroupItems.class);

        joinTemplate.joinQuery(repositoryGroupItemsList);

        return repositoryGroupItemsList;
    }

    @Override
    public List<RepositoryGroupItems> findRepositoryGroupItemsList(RepositoryGroupItemsQuery repositoryGroupItemsQuery) {
        List<RepositoryGroupItemsEntity> repositoryGroupItemsEntityList = repositoryGroupItemsDao.findRepositoryGroupItemsList(repositoryGroupItemsQuery);

        List<RepositoryGroupItems> repositoryGroupItemsList = BeanMapper.mapList(repositoryGroupItemsEntityList,RepositoryGroupItems.class);

        joinTemplate.joinQuery(repositoryGroupItemsList);

        return repositoryGroupItemsList;
    }

    @Override
    public Pagination<RepositoryGroupItems> findRepositoryGroupItemsPage(RepositoryGroupItemsQuery repositoryGroupItemsQuery) {
        Pagination<RepositoryGroupItemsEntity>  pagination = repositoryGroupItemsDao.findRepositoryGroupItemsPage(repositoryGroupItemsQuery);

        List<RepositoryGroupItems> repositoryGroupItemsList = BeanMapper.mapList(pagination.getDataList(),RepositoryGroupItems.class);

        joinTemplate.joinQuery(repositoryGroupItemsList);

        return PaginationBuilder.build(pagination,repositoryGroupItemsList);
    }
}