package io.tiklab.xpack.library.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.xpack.library.entity.LibraryVersionEntity;
import io.tiklab.xpack.library.model.LibraryVersionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LibraryVersionDao-制品版本数据访问
 */
@Repository
public class LibraryVersionDao{

    private static Logger logger = LoggerFactory.getLogger(LibraryVersionDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param libraryVersionEntity
     * @return
     */
    public String createLibraryVersion(LibraryVersionEntity libraryVersionEntity) {
        return jpaTemplate.save(libraryVersionEntity,String.class);
    }

    /**
     * 更新
     * @param libraryVersionEntity
     */
    public void updateLibraryVersion(LibraryVersionEntity libraryVersionEntity){
        jpaTemplate.update(libraryVersionEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteLibraryVersion(String id){
        jpaTemplate.delete(LibraryVersionEntity.class,id);
    }

    /**
     * 条件删除制品版本
     * @param deleteCondition
     */
    public void deleteLibraryVersion(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public LibraryVersionEntity findLibraryVersion(String id){
        return jpaTemplate.findOne(LibraryVersionEntity.class,id);
    }

    /**
    * 查询所有制品版本
    * @return
    */
    public List<LibraryVersionEntity> findAllLibraryVersion() {
        return jpaTemplate.findAll(LibraryVersionEntity.class);
    }

    /**
     * 通过ids查询制品版本
     * @param idList
     * @return List <LibraryVersionEntity>
     */
    public List<LibraryVersionEntity> findLibraryVersionList(List<String> idList) {
        return jpaTemplate.findList(LibraryVersionEntity.class,idList);
    }

    /**
     * 条件查询制品版本
     * @param libraryVersionQuery
     * @return List <LibraryVersionEntity>
     */
    public List<LibraryVersionEntity> findLibraryVersionList(LibraryVersionQuery libraryVersionQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryVersionEntity.class)
                .eq("repositoryId",libraryVersionQuery.getRepositoryId())
                .eq("libraryId",libraryVersionQuery.getLibraryId())
                .eq("version",libraryVersionQuery.getVersion())
                .orders(libraryVersionQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,LibraryVersionEntity.class);
    }

    /**
     * 条件分页查询制品版本
     * @param libraryVersionQuery
     * @return Pagination <LibraryVersionEntity>
     */
    public Pagination<LibraryVersionEntity> findLibraryVersionPage(LibraryVersionQuery libraryVersionQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryVersionEntity.class)
                .eq("repositoryId",libraryVersionQuery.getRepositoryId())
                .eq("version",libraryVersionQuery.getVersion())
                .eq("libraryId",libraryVersionQuery.getLibraryId())
                .orders(libraryVersionQuery.getOrderParams())
                .pagination(libraryVersionQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,LibraryVersionEntity.class);
    }
}