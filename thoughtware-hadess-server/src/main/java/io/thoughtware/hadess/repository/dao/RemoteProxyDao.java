package io.thoughtware.hadess.repository.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.hadess.repository.entity.RemoteProxyEntity;
import io.thoughtware.hadess.repository.model.RemoteProxyQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RemoteProxyDao-代理地址数据访问
 */
@Repository
public class RemoteProxyDao {

    private static Logger logger = LoggerFactory.getLogger(RemoteProxyDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param remoteProxyEntity
     * @return
     */
    public String createRemoteProxy(RemoteProxyEntity remoteProxyEntity) {
        return jpaTemplate.save(remoteProxyEntity,String.class);
    }

    /**
     * 更新
     * @param remoteProxyEntity
     */
    public void updateRemoteProxy(RemoteProxyEntity remoteProxyEntity){
        jpaTemplate.update(remoteProxyEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteRemoteProxy(String id){
        jpaTemplate.delete(RemoteProxyEntity.class,id);
    }

    /**
     * 条件删除代理地址
     * @param deleteCondition
     */
    public void deleteRemoteProxy(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public RemoteProxyEntity findRemoteProxy(String id){
        return jpaTemplate.findOne(RemoteProxyEntity.class,id);
    }

    /**
    * 查询所有代理地址
    * @return
    */
    public List<RemoteProxyEntity> findAllRemoteProxy() {
        return jpaTemplate.findAll(RemoteProxyEntity.class);
    }

    /**
     * 通过ids查询代理地址
     * @param idList
     * @return List <RemoteProxyEntity>
     */
    public List<RemoteProxyEntity> findRemoteProxyList(List<String> idList) {
        return jpaTemplate.findList(RemoteProxyEntity.class,idList);
    }

    /**
     * 条件查询代理地址
     * @param remoteProxyQuery
     * @return List <RemoteProxyEntity>
     */
    public List<RemoteProxyEntity> findRemoteProxyList(RemoteProxyQuery remoteProxyQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RemoteProxyEntity.class)
                .eq("agencyType",remoteProxyQuery.getAgencyType())
                .eq("agencyUrl",remoteProxyQuery.getAgencyUrl())
                .orders(remoteProxyQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,RemoteProxyEntity.class);
    }

    /**
     * 条件分页查询代理地址
     * @param remoteProxyQuery
     * @return Pagination <RemoteProxyEntity>
     */
    public Pagination<RemoteProxyEntity> findRemoteProxyPage(RemoteProxyQuery remoteProxyQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RemoteProxyEntity.class)
                .eq("agencyType",remoteProxyQuery.getAgencyType())
                .eq("agencyUrl",remoteProxyQuery.getAgencyUrl())
                .orders(remoteProxyQuery.getOrderParams())
                .pagination(remoteProxyQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,RemoteProxyEntity.class);
    }
}