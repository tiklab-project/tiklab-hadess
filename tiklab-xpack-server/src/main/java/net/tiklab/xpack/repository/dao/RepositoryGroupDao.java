package net.tiklab.xpack.repository.dao;

import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.xpack.repository.entity.RepositoryGroupEntity;
import net.tiklab.xpack.repository.model.RepositoryGroupQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RepositoryGroupDao
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
    * findAllRepositoryGroup
    * @return
    */
    public List<RepositoryGroupEntity> findAllRepositoryGroup() {
        return jpaTemplate.findAll(RepositoryGroupEntity.class);
    }

    public List<RepositoryGroupEntity> findRepositoryGroupList(List<String> idList) {
        return jpaTemplate.findList(RepositoryGroupEntity.class,idList);
    }

    public List<RepositoryGroupEntity> findRepositoryGroupList(RepositoryGroupQuery repositoryGroupQuery) {
        QueryCondition QueryCondition = QueryBuilders.createQuery(RepositoryGroupEntity.class)
                .eq("repositoryGroupId", repositoryGroupQuery.getRepositoryGroupId())
                .orders(repositoryGroupQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(QueryCondition,RepositoryGroupEntity.class);
    }

    public Pagination<RepositoryGroupEntity> findRepositoryGroupPage(RepositoryGroupQuery repositoryGroupQuery) {
        QueryCondition QueryCondition = QueryBuilders.createQuery(RepositoryGroupEntity.class)
                .eq("repositoryGroupId", repositoryGroupQuery.getRepositoryGroupId())
                .orders(repositoryGroupQuery.getOrderParams())
                .pagination(repositoryGroupQuery.getPageParam())
                .get();

        return jpaTemplate.findPage(QueryCondition,RepositoryGroupEntity.class);
    }
}