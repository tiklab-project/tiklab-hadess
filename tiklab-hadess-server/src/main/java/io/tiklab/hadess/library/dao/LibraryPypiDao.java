package io.tiklab.hadess.library.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.hadess.library.entity.LibraryPypiEntity;
import io.tiklab.hadess.library.model.LibraryPypiQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

/**
 * LibraryPypiDao-Pypi制品数据访问
 */
@Repository
public class LibraryPypiDao {

    private static Logger logger = LoggerFactory.getLogger(LibraryPypiDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param libraryPypiEntity
     * @return
     */
    public String createLibraryPypi(LibraryPypiEntity libraryPypiEntity) {
        return jpaTemplate.save(libraryPypiEntity,String.class);
    }

    /**
     * 更新
     * @param libraryPypiEntity
     */
    public void updateLibraryPypi(LibraryPypiEntity libraryPypiEntity){
        jpaTemplate.update(libraryPypiEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteLibraryPypi(String id){
        jpaTemplate.delete(LibraryPypiEntity.class,id);
    }

    /**
     * 条件删Pypi制品
     * @param deleteCondition
     */
    public void deleteLibraryPypi(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     */
    public LibraryPypiEntity findLibraryPypi(String id){
        return jpaTemplate.findOne(LibraryPypiEntity.class,id);
    }

    /**
    * 查询所有Pypi制品
    */
    public List<LibraryPypiEntity> findAllLibraryPypi() {
        return jpaTemplate.findAll(LibraryPypiEntity.class);
    }

    /**
     * 通过ids查询Pypi制品
     * @param idList
     * @return List <LibraryPypiEntity>
     */
    public List<LibraryPypiEntity> findLibraryPypiList(List<String> idList) {
        return jpaTemplate.findList(LibraryPypiEntity.class,idList);
    }

    /**
     * 条件查询Pypi制品
     * @param libraryPypiQuery
     * @return List <LibraryPypiEntity>
     */
    public List<LibraryPypiEntity> findLibraryPypiList(LibraryPypiQuery libraryPypiQuery) {
        QueryBuilders builders = QueryBuilders.createQuery(LibraryPypiEntity.class)
                .orders(libraryPypiQuery.getOrderParams());
        if (!ObjectUtils.isEmpty(libraryPypiQuery.getLibraryIds())){
            builders.in("libraryId",libraryPypiQuery.getLibraryIds());
        }else {
            builders.eq("libraryId", libraryPypiQuery.getLibraryId());
        }
        QueryCondition queryCondition = builders.get();
        return jpaTemplate.findList(queryCondition,LibraryPypiEntity.class);
    }

    /**
     * 条件分页查询Pypi制品
     * @param libraryPypiQuery
     * @return Pagination <LibraryPypiEntity>
     */
    public Pagination<LibraryPypiEntity> findLibraryPypiPage(LibraryPypiQuery libraryPypiQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryPypiEntity.class)
                .eq("libraryId", libraryPypiQuery.getLibraryId())
                .orders(libraryPypiQuery.getOrderParams())
                .pagination(libraryPypiQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,LibraryPypiEntity.class);
    }


}