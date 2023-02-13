package net.tiklab.xpack.repository.dao;

import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.xpack.repository.entity.RepositoryGroupItemsEntity;
import net.tiklab.xpack.repository.model.RepositoryGroupItemsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RepositoryGroupItemsDao
 */
@Repository
public class RepositoryGroupItemsDao{

    private static Logger logger = LoggerFactory.getLogger(RepositoryGroupItemsDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param repositoryGroupItemsEntity
     * @return
     */
    public String createRepositoryGroupItems(RepositoryGroupItemsEntity repositoryGroupItemsEntity) {
        return jpaTemplate.save(repositoryGroupItemsEntity,String.class);
    }

    /**
     * 更新
     * @param repositoryGroupItemsEntity
     */
    public void updateRepositoryGroupItems(RepositoryGroupItemsEntity repositoryGroupItemsEntity){
        jpaTemplate.update(repositoryGroupItemsEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteRepositoryGroupItems(String id){
        jpaTemplate.delete(RepositoryGroupItemsEntity.class,id);
    }

    public void deleteRepositoryGroupItems(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public RepositoryGroupItemsEntity findRepositoryGroupItems(String id){
        return jpaTemplate.findOne(RepositoryGroupItemsEntity.class,id);
    }

    /**
    * findAllRepositoryGroupItems
    * @return
    */
    public List<RepositoryGroupItemsEntity> findAllRepositoryGroupItems() {
        return jpaTemplate.findAll(RepositoryGroupItemsEntity.class);
    }

    public List<RepositoryGroupItemsEntity> findRepositoryGroupItemsList(List<String> idList) {
        return jpaTemplate.findList(RepositoryGroupItemsEntity.class,idList);
    }

    public List<RepositoryGroupItemsEntity> findRepositoryGroupItemsList(RepositoryGroupItemsQuery repositoryGroupItemsQuery) {
        QueryCondition QueryCondition = QueryBuilders.createQuery(RepositoryGroupItemsEntity.class)
                .eq("repositoryGroupId", repositoryGroupItemsQuery.getRepositoryGroupId())
                .orders(repositoryGroupItemsQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(QueryCondition,RepositoryGroupItemsEntity.class);
    }

    public Pagination<RepositoryGroupItemsEntity> findRepositoryGroupItemsPage(RepositoryGroupItemsQuery repositoryGroupItemsQuery) {
        QueryCondition QueryCondition = QueryBuilders.createQuery(RepositoryGroupItemsEntity.class)
                .eq("repositoryGroupId", repositoryGroupItemsQuery.getRepositoryGroupId())
                .orders(repositoryGroupItemsQuery.getOrderParams())
                .pagination(repositoryGroupItemsQuery.getPageParam())
                .get();

        return jpaTemplate.findPage(QueryCondition,RepositoryGroupItemsEntity.class);
    }
}