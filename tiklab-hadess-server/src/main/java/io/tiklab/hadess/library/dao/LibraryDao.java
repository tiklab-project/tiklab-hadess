package io.tiklab.hadess.library.dao;

import io.tiklab.hadess.library.entity.LibraryEntity;
import io.tiklab.core.page.Page;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.hadess.library.model.Library;
import io.tiklab.hadess.library.model.LibraryQuery;
import org.apache.commons.collections.CollectionUtils;
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
 * LibraryDao-制品数据访问
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

    /**
     * 条件删除
     * @param deleteCondition
     * @return
     */
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
    * 查询所有制品
    * @return
    */
    public List<LibraryEntity> findAllLibrary() {
        return jpaTemplate.findAll(LibraryEntity.class);
    }

    /**
     * 通过ids查询制品
     * @param idList
     * @return List <LibraryEntity>
     */
    public List<LibraryEntity> findLibraryList(List<String> idList) {
        return jpaTemplate.findList(LibraryEntity.class,idList);
    }

    /**
     * 条件查询制品
     * @param libraryQuery
     * @return List <LibraryEntity>
     */
    public List<LibraryEntity> findLibraryList(LibraryQuery libraryQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(LibraryEntity.class)
                .eq("libraryType", libraryQuery.getLibraryType())
                .like("name", libraryQuery.getName())
                .eq("newVersion", libraryQuery.getNewVersion())
                .orders(libraryQuery.getOrderParams());

        List<String> repositoryIds = libraryQuery.getRepositoryIds();

        //组合库查询 传入关联的本地库和远程库id
        if (CollectionUtils.isNotEmpty(repositoryIds)){
            String[] repositorySize = new String[repositoryIds.size()];
            String[] repositoryId = repositoryIds.toArray(repositorySize);
            queryBuilders.in("repositoryId",repositoryId);
        }else {
            queryBuilders.eq("repositoryId", libraryQuery.getRepositoryId());
        }
        QueryCondition queryCondition = queryBuilders.get();
        return jpaTemplate.findList(queryCondition,LibraryEntity.class);
    }


    public List<LibraryEntity> findLibraryListNo(LibraryQuery libraryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryEntity.class)
                .eq("repositoryId", libraryQuery.getRepositoryId())
                .eq("name", libraryQuery.getName())
                .get();

        return jpaTemplate.findList(queryCondition,LibraryEntity.class);
    }
    /**
     * 条件分页查询制品
     * @param libraryQuery
     * @return Pagination <LibraryEntity>
     */
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


    /**
     * 制品库下面条件查询制品制品列表
     * @param libraryQuery
     * @return Pagination <LibraryEntity>
     */
    public Pagination<Library> findLibraryListByRepository(LibraryQuery libraryQuery) {
        Pagination pagination = new Pagination();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("repositoryIds", libraryQuery.getRepositoryIds());
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(getJdbcTemplate());
        Page pageParam = libraryQuery.getPageParam();

        //查询数量
        String countSql="SELECT count(1)  FROM (SELECT count(1)   " + jointSql("rpyLibrary");
        //拼接条件
         countSql = findRepositoryCondition(libraryQuery, countSql,"count");
        Integer integer = jdbc.queryForObject(countSql, paramMap, Integer.class);
        pagination.setTotalRecord(integer);
        double result = Math.ceil((double)integer/pageParam.getPageSize());
        pagination.setTotalPage((int) result);

        //根据制品查询出所有版本然后取最新创建的
        String  sql="SELECT li.* ,MAX(ver.version) AS showVersion,MAX(ver.id) AS versionId,MAX(ver.update_time) AS update_time,MAX(file.id) AS fileId,lim.group_id, lim.artifact_id,COUNT(DISTINCT ver.id) AS versionCount ,sum(file.size) AS librarySize " +
                jointSql("rpyLibrary");

        //拼接sql条件
        sql=findRepositoryCondition(libraryQuery,sql,"find");
        int offset = (pageParam.getCurrentPage() - 1) * pageParam.getPageSize();
        sql= sql+" LIMIT " +pageParam.getPageSize()+" offset "+offset;

        List<Library> query = jdbc.query(sql, paramMap, new BeanPropertyRowMapper(Library.class));
        pagination.setDataList(query);
        return pagination;
    }


    /**
     * 条件查询制品list
     * @param
     * @return
     */
    public Pagination<Library> findLibraryListByCond(LibraryQuery libraryQuery) {
        Pagination pagination = new Pagination();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("repositoryIds", libraryQuery.getRepositoryIds());
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(getJdbcTemplate());
        Page pageParam = libraryQuery.getPageParam();

        //查询数量
        String countSql="SELECT count(1)  FROM  (SELECT count(1)   "+jointSql("library" ) +" li.library_type='"+libraryQuery.getLibraryType()+"'";
        countSql=findCond(libraryQuery,countSql,"count");
        Integer integer = jdbc.queryForObject(countSql, paramMap, Integer.class);
        pagination.setTotalRecord(integer);
        double result = Math.ceil((double)integer/pageParam.getPageSize());
        pagination.setTotalPage((int) result);

        //查询列表
        String sql="SELECT li.* ,MAX(ver.version) AS showVersion,MAX(ver.id) AS versionId,MAX(ver.update_time) AS update_time,MAX(file.id) AS fileId," +
                "lim.group_id, lim.artifact_id,COUNT(DISTINCT ver.id) AS versionCount ,sum(file.size) AS librarySize "+
                jointSql("library") +" li.library_type='"+libraryQuery.getLibraryType()+"'";
        sql=findCond(libraryQuery,sql,"find");
        int offset = (pageParam.getCurrentPage() - 1) * pageParam.getPageSize();
        sql= sql+" LIMIT " +pageParam.getPageSize()+" offset "+offset;

        List<Library> query = jdbc.query(sql, paramMap, new BeanPropertyRowMapper(Library.class));
        pagination.setDataList(query);
        return pagination;
    }



    /**
     * 精确条件查询制品
     * @param libraryQuery
     * @return List <LibraryEntity>
     */
    public List<LibraryEntity> findEqLibraryList(LibraryQuery libraryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryEntity.class)
                .eq("repositoryId",libraryQuery.getRepositoryId())
                .eq("name", libraryQuery.getName())
                .orders(libraryQuery.getOrderParams())
                .get();

        return jpaTemplate.findList(queryCondition,LibraryEntity.class);
    }

    public JdbcTemplate getJdbcTemplate() {
        io.tiklab.dal.jdbc.JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return jdbcTemplate;
    }

    /**
     * 查询未添加到推送中央仓库的记录的制品列表
     * @param libraryIds
     * @return List <LibraryEntity>
     */
    public List<LibraryEntity> findNotPushLibraryList(String[] libraryIds,String repositoryId,String name) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(LibraryEntity.class)
                .like("name", name)
                .eq("repositoryId", repositoryId);

        if (!ObjectUtils.isEmpty(libraryIds)){
            queryBuilders.notIn("id",libraryIds);
        }
        QueryCondition queryCondition = queryBuilders.get();
        return jpaTemplate.findList(queryCondition,LibraryEntity.class);
    }

    /**
     * 查询不包含的制品
     * @param libraryIds
     * @return List <LibraryEntity>
     */
    public List<LibraryEntity> findNotInLibraryList(String[] libraryIds,String repositoryId,String name) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(LibraryEntity.class)
                .like("name", name)
                .eq("repositoryId", repositoryId);

        if (!ObjectUtils.isEmpty(libraryIds)){
            queryBuilders.notIn("id",libraryIds);
        }
        QueryCondition queryCondition = queryBuilders.get();
        return jpaTemplate.findList(queryCondition,LibraryEntity.class);
    }

    /**
     * 通过制品名字查询制品
     * @param name
     * @return List <LibraryEntity>
     */
    public List<LibraryEntity> findLibraryByCondition(String name,String type,String repId) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryEntity.class)
                .eq("name",name)
                .eq("libraryType",type)
                .eq("repositoryId",repId)
                .get();
        return jpaTemplate.findList(queryCondition,LibraryEntity.class);

    }

    public List<LibraryEntity> findLibraryByCondition(String name, String type, String[] rpyIds) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(LibraryEntity.class)
                .eq("name", name)
                .eq("libraryType", type);
        if (!ObjectUtils.isEmpty(rpyIds)){
            queryBuilders.in("repositoryId", rpyIds);
        }
        QueryCondition queryCondition = queryBuilders.get();
        return jpaTemplate.findList(queryCondition,LibraryEntity.class);
    }


    /**
     * 通过制品库查询制品数量
     * @param repositoryId
     * @return Integer
     */
    public Integer findLibraryNum(String repositoryId,String repositoryType) {
        String sql;
        if (("group").equals(repositoryType)){
            sql="SELECT count(1) FROM (  SELECT li.id FROM pack_repository_group_items reg " +
                    " LEFT JOIN  pack_library li ON reg.repository_id=li.repository_id " +
                    " LEFT JOIN pack_library_version ver ON li.id = ver.library_id "+
                    "WHERE reg.repository_group_id='"+repositoryId+"' GROUP BY li.id HAVING COUNT(ver.id) > 0 ) AS subquery";
        }else {
            sql = "SELECT count(1) FROM ( SELECT li.id FROM pack_library li LEFT JOIN pack_library_version ver ON li.id = ver.library_id " +
                    " WHERE li.repository_id = '" + repositoryId + "'    GROUP BY li.id HAVING COUNT(ver.id) > 0 ) AS subquery";
        }
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(getJdbcTemplate());
        Integer integer = jdbc.queryForObject(sql, new HashMap<String, Object>(), Integer.class);
        return integer;
    }


    public List<LibraryEntity> likeLibraryByName(LibraryQuery libraryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryEntity.class)
                .like("name",libraryQuery.getName())
                .eq("repositoryId", libraryQuery.getRepositoryId())
                .get();
        return jpaTemplate.findList(queryCondition,LibraryEntity.class);
    }


    /**
     * 查找制品是否存在
     * @param rpyId
     * @return
     */
    public List<LibraryEntity> findLibraryByRpyIdAndName(String rpyId,String name ){
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryEntity.class)
                .eq("name",name)
                .eq("repositoryId", rpyId)
                .get();
        return jpaTemplate.findList(queryCondition,LibraryEntity.class);
    }

    /**
     * 通过仓库id、制品名字查询
     * @param repositoryId  仓库id
     * @param name 制品名字
     */
    public List<LibraryEntity> findLibraryList(String repositoryId, String name){
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryEntity.class)
                .eq("name",name)
                .eq("repositoryId", repositoryId)
                .get();
        return jpaTemplate.findList(queryCondition,LibraryEntity.class);
    }

    /**
     * 拼接sql
     */
    public String jointSql(String type){
        if (type.equals("library") ){
            return "FROM   pack_library li LEFT JOIN pack_library_maven lim ON li.id=lim.library_id " +
                    "LEFT JOIN pack_library_version ver ON li.id=ver.library_id "+
                    "LEFT JOIN pack_library_file file ON ver.id=file.library_version_id  WHERE ";
        }else {
            return "FROM  pack_repository re LEFT JOIN pack_library li on re.id=li.repository_id " +
                    "LEFT JOIN pack_library_maven lim ON li.id=lim.library_id " +
                    "LEFT JOIN pack_library_version ver ON li.id=ver.library_id " +
                    "LEFT JOIN pack_library_file file ON ver.id=file.library_version_id  WHERE ";
        }
    }

    /**
     * 首页制品的查询
     */
    public String findCond(LibraryQuery libraryQuery,String sql,String type){
        if (StringUtils.isNotEmpty(libraryQuery.getRepositoryId())){
            sql = sql + " and li.repository_id='" + libraryQuery.getRepositoryId() + "'";
        }

        if (StringUtils.isNotEmpty(libraryQuery.getSearchName())){
            sql = sql + " and (li.name like '%" + libraryQuery.getSearchName() + "%'";

            //maven 类型的制品 查询条件
            if (("maven").equals(libraryQuery.getLibraryType())){
                sql=sql + " OR lim.group_id like '%" + libraryQuery.getSearchName() +"%'"+
                        " OR lim.artifact_id like '%" + libraryQuery.getSearchName() +"%'";
            }
            sql = sql +" OR ver.version like '%" + libraryQuery.getSearchName() +"%')";

        }
        sql=sql+" GROUP BY li.id,lim.id HAVING COUNT(ver.id) > 0";
        if (("count").equals(type)){
            sql= sql +") as lis";
        }
        //查询类型为查询数据且排序有数据
        if (("find").equals(type)){
            if (StringUtils.isNotEmpty(libraryQuery.getSort())){
                sql=sql+" ORDER BY librarySize "+  libraryQuery.getSort();
            }
        }
        return sql;
    }


    /**
     * 仓库里面的查询制品
     * @param libraryQuery
     * @return String
     */
    public String findRepositoryCondition(LibraryQuery libraryQuery,String sql,String type){
        if (ObjectUtils.isEmpty(libraryQuery.getRepositoryIds())){
            sql = sql + "  li.repository_id='" + libraryQuery.getRepositoryId() + "'";
        }else {
            sql = sql + "  li.repository_id in (:repositoryIds)";
        }
        if (!StringUtils.isEmpty(libraryQuery.getGroupId())){
            sql = sql + " and lim.group_id='" + libraryQuery.getGroupId() + "'";
        }
        if (!StringUtils.isEmpty(libraryQuery.getArtifactId())){
            sql = sql + " and lim.artifact_id='" + libraryQuery.getArtifactId()+ "'";
        }
        if(!StringUtils.isEmpty(libraryQuery.getNewVersion())){
            sql = sql + " and ver.version like '%" + libraryQuery.getNewVersion()+ "%'";
        }
        if (!StringUtils.isEmpty(libraryQuery.getName())){
            sql = sql + " and li.name like '%" + libraryQuery.getName()+ "%'";
        }
        sql=sql+" GROUP BY re.id,li.id,lim.id  HAVING COUNT(ver.id) > 0";
        if (("count").equals(type)){
            sql=sql +") as lis";
        }
        //查询类型为查询数据且排序有数据
        if (("find").equals(type)){
            if (StringUtils.isNotEmpty(libraryQuery.getSort())){
                sql=sql+" ORDER BY librarySize "+  libraryQuery.getSort();
            }
        }
        return sql;
    }

}