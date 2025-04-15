package io.tiklab.hadess.library.dao;

import io.tiklab.hadess.library.entity.LibraryEntity;
import io.tiklab.hadess.library.entity.LibraryFileEntity;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.hadess.library.entity.LibraryVersionEntity;
import io.tiklab.hadess.library.model.LibraryFileQuery;
import io.tiklab.hadess.repository.entity.RepositoryEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
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
        QueryBuilders builders = QueryBuilders.createQuery(LibraryFileEntity.class)
                .eq("libraryId", libraryFileQuery.getLibraryId())
                .eq("libraryVersionId", libraryFileQuery.getLibraryVersionId())
                .eq("relativePath", libraryFileQuery.getRelativePath())
                .eq("repositoryId", libraryFileQuery.getRepositoryId())
                .eq("fileUrl", libraryFileQuery.getFileUrl())
                .orders(libraryFileQuery.getOrderParams());


        if (("like").equals(libraryFileQuery.getFindNameWay())){
            builders.like("fileName",libraryFileQuery.getFileName());
         }else {
            builders.eq("fileName", libraryFileQuery.getFileName());
        }
        QueryCondition queryCondition = builders.get();
        return jpaTemplate.findList(queryCondition,LibraryFileEntity.class);
    }


    public List<LibraryFileEntity> findLibraryFileListByNames(String [] names) {
        if (ObjectUtils.isEmpty(names)){
            return Collections.emptyList();
        }
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

    public List<LibraryFileEntity> findLibraryFileList(String [] repositoryIds,String relativePath){
        QueryBuilders queryBuilders = QueryBuilders.createQuery(LibraryFileEntity.class)
                .eq("relativePath", relativePath);

        if (!ObjectUtils.isEmpty(repositoryIds)){
            queryBuilders.in("repositoryId",repositoryIds);
        }
        QueryCondition queryCondition = queryBuilders.get();
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
        QueryCondition queryCondition;
        if (StringUtils.isNotEmpty(version)){
            queryCondition = QueryBuilders.createQuery(LibraryFileEntity.class, "file")
                    .leftJoin(LibraryVersionEntity.class, "ver", "file.libraryVersionId=ver.id")
                    .leftJoin(LibraryEntity.class, "li", "ver.libraryId=li.id")
                    .leftJoin(RepositoryEntity.class, "re", "li.repositoryId=re.id")
                    .eq("li.name", libraryName)
                    .eq("ver.version", version)
                    .eq("re.id", repositoryId)
                    .get();
        }else {
            queryCondition = QueryBuilders.createQuery(LibraryFileEntity.class, "file")
                    .leftJoin(LibraryEntity.class, "li", "file.libraryId=li.id")
                    .leftJoin(RepositoryEntity.class, "re", "li.repositoryId=re.id")
                    .eq("li.name", libraryName)
                    .eq("re.id", repositoryId)
                    .get();
        }
        return   jpaTemplate.findList(queryCondition,LibraryFileEntity.class);
    }

    /**
     * 通过制品库和制品以及版本查询
     * @param repositoryId  制品库id
     * @param libraryName   制品名称
     * @param version       版本
     * @return Pagination <LibraryFileEntity>
     */
    public List<LibraryFileEntity> findFileByReAndLibraryAndVer(String[] repositoryId,String libraryName,String version){

        QueryCondition queryCondition;
        if (StringUtils.isNotEmpty(version)){
             queryCondition = QueryBuilders.createQuery(LibraryFileEntity.class, "file")
                    .leftJoin(LibraryVersionEntity.class, "ver", "file.libraryVersionId=ver.id")
                    .leftJoin(LibraryEntity.class, "li", "ver.libraryId=li.id")
                    .leftJoin(RepositoryEntity.class, "re", "li.repositoryId=re.id")
                    .eq("li.name", libraryName)
                    .eq("ver.version", version)
                    .in("re.id", repositoryId)
                    .get();
        }else {
             queryCondition = QueryBuilders.createQuery(LibraryFileEntity.class, "file")
                    .leftJoin(LibraryEntity.class, "li", "file.libraryId=li.id")
                    .leftJoin(RepositoryEntity.class, "re", "li.repositoryId=re.id")
                    .eq("li.name", libraryName)
                    .in("re.id", repositoryId)
                    .get();
        }
        return   jpaTemplate.findList(queryCondition,LibraryFileEntity.class);
    }
}