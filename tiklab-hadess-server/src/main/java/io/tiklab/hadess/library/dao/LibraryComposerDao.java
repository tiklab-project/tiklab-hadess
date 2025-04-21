package io.tiklab.hadess.library.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.hadess.library.entity.LibraryComposerEntity;
import io.tiklab.hadess.library.entity.LibraryComposerEntity;
import io.tiklab.hadess.library.model.LibraryComposerQuery;
import io.tiklab.hadess.library.model.LibraryComposerQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * LibraryComposerDao-Composer制品数据访问
 */
@Repository
public class LibraryComposerDao {

    private static Logger logger = LoggerFactory.getLogger(LibraryComposerDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param libraryComposerEntity
     * @return
     */
    public String createLibraryComposer(LibraryComposerEntity libraryComposerEntity) {
        return jpaTemplate.save(libraryComposerEntity,String.class);
    }

    /**
     * 更新
     * @param libraryComposerEntity
     */
    public void updateLibraryComposer(LibraryComposerEntity libraryComposerEntity){
        jpaTemplate.update(libraryComposerEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteLibraryComposer(String id){
        jpaTemplate.delete(LibraryComposerEntity.class,id);
    }

    /**
     * 条件删Composer制品
     * @param deleteCondition
     */
    public void deleteLibraryComposer(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     */
    public LibraryComposerEntity findLibraryComposer(String id){
        return jpaTemplate.findOne(LibraryComposerEntity.class,id);
    }

    /**
    * 查询所有Composer制品
    */
    public List<LibraryComposerEntity> findAllLibraryComposer() {
        return jpaTemplate.findAll(LibraryComposerEntity.class);
    }

    /**
     * 通过ids查询Composer制品
     * @param idList
     * @return List <LibraryComposerEntity>
     */
    public List<LibraryComposerEntity> findLibraryComposerList(List<String> idList) {
        return jpaTemplate.findList(LibraryComposerEntity.class,idList);
    }

    /**
     * 条件查询Composer制品
     * @param libraryComposerQuery
     * @return List <LibraryComposerEntity>
     */
    public List<LibraryComposerEntity> findLibraryComposerList(LibraryComposerQuery libraryComposerQuery) {
        QueryBuilders builders = QueryBuilders.createQuery(LibraryComposerEntity.class)
                .orders(libraryComposerQuery.getOrderParams());
        if (!ObjectUtils.isEmpty(libraryComposerQuery.getLibraryIds())){
            builders.in("libraryId",libraryComposerQuery.getLibraryIds());
        }else {
            builders.eq("libraryId", libraryComposerQuery.getLibraryId());
        }
        QueryCondition queryCondition = builders.get();
        return jpaTemplate.findList(queryCondition,LibraryComposerEntity.class);
    }

    /**
     * 条件分页查询Composer制品
     * @param libraryComposerQuery
     * @return Pagination <LibraryComposerEntity>
     */
    public Pagination<LibraryComposerEntity> findLibraryComposerPage(LibraryComposerQuery libraryComposerQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(LibraryComposerEntity.class)
                .eq("libraryId", libraryComposerQuery.getLibraryId())
                .orders(libraryComposerQuery.getOrderParams())
                .pagination(libraryComposerQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,LibraryComposerEntity.class);
    }


}