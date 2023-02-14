package net.tiklab.xpack.repository.dao;

import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.xpack.repository.entity.RepositoryEntity;
import net.tiklab.xpack.repository.model.RepositoryQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RepositoryDao
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
    * findAllRepository
    * @return
    */
    public List<RepositoryEntity> findAllRepository() {
        return jpaTemplate.findAll(RepositoryEntity.class);
    }

    public List<RepositoryEntity> findRepositoryList(List<String> idList) {
        return jpaTemplate.findList(RepositoryEntity.class,idList);
    }

    public List<RepositoryEntity> findRepositoryList(RepositoryQuery repositoryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RepositoryEntity.class)
                .eq("repositoryType",repositoryQuery.getRepositoryType())
                .eq("type",repositoryQuery.getType())
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