package io.thoughtware.hadess.pushcentral.dao;

import io.thoughtware.hadess.library.entity.LibraryEntity;
import io.thoughtware.hadess.pushcentral.entity.PushLibraryEntity;
import io.thoughtware.core.page.Page;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.hadess.pushcentral.model.PushLibraryQuery;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param pushLibraryEntity
     * @return
     */
    public String createPushLibrary(PushLibraryEntity pushLibraryEntity) {
        return jpaTemplate.save(pushLibraryEntity,String.class);
    }

    /**
     * 更新
     * @param pushLibraryEntity
     */
    public void updatePushLibrary(PushLibraryEntity pushLibraryEntity){
        jpaTemplate.update(pushLibraryEntity);
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
     * @param pushLibraryQuery
     * @return List <PushLibraryEntity>
     */
    public List<PushLibraryEntity> findPushLibraryList(PushLibraryQuery pushLibraryQuery, String[] LibraryIdList) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(PushLibraryEntity.class)
                .eq("repositoryId", pushLibraryQuery.getRepositoryId())
                .eq("pushGroupId",pushLibraryQuery.getPushGroupId());

        if (!ObjectUtils.isEmpty(LibraryIdList)&&LibraryIdList.length>0){
            queryBuilders.in("libraryId",LibraryIdList);
        }else {
            queryBuilders.eq("libraryId",pushLibraryQuery.getLibraryId());
        }
        QueryCondition queryCondition = queryBuilders.orders(pushLibraryQuery.getOrderParams())
                .orders(pushLibraryQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,PushLibraryEntity.class);
    }

    public List<PushLibraryEntity> findPushLibraryListByGroupId(String[] pushGroupIds) {
        QueryCondition condition = QueryBuilders.createQuery(PushLibraryEntity.class)
                .in("pushGroupId", pushGroupIds)
                .get();
        return jpaTemplate.findList(condition,PushLibraryEntity.class);
    }

    /**
     * 条件分页查询
     * @param pushLibraryQuery
     * @return Pagination <PushLibraryEntity>
     */
    public Pagination<PushLibraryEntity> findPushLibraryPage(PushLibraryQuery pushLibraryQuery,String[] LibraryIdList) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(PushLibraryEntity.class)
                .eq("repositoryId", pushLibraryQuery.getRepositoryId())
                 .eq("pushGroupId",pushLibraryQuery.getPushGroupId());
        if (!ObjectUtils.isEmpty(LibraryIdList)&&LibraryIdList.length>0){
            queryBuilders.in("libraryId",LibraryIdList);
       }else {
            queryBuilders.eq("libraryId",pushLibraryQuery.getLibraryId());
        }

        QueryCondition queryCondition = queryBuilders.orders(pushLibraryQuery.getOrderParams())
                .pagination(pushLibraryQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,PushLibraryEntity.class);
    }


    /**
     * 条件分页查询
     * @param pushLibraryQuery
     * @return Pagination <PushLibraryEntity>
     */
    public Pagination<PushLibraryEntity> findPushPage(PushLibraryQuery pushLibraryQuery) {

        Pagination pagination = new Pagination();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(getJdbcTemplate());
        Page pageParam = pushLibraryQuery.getPageParam();

        String countSql="SELECT count(1) FROM pack_push_library pu LEFT JOIN pack_library li ON pu.library_id=li.id WHERE pu.repository_id='"+pushLibraryQuery.getRepositoryId()+"'";
        if (StringUtils.isNotEmpty(pushLibraryQuery.getLibraryName())){
            countSql= countSql + " and li.name like '%" + pushLibraryQuery.getLibraryName()+ "%'";
        }

        Integer integer = jdbc.queryForObject(countSql, paramMap, Integer.class);
        pagination.setTotalRecord(integer);
        double result = Math.ceil(integer/pageParam.getPageSize());
        pagination.setTotalPage((int) result);

        String sql="SELECT pu.* FROM pack_push_library pu LEFT JOIN pack_library li ON pu.library_id=li.id WHERE pu.repository_id='"+pushLibraryQuery.getRepositoryId()+"'";
        if (StringUtils.isNotEmpty(pushLibraryQuery.getLibraryName())){
            sql= sql + " and li.name like '%" + pushLibraryQuery.getLibraryName()+ "%'";
        }
        int offset = (pageParam.getCurrentPage() - 1) * pageParam.getPageSize();
        sql= sql+" LIMIT " +pageParam.getPageSize()+" offset "+offset;
        List<LibraryEntity> query = jdbc.query(sql, paramMap, new BeanPropertyRowMapper(LibraryEntity.class));
        pagination.setDataList(query);
        return pagination;
    }


    public JdbcTemplate getJdbcTemplate() {

        return jpaTemplate.getJdbcTemplate();
    }

}