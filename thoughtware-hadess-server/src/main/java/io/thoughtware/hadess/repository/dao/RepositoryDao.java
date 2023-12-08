package io.thoughtware.hadess.repository.dao;

import io.thoughtware.hadess.repository.entity.RepositoryEntity;
import io.thoughtware.hadess.repository.model.RepositoryQuery;
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
 * RepositoryDao-制品库数据访问
 */
@Repository
public class RepositoryDao{

    private static Logger logger = LoggerFactory.getLogger(RepositoryDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param repositoryEntity
     * @return
     */
    public String createRepository(RepositoryEntity repositoryEntity) {
        return jpaTemplate.save(repositoryEntity,String.class);
    }

    /**
     * 更新
     * @param repositoryEntity
     */
    public void updateRepository(RepositoryEntity repositoryEntity){
        jpaTemplate.update(repositoryEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteRepository(String id){
        jpaTemplate.delete(RepositoryEntity.class,id);
    }

    /**
     * 条件删除制品库
     * @param deleteCondition
     */
    public void deleteRepository(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public RepositoryEntity findRepository(String id){
        return jpaTemplate.findOne(RepositoryEntity.class,id);
    }

    /**
    * 查询所有制品库
    * @return
    */
    public List<RepositoryEntity> findAllRepository() {
        return jpaTemplate.findAll(RepositoryEntity.class);
    }

    /**
     * 通过ids查询制品库
     * @param idList
     * @return List <RepositoryEntity>
     */
    public List<RepositoryEntity> findRepositoryList(List<String> idList) {
        return jpaTemplate.findList(RepositoryEntity.class,idList);
    }

    /**
     * 条件查询制品库
     * @param repositoryQuery
     * @return List <RepositoryEntity>
     */
    public List<RepositoryEntity> findRepositoryList(RepositoryQuery repositoryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryEntity.class)
                .eq("repositoryType",repositoryQuery.getRepositoryType())
                .eq("type",repositoryQuery.getType())
                .eq("category",repositoryQuery.getCategory())
                .eq("name",repositoryQuery.getName())
                .orders(repositoryQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,RepositoryEntity.class);
    }

    public List<RepositoryEntity> findLocalAndRemoteRepository(String type) {
        String[] strings = new String[]{"local","remote"};
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryEntity.class)
                .eq("type",type)
                .in("repositoryType",strings)
                .get();
        return jpaTemplate.findList(queryCondition,RepositoryEntity.class);
    }

    /**
     * 条件分页查询制品库
     * @param repositoryQuery
     * @return Pagination <RepositoryEntity>
     */
    public Pagination<RepositoryEntity> findRepositoryPage(RepositoryQuery repositoryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryEntity.class)
                .eq("repositoryType",repositoryQuery.getRepositoryType())
                .eq("type",repositoryQuery.getType())
                .eq("name",repositoryQuery.getName())
                .orders(repositoryQuery.getOrderParams())
                .pagination(repositoryQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,RepositoryEntity.class);
    }
}