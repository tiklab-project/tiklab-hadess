package io.tiklab.hadess.repository.dao;

import io.tiklab.hadess.repository.entity.RepositoryRemoteProxyEntity;
import io.tiklab.hadess.repository.model.RemoteProxy;
import io.tiklab.hadess.repository.model.RepositoryRemoteProxyQuery;
import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

/**
 * RepositoryRemoteProxyDao-制品远程库代理数据访问
 */
@Repository
public class RepositoryRemoteProxyDao{

    private static Logger logger = LoggerFactory.getLogger(RepositoryRemoteProxyDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param repositoryRemoteProxyEntity
     * @return
     */
    public String createRepositoryRemoteProxy(RepositoryRemoteProxyEntity repositoryRemoteProxyEntity) {
        return jpaTemplate.save(repositoryRemoteProxyEntity,String.class);
    }

    /**
     * 更新
     * @param repositoryRemoteProxyEntity
     */
    public void updateRepositoryRemoteProxy(RepositoryRemoteProxyEntity repositoryRemoteProxyEntity){
        jpaTemplate.update(repositoryRemoteProxyEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteRepositoryRemoteProxy(String id){
        jpaTemplate.delete(RepositoryRemoteProxyEntity.class,id);
    }

    /**
     * 条件删除远程库代理信息
     * @param deleteCondition
     */
    public void deleteRepositoryRemoteProxy(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public RepositoryRemoteProxyEntity findRepositoryRemoteProxy(String id){
        return jpaTemplate.findOne(RepositoryRemoteProxyEntity.class,id);
    }

    /**
    * 查询所有远程库代理信息
    * @return
    */
    public List<RepositoryRemoteProxyEntity> findAllRepositoryRemoteProxy() {
        return jpaTemplate.findAll(RepositoryRemoteProxyEntity.class);
    }

    /**
     * 通过ids查询远程库代理信息
     * @param idList
     * @return List <RepositoryRemoteProxyEntity>
     */
    public List<RepositoryRemoteProxyEntity> findRepositoryRemoteProxyList(List<String> idList) {
        return jpaTemplate.findList(RepositoryRemoteProxyEntity.class,idList);
    }

    /**
     * 条件查询远程库代理信息
     * @param repositoryRemoteProxyQuery
     * @return List <RepositoryRemoteProxyEntity>
     */
    public List<RepositoryRemoteProxyEntity> findRepositoryRemoteProxyList(RepositoryRemoteProxyQuery repositoryRemoteProxyQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryRemoteProxyEntity.class)
                .eq("repositoryId",repositoryRemoteProxyQuery.getRepositoryId())
                .eq("remoteProxyId", repositoryRemoteProxyQuery.getRemoteProxyId())
                .orders(repositoryRemoteProxyQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,RepositoryRemoteProxyEntity.class);
    }

    /**
     * 条件分页查询远程库代理信息
     * @param repositoryRemoteProxyQuery
     * @return Pagination <RepositoryRemoteProxyEntity>
     */
    public Pagination<RepositoryRemoteProxyEntity> findRepositoryRemoteProxyPage(RepositoryRemoteProxyQuery repositoryRemoteProxyQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryRemoteProxyEntity.class)
                .eq("repositoryId",repositoryRemoteProxyQuery.getRepositoryId())
                .eq("remoteProxyId", repositoryRemoteProxyQuery.getRemoteProxyId())
                .orders(repositoryRemoteProxyQuery.getOrderParams())
                .pagination(repositoryRemoteProxyQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,RepositoryRemoteProxyEntity.class);
    }



    public List<RepositoryRemoteProxyEntity> findAgencyByRpyIdAndPath(String[] repositoryIds, String agencyId) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(RepositoryRemoteProxyEntity.class)
                .eq("remoteProxyId", agencyId);

        if (!ObjectUtils.isEmpty(repositoryIds)){
            queryBuilders.in("repositoryId",repositoryIds);
        }
        QueryCondition queryCondition = queryBuilders.get();
        return jpaTemplate.findList(queryCondition,RepositoryRemoteProxyEntity.class);
    }

    public List<RepositoryRemoteProxyEntity> findAgencyByRpyIds(String[] repositoryIds) {
        if (ObjectUtils.isEmpty(repositoryIds)){
            return Collections.emptyList();
        }
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryRemoteProxyEntity.class)
                .in("repositoryId",repositoryIds)
                .get();
        return jpaTemplate.findList(queryCondition,RepositoryRemoteProxyEntity.class);
    }

    public List<RepositoryRemoteProxyEntity> findRemoteProxyRepId(String repositoryId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryRemoteProxyEntity.class)
                .eq("repositoryId",repositoryId)
                .get();
        return  jpaTemplate.findList(queryCondition,RepositoryRemoteProxyEntity.class);
    }
}