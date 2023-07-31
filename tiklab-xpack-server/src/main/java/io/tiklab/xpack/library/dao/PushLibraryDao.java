package io.tiklab.xpack.library.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.xpack.library.entity.PushLibraryEntity;
import io.tiklab.xpack.library.model.PushLibraryQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PushLibraryDao-制品数据访问
 */
@Repository
public class PushLibraryDao {

    private static Logger logger = LoggerFactory.getLogger(PushLibraryDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pushPushLibraryEntity
     * @return
     */
    public String createPushLibrary(PushLibraryEntity pushPushLibraryEntity) {
        return jpaTemplate.save(pushPushLibraryEntity,String.class);
    }

    /**
     * 更新
     * @param pushPushLibraryEntity
     */
    public void updatePushLibrary(PushLibraryEntity pushPushLibraryEntity){
        jpaTemplate.update(pushPushLibraryEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deletePushLibrary(String id){
        jpaTemplate.delete(PushLibraryEntity.class,id);
    }

    /**
     * 条件删除
     * @param deleteCondition
     * @return
     */
    public void deletePushLibrary(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public PushLibraryEntity findPushLibrary(String id){
        return jpaTemplate.findOne(PushLibraryEntity.class,id);
    }

    /**
    * 查询所有制品
    * @return
    */
    public List<PushLibraryEntity> findAllPushLibrary() {
        return jpaTemplate.findAll(PushLibraryEntity.class);
    }

    /**
     * 通过ids查询
     * @param idList
     * @return List <PushLibraryEntity>
     */
    public List<PushLibraryEntity> findPushLibraryList(List<String> idList) {
        return jpaTemplate.findList(PushLibraryEntity.class,idList);
    }

    /**
     * 条件查询制品
     * @param pushPushLibraryQuery
     * @return List <PushLibraryEntity>
     */
    public List<PushLibraryEntity> findPushLibraryList(PushLibraryQuery pushPushLibraryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(PushLibraryEntity.class)
                .eq("repositoryId", pushPushLibraryQuery.getRepositoryId())
                .eq("libraryId",pushPushLibraryQuery.getLibraryId())
                .orders(pushPushLibraryQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,PushLibraryEntity.class);
    }

    /**
     * 条件分页查询
     * @param pushPushLibraryQuery
     * @return Pagination <PushLibraryEntity>
     */
    public Pagination<PushLibraryEntity> findPushLibraryPage(PushLibraryQuery pushPushLibraryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(PushLibraryEntity.class)
                .eq("repositoryId",pushPushLibraryQuery.getRepositoryId())
                .eq("libraryId",pushPushLibraryQuery.getLibraryId())
                .orders(pushPushLibraryQuery.getOrderParams())
                .pagination(pushPushLibraryQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,PushLibraryEntity.class);
    }


}