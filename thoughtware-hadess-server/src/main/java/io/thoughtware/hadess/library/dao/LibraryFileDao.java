package io.thoughtware.hadess.library.dao;

import io.thoughtware.hadess.library.entity.LibraryFileEntity;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jdbc.JdbcTemplate;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.hadess.library.model.LibraryFileQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
                .eq("relativePath",libraryFileQuery.getRelativePath())
                .eq("repositoryId",libraryFileQuery.getRepositoryId())
                .eq("fileUrl",libraryFileQuery.getFileUrl())
                .orders(libraryFileQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,LibraryFileEntity.class);
    }


    public List<LibraryFileEntity> findLibraryFileListByNames(String [] names) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryFileEntity.class)
                .in("fileName",names)
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
                .eq("relativePath",libraryFileQuery.getRelativePath())
                .eq("repositoryId",libraryFileQuery.getRepositoryId())
                .pagination(libraryFileQuery.getPageParam())
                .orders(libraryFileQuery.getOrderParams())
                .get();
        return jpaTemplate.findPage(queryCondition,LibraryFileEntity.class);
    }

    public List<LibraryFileEntity> findLibraryFileByLibraryId(String [] repositoryIds,String fileName){
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryFileEntity.class)
                .in("repositoryId",repositoryIds)
                .eq("fileName",fileName)
                .get();
        return jpaTemplate.findList(queryCondition,LibraryFileEntity.class);
    }


    /**
     * 条件分页查询制品文件
     * @param fileUrl
     * @return Pagination <LibraryFileEntity>
     */
    public List<LibraryFileEntity> findLibraryLikeFileUrl(String fileUrl){
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryFileEntity.class)
                .like("fileUrl",fileUrl)
                .get();
        return jpaTemplate.findList(queryCondition,LibraryFileEntity.class);
    }

    /**
     * 通过制品库和制品以及版本查询
     * @param repositoryId  制品库id
     * @param libraryName   制品名称
     * @param version       版本
     * @return Pagination <LibraryFileEntity>
     */
    public List<LibraryFileEntity> findFileByReAndLibraryAndVer(String repositoryId,String libraryName,String version){
        String sql="SELECT lf.*   FROM pack_library_file lf\n" +
                "LEFT JOIN pack_library_version lv ON lv.id=lf.library_version_id\n" +
                "LEFT JOIN pack_library li ON li.id=lv.library_id\n" +
                "WHERE lf.repository_id='"+repositoryId+"' AND li.name='"+libraryName+"' AND lv.version='"+version+"'";

        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        List<LibraryFileEntity> libraryFileEntities = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LibraryFileEntity.class));

        return libraryFileEntities;
    }
}