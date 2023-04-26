package io.tiklab.xpack.library.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.xpack.library.entity.PullInfoEntity;
import io.tiklab.xpack.library.model.PullInfoQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PullInfoDao-制品拉取信息 数据访问
 */
@Repository
public class PullInfoDao {

    private static Logger logger = LoggerFactory.getLogger(PullInfoDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pullInfoEntity
     * @return
     */
    public String createPullInfo(PullInfoEntity pullInfoEntity) {
        return jpaTemplate.save(pullInfoEntity,String.class);
    }

    /**
     * 更新
     * @param pullInfoEntity
     */
    public void updatePullInfo(PullInfoEntity pullInfoEntity){
        jpaTemplate.update(pullInfoEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deletePullInfo(String id){
        jpaTemplate.delete(PullInfoEntity.class,id);
    }

    /**
     * 条件删除
     * @param deleteCondition
     * @return
     */
    public void deletePullInfo(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public PullInfoEntity findPullInfo(String id){
        return jpaTemplate.findOne(PullInfoEntity.class,id);
    }

    /**
    * 查询所有制品
    * @return
    */
    public List<PullInfoEntity> findAllPullInfo() {
        return jpaTemplate.findAll(PullInfoEntity.class);
    }

    /**
     * 通过ids查询制品拉取信息
     * @param idList
     * @return List <PullInfoEntity>
     */
    public List<PullInfoEntity> findPullInfoList(List<String> idList) {
        return jpaTemplate.findList(PullInfoEntity.class,idList);
    }

    /**
     * 条件查询制品拉取信息
     * @param pullInfoQuery
     * @return List <PullInfoEntity>
     */
    public List<PullInfoEntity> findPullInfoList(PullInfoQuery pullInfoQuery) {
        QueryCondition queryCondition =QueryBuilders.createQuery(PullInfoEntity.class)
                .eq("libraryVersionId", pullInfoQuery.getLibraryVersionId())
                .eq("libraryId", pullInfoQuery.getLibraryId())
                .orders(pullInfoQuery.getOrderParams())
                .get();

        return jpaTemplate.findList(queryCondition,PullInfoEntity.class);
    }

    /**
     * 条件分页查询制品拉取信息
     * @param pullInfoQuery
     * @return Pagination <PullInfoEntity>
     */
    public Pagination<PullInfoEntity> findPullInfoPage(PullInfoQuery pullInfoQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(PullInfoEntity.class)
                .eq("libraryVersionId", pullInfoQuery.getLibraryVersionId())
                .eq("libraryId", pullInfoQuery.getLibraryId())
                .orders(pullInfoQuery.getOrderParams())
                .pagination(pullInfoQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,PullInfoEntity.class);
    }



}