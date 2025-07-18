package io.tiklab.hadess.repository.dao;

import io.tiklab.hadess.repository.entity.RepositoryMavenEntity;
import io.tiklab.hadess.repository.model.RepositoryMavenQuery;
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

import java.util.List;

/**
 * RepositoryMavenDao-存储库数据访问
 */
@Repository
public class RepositoryMavenDao {

    private static Logger logger = LoggerFactory.getLogger(RepositoryMavenDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param repositoryMavenEntity
     * @return
     */
    public String createRepositoryMaven(RepositoryMavenEntity repositoryMavenEntity) {
        return jpaTemplate.save(repositoryMavenEntity,String.class);
    }

    /**
     * 更新
     * @param repositoryMavenEntity
     */
    public void updateRepositoryMaven(RepositoryMavenEntity repositoryMavenEntity){
        jpaTemplate.update(repositoryMavenEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteRepositoryMaven(String id){
        jpaTemplate.delete(RepositoryMavenEntity.class,id);
    }

    /**
     * 条件删除存储库
     * @param deleteCondition
     */
    public void deleteRepositoryMaven(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public RepositoryMavenEntity findRepositoryMaven(String id){
        return jpaTemplate.findOne(RepositoryMavenEntity.class,id);
    }

    /**
    * 查询所有存储库
    * @return
    */
    public List<RepositoryMavenEntity> findAllRepositoryMaven() {
        return jpaTemplate.findAll(RepositoryMavenEntity.class);
    }

    /**
     * 通过ids查询存储库
     * @param idList
     * @return List <RepositoryMavenEntity>
     */
    public List<RepositoryMavenEntity> findRepositoryMavenList(List<String> idList) {
        return jpaTemplate.findList(RepositoryMavenEntity.class,idList);
    }

    /**
     * 条件查询存储库
     * @param repositoryMavenQuery
     * @return List <RepositoryMavenEntity>
     */
    public List<RepositoryMavenEntity> findRepositoryMavenList(RepositoryMavenQuery repositoryMavenQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryMavenEntity.class)
                .eq("repositoryId",repositoryMavenQuery.getRepositoryId())
                .orders(repositoryMavenQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,RepositoryMavenEntity.class);
    }

    /**
     * 条件分页查询存储库
     * @param repositoryMavenQuery
     * @return Pagination <RepositoryMavenEntity>
     */
    public Pagination<RepositoryMavenEntity> findRepositoryMavenPage(RepositoryMavenQuery repositoryMavenQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryMavenEntity.class)
                .eq("repositoryId",repositoryMavenQuery.getRepositoryId())
                .orders(repositoryMavenQuery.getOrderParams())
                .pagination(repositoryMavenQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,RepositoryMavenEntity.class);
    }

    /**
     * 通过制品库ids  查询
     * @param ids
     * @return Pagination <RepositoryMavenEntity>
     */
    public List<RepositoryMavenEntity> findRepositoryMavenByRpyIds(String []ids,String version) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(RepositoryMavenEntity.class)
                .eq("version", version);

        if (!ObjectUtils.isEmpty(ids)){
            queryBuilders.in("repositoryId",ids);
        }
        QueryCondition queryCondition = queryBuilders.get();
        return jpaTemplate.findList(queryCondition,RepositoryMavenEntity.class);
    }



    public List<RepositoryMavenEntity> findRepositoryMavenByRep(RepositoryMavenQuery repositoryMavenQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(RepositoryMavenEntity.class);
        if (!ObjectUtils.isEmpty(repositoryMavenQuery.getRepIds())){
            queryBuilders.in("repositoryId",repositoryMavenQuery.getRepIds());
        }
        if (!ObjectUtils.isEmpty(repositoryMavenQuery.getRepositoryId())){
            queryBuilders.eq("repositoryId",repositoryMavenQuery.getRepositoryId());
        }
        QueryCondition queryCondition = queryBuilders.get();
        return jpaTemplate.findList(queryCondition,RepositoryMavenEntity.class);
    }
}