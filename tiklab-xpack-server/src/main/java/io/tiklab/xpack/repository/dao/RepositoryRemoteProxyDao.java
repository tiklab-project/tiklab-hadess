package io.tiklab.xpack.repository.dao;

import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.xpack.repository.entity.RepositoryRemoteProxyEntity;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.xpack.repository.model.RepositoryRemoteProxyQuery;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

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
                .orders(repositoryRemoteProxyQuery.getOrderParams())
                .pagination(repositoryRemoteProxyQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,RepositoryRemoteProxyEntity.class);
    }

    public String findAgencyUrl(String repositoryName){
        String sql="SELECT pr.agency_url from pack_repository_remote_proxy pr " +
                " LEFT JOIN pack_repository_group_items gr ON gr.repository_id=pr.repository_id  " +
                "LEFT JOIN pack_repository re ON gr.repository_group_id=re.id" +
                " WHERE re.`name`='"+repositoryName +"'";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        List<String> agencyUrlList = jdbcTemplate.queryForList(sql,String.class);
        if (CollectionUtils.isNotEmpty(agencyUrlList)){
           return agencyUrlList.get(0);
        }
        return null;
    }

}