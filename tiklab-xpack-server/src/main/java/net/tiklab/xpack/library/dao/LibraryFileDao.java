package net.tiklab.xpack.library.dao;

import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.xpack.library.entity.LibraryFileEntity;
import net.tiklab.xpack.library.model.LibraryFileQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LibraryFileDao-制品文件数据访问
 */
@Repository
public class LibraryFileDao{

    private static Logger logger = LoggerFactory.getLogger(LibraryFileDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param libraryFileEntity
     * @return
     */
    public String createLibraryFile(LibraryFileEntity libraryFileEntity) {
        return jpaTemplate.save(libraryFileEntity,String.class);
    }

    /**
     * 更新
     * @param libraryFileEntity
     */
    public void updateLibraryFile(LibraryFileEntity libraryFileEntity){
        jpaTemplate.update(libraryFileEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteLibraryFile(String id){
        jpaTemplate.delete(LibraryFileEntity.class,id);
    }

    /**
     * 条件删除制品文件
     * @param deleteCondition
     * @return
     */
    public void deleteLibraryFile(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public LibraryFileEntity findLibraryFile(String id){
        return jpaTemplate.findOne(LibraryFileEntity.class,id);
    }

    /**
    * findAllLibraryFile
    * @return
    */
    public List<LibraryFileEntity> findAllLibraryFile() {
        return jpaTemplate.findAll(LibraryFileEntity.class);
    }

    /**
     * 通过制品文件ids查询制品文件
     * @param idList
     * @return List <LibraryFileEntity>
     */
    public List<LibraryFileEntity> findLibraryFileList(List<String> idList) {
        return jpaTemplate.findList(LibraryFileEntity.class,idList);
    }

    /**
     * 条件查询制品文件
     * @param libraryFileQuery
     * @return List <LibraryFileEntity>
     */
    public List<LibraryFileEntity> findLibraryFileList(LibraryFileQuery libraryFileQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryFileEntity.class)
                .eq("libraryId",libraryFileQuery.getLibraryId())
                .eq("libraryVersionId",libraryFileQuery.getLibraryVersionId())
                .eq("fileName",libraryFileQuery.getFileName())
                .orders(libraryFileQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,LibraryFileEntity.class);
    }

    /**
     * 条件分页查询制品文件
     * @param libraryFileQuery
     * @return Pagination <LibraryFileEntity>
     */
    public Pagination<LibraryFileEntity> findLibraryFilePage(LibraryFileQuery libraryFileQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryFileEntity.class)
                .eq("libraryId",libraryFileQuery.getLibraryId())
                .eq("libraryVersionId",libraryFileQuery.getLibraryVersionId())
                .eq("fileName",libraryFileQuery.getFileName())
                .pagination(libraryFileQuery.getPageParam())
                .orders(libraryFileQuery.getOrderParams())
                .get();
        return jpaTemplate.findPage(queryCondition,LibraryFileEntity.class);
    }
}