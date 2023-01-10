package net.tiklab.xpack.repository.dao;

import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.xpack.repository.entity.RepositoryClusterCfgEntity;
import net.tiklab.xpack.repository.entity.RepositoryRemoteProxyEntity;
import net.tiklab.xpack.repository.model.RepositoryClusterCfgQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RepositoryClusterCfgDao
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

    public List<RepositoryClusterCfgEntity> findRepositoryClusterCfgList(List<String> idList) {
        return jpaTemplate.findList(RepositoryClusterCfgEntity.class,idList);
    }

    public List<RepositoryClusterCfgEntity> findRepositoryClusterCfgList(RepositoryClusterCfgQuery repositoryClusterCfgQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryClusterCfgEntity.class)
                .eq("repositoryId",repositoryClusterCfgQuery.getRepositoryId())
                .orders(repositoryClusterCfgQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,RepositoryClusterCfgEntity.class);
    }

    public Pagination<RepositoryClusterCfgEntity> findRepositoryClusterCfgPage(RepositoryClusterCfgQuery repositoryClusterCfgQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryClusterCfgEntity.class)
                .eq("repositoryId",repositoryClusterCfgQuery.getRepositoryId())
                .orders(repositoryClusterCfgQuery.getOrderParams())
                .pagination(repositoryClusterCfgQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,RepositoryClusterCfgEntity.class);
    }
}