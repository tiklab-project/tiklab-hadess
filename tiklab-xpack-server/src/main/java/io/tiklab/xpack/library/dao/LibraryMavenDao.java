package io.tiklab.xpack.library.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.xpack.library.entity.LibraryMavenEntity;
import io.tiklab.xpack.library.model.LibraryMavenQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LibraryMavenDao-maven制品数据访问
 */
@Repository
public class LibraryMavenDao{

    private static Logger logger = LoggerFactory.getLogger(LibraryMavenDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param libraryMavenEntity
     * @return
     */
    public String createLibraryMaven(LibraryMavenEntity libraryMavenEntity) {
        return jpaTemplate.save(libraryMavenEntity,String.class);
    }

    /**
     * 更新
     * @param libraryMavenEntity
     */
    public void updateLibraryMaven(LibraryMavenEntity libraryMavenEntity){
        jpaTemplate.update(libraryMavenEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteLibraryMaven(String id){
        jpaTemplate.delete(LibraryMavenEntity.class,id);
    }

    /**
     * 条件删maven制品
     * @param deleteCondition
     */
    public void deleteLibraryMaven(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public LibraryMavenEntity findLibraryMaven(String id){
        return jpaTemplate.findOne(LibraryMavenEntity.class,id);
    }

    /**
    * 查询所有maven制品
    * @return
    */
    public List<LibraryMavenEntity> findAllLibraryMaven() {
        return jpaTemplate.findAll(LibraryMavenEntity.class);
    }

    /**
     * 通过ids查询maven制品
     * @param idList
     * @return List <LibraryMavenEntity>
     */
    public List<LibraryMavenEntity> findLibraryMavenList(List<String> idList) {
        return jpaTemplate.findList(LibraryMavenEntity.class,idList);
    }

    /**
     * 条件查询maven制品
     * @param libraryMavenQuery
     * @return List <LibraryMavenEntity>
     */
    public List<LibraryMavenEntity> findLibraryMavenList(LibraryMavenQuery libraryMavenQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryMavenEntity.class)
                .eq("libraryId", libraryMavenQuery.getLibraryId())
                .eq("groupId", libraryMavenQuery.getGroupId())
                .eq("artifactId", libraryMavenQuery.getArtifactId())
                .orders(libraryMavenQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,LibraryMavenEntity.class);
    }

    /**
     * 条件分页查询maven制品
     * @param libraryMavenQuery
     * @return Pagination <LibraryMavenEntity>
     */
    public Pagination<LibraryMavenEntity> findLibraryMavenPage(LibraryMavenQuery libraryMavenQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryMavenEntity.class)
                .eq("libraryId", libraryMavenQuery.getLibraryId())
                .eq("groupId", libraryMavenQuery.getGroupId())
                .eq("artifactId", libraryMavenQuery.getArtifactId())
                .orders(libraryMavenQuery.getOrderParams())
                .pagination(libraryMavenQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,LibraryMavenEntity.class);
    }

    public List<LibraryMavenEntity> libraryMavenByLibraryIds(String[] libraryIds) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryMavenEntity.class)
                .in("libraryId", libraryIds)
                .get();
        return jpaTemplate.findList(queryCondition,LibraryMavenEntity.class);
    }
}