package io.thoughtware.hadess.repository.dao;

import io.thoughtware.hadess.repository.entity.RepositoryClusterCfgEntity;
import io.thoughtware.hadess.repository.model.RepositoryClusterCfgQuery;
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
 * RepositoryClusterCfgDao-制品库复制信息数据访问
 */
@Repository
public class RepositoryClusterCfgDao{

    private static Logger logger = LoggerFactory.getLogger(RepositoryClusterCfgDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param repositoryClusterCfgEntity
     * @return
     */
    public String createRepositoryClusterCfg(RepositoryClusterCfgEntity repositoryClusterCfgEntity) {
        return jpaTemplate.save(repositoryClusterCfgEntity,String.class);
    }

    /**
     * 更新
     * @param repositoryClusterCfgEntity
     */
    public void updateRepositoryClusterCfg(RepositoryClusterCfgEntity repositoryClusterCfgEntity){
        jpaTemplate.update(repositoryClusterCfgEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteRepositoryClusterCfg(String id){
        jpaTemplate.delete(RepositoryClusterCfgEntity.class,id);
    }

    /**
     * 条件删除制品库复制信息
     * @param deleteCondition
     * @return
     */
    public void deleteRepositoryClusterCfg(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public RepositoryClusterCfgEntity findRepositoryClusterCfg(String id){
        return jpaTemplate.findOne(RepositoryClusterCfgEntity.class,id);
    }

    /**
    * findAllRepositoryClusterCfg
    * @return
    */
    public List<RepositoryClusterCfgEntity> findAllRepositoryClusterCfg() {
        return jpaTemplate.findAll(RepositoryClusterCfgEntity.class);
    }

    /**
     * 通过ids查询制品库复制信息
     * @param idList
     * @return List <RepositoryClusterCfgEntity>
     */
    public List<RepositoryClusterCfgEntity> findRepositoryClusterCfgList(List<String> idList) {
        return jpaTemplate.findList(RepositoryClusterCfgEntity.class,idList);
    }

    /**
     * 条件查询制品库复制信息
     * @param repositoryClusterCfgQuery
     * @return List <RepositoryClusterCfgEntity>
     */
    public List<RepositoryClusterCfgEntity> findRepositoryClusterCfgList(RepositoryClusterCfgQuery repositoryClusterCfgQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryClusterCfgEntity.class)
                .eq("repositoryId",repositoryClusterCfgQuery.getRepositoryId())
                .orders(repositoryClusterCfgQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,RepositoryClusterCfgEntity.class);
    }

    /**
     * 条件分页查询制品库复制信息
     * @param repositoryClusterCfgQuery
     * @return Pagination <RepositoryClusterCfgEntity>
     */
    public Pagination<RepositoryClusterCfgEntity> findRepositoryClusterCfgPage(RepositoryClusterCfgQuery repositoryClusterCfgQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryClusterCfgEntity.class)
                .eq("repositoryId",repositoryClusterCfgQuery.getRepositoryId())
                .orders(repositoryClusterCfgQuery.getOrderParams())
                .pagination(repositoryClusterCfgQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,RepositoryClusterCfgEntity.class);
    }
}