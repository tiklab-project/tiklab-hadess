package net.tiklab.xpack.library.dao;

import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.xpack.library.entity.LibraryMavenEntity;
import net.tiklab.xpack.library.model.LibraryMavenQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LibraryMavenDao
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
    * findAllLibraryMaven
    * @return
    */
    public List<LibraryMavenEntity> findAllLibraryMaven() {
        return jpaTemplate.findAll(LibraryMavenEntity.class);
    }

    public List<LibraryMavenEntity> findLibraryMavenList(List<String> idList) {
        return jpaTemplate.findList(LibraryMavenEntity.class,idList);
    }

    public List<LibraryMavenEntity> findLibraryMavenList(LibraryMavenQuery libraryMavenQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryMavenEntity.class)
                .eq("libraryId", libraryMavenQuery.getLibraryId())
                .eq("groupId", libraryMavenQuery.getGroupId())
                .eq("artifactId", libraryMavenQuery.getArtifactId())
                .orders(libraryMavenQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,LibraryMavenEntity.class);
    }

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
}