package io.tiklab.xpack.library.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.xpack.library.entity.LibraryEntity;
import io.tiklab.xpack.library.model.Library;
import io.tiklab.xpack.library.model.LibraryQuery;
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
    public List<Library> findLibraryListByRepository(LibraryQuery libraryQuery) {
        String sql="SELECT li.* , lim.group_id FROM  pack_repository re LEFT JOIN pack_library li on re.id=li.repository_id " +
                "LEFT JOIN pack_library_maven lim ON li.id=lim.library_id WHERE " ;

        if (!ObjectUtils.isEmpty(libraryQuery.getRepositoryIds())){
            sql = sql + "li.repository_id in (:repositoryIds)";
        }else {
            sql = sql + "li.repository_id='" + libraryQuery.getRepositoryId() + "'";
        }
        if (!StringUtils.isEmpty(libraryQuery.getGroupId())){
             sql = sql + " and lim.group_id='" + libraryQuery.getGroupId() + "'";
        }
        if (!StringUtils.isEmpty(libraryQuery.getArtifactId())){
             sql = sql + " and lim.artifact_id='" + libraryQuery.getArtifactId()+ "'";
        }
        if(!StringUtils.isEmpty(libraryQuery.getNewVersion())){
            sql = sql + " and li.new_version='" + libraryQuery.getNewVersion()+ "'";
        }
        if (!StringUtils.isEmpty(libraryQuery.getName())){
            sql = sql + " and li.name like '%" + libraryQuery.getName()+ "%'";
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("repositoryIds", libraryQuery.getRepositoryIds());

        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(getJdbcTemplate());
        List<Library> query = jdbc.query(sql, paramMap, new BeanPropertyRowMapper(Library.class));
        return query;

    }


    /**
     * 条件查询制品
     * @param libraryQuery
     * @return Pagination <LibraryEntity>
     */
    public List<LibraryEntity> findLibraryListByCondition(LibraryQuery libraryQuery) {
        String sql="SELECT li.* , lim.group_id FROM  pack_repository re LEFT JOIN pack_library li on re.id=li.repository_id " +
                "LEFT JOIN pack_library_maven lim ON li.id=lim.library_id WHERE li.library_type='"+libraryQuery.getLibraryType()+"'";

        if (!ObjectUtils.isEmpty(libraryQuery.getRepositoryIds())){
            sql = sql + "and li.repository_id in (:repositoryIds)";
        }else {
            if (StringUtils.isNotEmpty(libraryQuery.getRepositoryId())){
                sql = sql + "and li.repository_id='" + libraryQuery.getRepositoryId() + "'";
            }
        }
        if(!StringUtils.isEmpty(libraryQuery.getNewVersion())){
            sql = sql + " and li.new_version='" + libraryQuery.getNewVersion()+ "'";
        }
        if (!StringUtils.isEmpty(libraryQuery.getName())){
            sql = sql + " and li.name like '%" + libraryQuery.getName()+ "%'";
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("repositoryIds", libraryQuery.getRepositoryIds());

        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(getJdbcTemplate());
        List<LibraryEntity> query = jdbc.query(sql, paramMap, new BeanPropertyRowMapper(LibraryEntity.class));
        return query;
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

        return jpaTemplate.getJdbcTemplate();
    }

    /**
     * 查询未添加到推送中央仓库的记录的制品列表
     * @param libraryIds
     * @return List <LibraryEntity>
     */
    public List<LibraryEntity> findNotPushLibraryList(String[] libraryIds,String repositoryId,String name) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryEntity.class)
                .notIn("id",libraryIds)
                .like("name",name)
                .eq("repositoryId", repositoryId)
                .get();
        return jpaTemplate.findList(queryCondition,LibraryEntity.class);

    }
}