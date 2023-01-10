package net.tiklab.xpack.repository.dao;

import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.xpack.repository.entity.RepositoryRemoteProxyEntity;
import net.tiklab.xpack.repository.entity.StorageEntity;
import net.tiklab.xpack.repository.model.RepositoryRemoteProxyQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RepositoryRemoteProxyDao
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
    * findAllRepositoryRemoteProxy
    * @return
    */
    public List<RepositoryRemoteProxyEntity> findAllRepositoryRemoteProxy() {
        return jpaTemplate.findAll(RepositoryRemoteProxyEntity.class);
    }

    public List<RepositoryRemoteProxyEntity> findRepositoryRemoteProxyList(List<String> idList) {
        return jpaTemplate.findList(RepositoryRemoteProxyEntity.class,idList);
    }

    public List<RepositoryRemoteProxyEntity> findRepositoryRemoteProxyList(RepositoryRemoteProxyQuery repositoryRemoteProxyQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryRemoteProxyEntity.class)
                .eq("repositoryId",repositoryRemoteProxyQuery.getRepositoryId())
                .orders(repositoryRemoteProxyQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,RepositoryRemoteProxyEntity.class);
    }

    public Pagination<RepositoryRemoteProxyEntity> findRepositoryRemoteProxyPage(RepositoryRemoteProxyQuery repositoryRemoteProxyQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryRemoteProxyEntity.class)
                .eq("repositoryId",repositoryRemoteProxyQuery.getRepositoryId())
                .orders(repositoryRemoteProxyQuery.getOrderParams())
                .pagination(repositoryRemoteProxyQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,RepositoryRemoteProxyEntity.class);
    }
}