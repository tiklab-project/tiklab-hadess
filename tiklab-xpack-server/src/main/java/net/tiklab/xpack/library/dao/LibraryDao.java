package net.tiklab.xpack.library.dao;

import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.xpack.library.entity.LibraryEntity;
import net.tiklab.xpack.library.model.LibraryQuery;
import net.tiklab.xpack.repository.entity.RepositoryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LibraryDao
 */
@Repository
public class LibraryDao{

    private static Logger logger = LoggerFactory.getLogger(LibraryDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param libraryEntity
     * @return
     */
    public String createLibrary(LibraryEntity libraryEntity) {
        return jpaTemplate.save(libraryEntity,String.class);
    }

    /**
     * 更新
     * @param libraryEntity
     */
    public void updateLibrary(LibraryEntity libraryEntity){
        jpaTemplate.update(libraryEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteLibrary(String id){
        jpaTemplate.delete(LibraryEntity.class,id);
    }

    public void deleteLibrary(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public LibraryEntity findLibrary(String id){
        return jpaTemplate.findOne(LibraryEntity.class,id);
    }

    /**
    * findAllLibrary
    * @return
    */
    public List<LibraryEntity> findAllLibrary() {
        return jpaTemplate.findAll(LibraryEntity.class);
    }

    public List<LibraryEntity> findLibraryList(List<String> idList) {
        return jpaTemplate.findList(LibraryEntity.class,idList);
    }

    public List<LibraryEntity> findLibraryList(LibraryQuery libraryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryEntity.class)
                .eq("repositoryId",libraryQuery.getRepositoryId())
                .eq("libraryType",libraryQuery.getLibraryType())
                .like("name",libraryQuery.getName())
                .eq("newVersion",libraryQuery.getNewVersion())
                .orders(libraryQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,LibraryEntity.class);
    }

    public Pagination<LibraryEntity> findLibraryPage(LibraryQuery libraryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryEntity.class)
                .eq("repositoryId",libraryQuery.getRepositoryId())
                .eq("libraryType",libraryQuery.getLibraryType())
                .like("name",libraryQuery.getName())
                .eq("newVersion",libraryQuery.getNewVersion())
                .orders(libraryQuery.getOrderParams())
                .pagination(libraryQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,LibraryEntity.class);
    }
}