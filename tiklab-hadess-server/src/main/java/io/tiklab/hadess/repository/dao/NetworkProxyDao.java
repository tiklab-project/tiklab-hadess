package io.tiklab.hadess.repository.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.hadess.repository.entity.NetworkProxyEntity;
import io.tiklab.hadess.repository.model.NetworkProxyQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * NetworkProxyDao-网络代理地址数据访问
 */
@Repository
public class NetworkProxyDao {

    private static Logger logger = LoggerFactory.getLogger(NetworkProxyDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param networkProxyEntity
     * @return
     */
    public String createNetworkProxy(NetworkProxyEntity networkProxyEntity) {
        return jpaTemplate.save(networkProxyEntity,String.class);
    }

    /**
     * 更新
     * @param networkProxyEntity
     */
    public void updateNetworkProxy(NetworkProxyEntity networkProxyEntity){
        jpaTemplate.update(networkProxyEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteNetworkProxy(String id){
        jpaTemplate.delete(NetworkProxyEntity.class,id);
    }

    /**
     * 条件删除网络代理地址
     * @param deleteCondition
     */
    public void deleteNetworkProxy(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public NetworkProxyEntity findNetworkProxy(String id){
        return jpaTemplate.findOne(NetworkProxyEntity.class,id);
    }

    /**
    * 查询所有网络代理地址
    * @return
    */
    public List<NetworkProxyEntity> findAllNetworkProxy() {
        return jpaTemplate.findAll(NetworkProxyEntity.class);
    }

    /**
     * 通过ids查询网络代理地址
     * @param idList
     * @return List <NetworkProxyEntity>
     */
    public List<NetworkProxyEntity> findNetworkProxyList(List<String> idList) {
        return jpaTemplate.findList(NetworkProxyEntity.class,idList);
    }

    /**
     * 条件查询网络代理地址
     * @param networkProxyQuery
     * @return List <NetworkProxyEntity>
     */
    public List<NetworkProxyEntity> findNetworkProxyList(NetworkProxyQuery networkProxyQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(NetworkProxyEntity.class)
                .eq("enable",networkProxyQuery.getEnable())
                .get();

        return jpaTemplate.findList(queryCondition,NetworkProxyEntity.class);
    }

    /**
     * 条件分页查询网络代理地址
     * @param networkProxyQuery
     * @return Pagination <NetworkProxyEntity>
     */
    public Pagination<NetworkProxyEntity> findNetworkProxyPage(NetworkProxyQuery networkProxyQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(NetworkProxyEntity.class)
                .orders(networkProxyQuery.getOrderParams())
                .pagination(networkProxyQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,NetworkProxyEntity.class);
    }
}