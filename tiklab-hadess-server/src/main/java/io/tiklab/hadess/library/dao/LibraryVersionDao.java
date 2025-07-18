package io.tiklab.hadess.library.dao;

import io.tiklab.hadess.library.entity.LibraryVersionEntity;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.hadess.library.model.LibraryVersionQuery;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        QueryBuilders pagination = QueryBuilders.createQuery(LibraryVersionEntity.class)
                .eq("repositoryId", libraryVersionQuery.getRepositoryId())
                .eq("version", libraryVersionQuery.getVersion())
                .eq("libraryId", libraryVersionQuery.getLibraryId())
                .orders(libraryVersionQuery.getOrderParams())
                .pagination(libraryVersionQuery.getPageParam());

        QueryCondition queryCondition = pagination.get();


        return jpaTemplate.findPage(queryCondition,LibraryVersionEntity.class);
    }
    /**
     * 通过制品名字和版本号查询
     * @param libraryName 制品名字
     * @param  version 版本
     * @return LibraryVersionEntity
     */
    public LibraryVersionEntity findVersionByNameAndVer(String libraryName,String version){
        LibraryVersionEntity libraryVersionEntity=null;
        String sql="SELECT ve.* from pack_library li LEFT JOIN pack_library_version ve ON li.id=ve.library_id WHERE li.name='"+libraryName+"' AND ve.version='"+version+"'";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        List<LibraryVersionEntity> versionEntities = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LibraryVersionEntity.class));
        if (CollectionUtils.isNotEmpty(versionEntities)){
             libraryVersionEntity = versionEntities.get(0);
        }
        return libraryVersionEntity;
    }


    /**
     * 通过制品ids 查询
     * @param libraryIds 制品ids
     */
    public List<LibraryVersionEntity> findVersionByLibraryIds(String[] libraryIds) {
        if (ObjectUtils.isEmpty(libraryIds)){
            return Collections.emptyList();
        }
        QueryCondition condition = QueryBuilders.createQuery(LibraryVersionEntity.class)
                .in("libraryId", libraryIds)
                .get();

        return jpaTemplate.findList(condition,LibraryVersionEntity.class)    ;
    }

    /**
     * 批量删除制品
     * @param ids ids
     */
    public void deleteBatchesVersion(String ids) {
        if (!ObjectUtils.isEmpty(ids)){
            String sql="  DELETE FROM pack_library_version WHERE id IN ("+ids+")";
            JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
            jdbcTemplate.execute(sql);
        }
    }

    public List<LibraryVersionEntity> findVersionByRepIds(String[] repIds) {
        if (ObjectUtils.isEmpty(repIds)){
            return Collections.emptyList();
        }
        QueryCondition condition = QueryBuilders.createQuery(LibraryVersionEntity.class)
                .in("repositoryId", repIds)
                .get();
        return jpaTemplate.findList(condition,LibraryVersionEntity.class)    ;
    }
}