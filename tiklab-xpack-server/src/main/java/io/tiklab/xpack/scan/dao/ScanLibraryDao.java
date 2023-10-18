package io.tiklab.xpack.scan.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.xpack.scan.entity.ScanLibraryEntity;
import io.tiklab.xpack.scan.model.ScanLibraryQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ScanLibraryDao-扫描制品数据库访问
 */
@Repository
public class ScanLibraryDao {

    private static Logger logger = LoggerFactory.getLogger(ScanLibraryDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param scanLibraryEntity
     * @return
     */
    public String createScanLibrary(ScanLibraryEntity scanLibraryEntity) {
        return jpaTemplate.save(scanLibraryEntity,String.class);
    }

    /**
     * 更新
     * @param scanLibraryEntity
     */
    public void updateScanLibrary(ScanLibraryEntity scanLibraryEntity){
        jpaTemplate.update(scanLibraryEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteScanLibrary(String id){
        jpaTemplate.delete(ScanLibraryEntity.class,id);
    }

    /**
     * 条件删除存储库
     * @param deleteCondition
     */
    public void deleteScanLibrary(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public ScanLibraryEntity findScanLibrary(String id){
        return jpaTemplate.findOne(ScanLibraryEntity.class,id);
    }

    /**
    * 查询所有存储库
    * @return
    */
    public List<ScanLibraryEntity> findAllScanLibrary() {
        return jpaTemplate.findAll(ScanLibraryEntity.class);
    }

    /**
     * 通过ids查询存储库
     * @param idList
     * @return List <ScanLibraryEntity>
     */
    public List<ScanLibraryEntity> findScanLibraryList(List<String> idList) {
        return jpaTemplate.findList(ScanLibraryEntity.class,idList);
    }

    /**
     * 条件查询存储库
     * @param scanLibraryQuery
     * @return List <ScanLibraryEntity>
     */
    public List<ScanLibraryEntity> findScanLibraryList(ScanLibraryQuery scanLibraryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanLibraryEntity.class)
                .eq("libraryId",scanLibraryQuery.getLibraryId())
                .eq("repositoryId",scanLibraryQuery.getRepositoryId())
                .orders(scanLibraryQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ScanLibraryEntity.class);
    }

    /**
     * 条件分页查询存储库
     * @param scanLibraryQuery
     * @return Pagination <ScanLibraryEntity>
     */
    public Pagination<ScanLibraryEntity> findScanLibraryPage(ScanLibraryQuery scanLibraryQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanLibraryEntity.class)
                .eq("libraryId",scanLibraryQuery.getLibraryId())
                .eq("repositoryId",scanLibraryQuery.getRepositoryId())
                .orders(scanLibraryQuery.getOrderParams())
                .pagination(scanLibraryQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ScanLibraryEntity.class);
    }
}