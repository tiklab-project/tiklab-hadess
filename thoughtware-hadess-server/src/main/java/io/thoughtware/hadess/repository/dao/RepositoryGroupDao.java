package io.thoughtware.hadess.repository.dao;

import io.thoughtware.hadess.repository.entity.RepositoryGroupEntity;
import io.thoughtware.hadess.repository.model.RepositoryGroupQuery;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RepositoryGroupDao-组合库数据访问
 */
@Repository
public class RepositoryGroupDao{

    private static Logger logger = LoggerFactory.getLogger(RepositoryGroupDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param repositoryGroupEntity
     * @return
     */
    public String createRepositoryGroup(RepositoryGroupEntity repositoryGroupEntity) {
        return jpaTemplate.save(repositoryGroupEntity,String.class);
    }

    /**
     * 更新
     * @param repositoryGroupEntity
     */
    public void updateRepositoryGroup(RepositoryGroupEntity repositoryGroupEntity){
        jpaTemplate.update(repositoryGroupEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteRepositoryGroup(String id){
        jpaTemplate.delete(RepositoryGroupEntity.class,id);
    }

    /**
     * 条件删除组合库
     * @param deleteCondition
     */
    public void deleteRepositoryGroup(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public RepositoryGroupEntity findRepositoryGroup(String id){
        return jpaTemplate.findOne(RepositoryGroupEntity.class,id);
    }

    /**
    * 查询所有组合库
    * @return
    */
    public List<RepositoryGroupEntity> findAllRepositoryGroup() {
        return jpaTemplate.findAll(RepositoryGroupEntity.class);
    }

    /**
     * 通过ids查询组合库
     * @param idList
     * @return  List <RepositoryGroupEntity>
     */
    public List<RepositoryGroupEntity> findRepositoryGroupList(List<String> idList) {
        return jpaTemplate.findList(RepositoryGroupEntity.class,idList);
    }

    /**
     * 条件查询组合库
     * @param repositoryGroupQuery
     * @return  List <RepositoryGroupEntity>
     */
    public List<RepositoryGroupEntity> findRepositoryGroupList(RepositoryGroupQuery repositoryGroupQuery) {
        QueryCondition QueryCondition = QueryBuilders.createQuery(RepositoryGroupEntity.class)
                .eq("repositoryGroupId", repositoryGroupQuery.getRepositoryGroupId())
                .eq("repositoryId", repositoryGroupQuery.getRepositoryId())
                .orders(repositoryGroupQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(QueryCondition,RepositoryGroupEntity.class);
    }

    /**
     * 条件分页查询组合库
     * @param repositoryGroupQuery
     * @return  Pagination <RepositoryGroupEntity>
     */
    public Pagination<RepositoryGroupEntity> findRepositoryGroupPage(RepositoryGroupQuery repositoryGroupQuery) {
        QueryCondition QueryCondition = QueryBuilders.createQuery(RepositoryGroupEntity.class)
                .eq("repositoryGroupId", repositoryGroupQuery.getRepositoryGroupId())
                .eq("repositoryId", repositoryGroupQuery.getRepositoryId())
                .orders(repositoryGroupQuery.getOrderParams())
                .pagination(repositoryGroupQuery.getPageParam())
                .get();

        return jpaTemplate.findPage(QueryCondition,RepositoryGroupEntity.class);
    }
}