package io.thoughtware.hadess.scan.service;

import io.thoughtware.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.join.JoinTemplate;
import io.thoughtware.hadess.scan.dao.ScanResultDao;
import io.thoughtware.hadess.scan.entity.ScanResultEntity;
import io.thoughtware.hadess.scan.model.ScanResult;
import io.thoughtware.hadess.scan.model.ScanResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
* ScanResultServiceImpl-扫描结果
*/
@Service
public class ScanResultServiceImpl implements ScanResultService {

    @Autowired
    ScanResultDao scanResultDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createScanResult(@NotNull @Valid ScanResult scanResult) {
        ScanResultEntity scanResultEntity = BeanMapper.map(scanResult, ScanResultEntity.class);
        scanResultEntity.setCreatTime(new Timestamp(System.currentTimeMillis()));
        return scanResultDao.createScanResult(scanResultEntity);
    }

    @Override
    public void updateScanResult(@NotNull @Valid ScanResult scanResult) {
        ScanResultEntity scanResultEntity = BeanMapper.map(scanResult, ScanResultEntity.class);

        scanResultDao.updateScanResult(scanResultEntity);
    }

    @Override
    public void deleteScanResult(@NotNull String id) {
        scanResultDao.deleteScanResult(id);
    }

    @Override
    public void deleteScanResultByCondition(String key,String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(ScanResultEntity.class)
                .eq(key, value)
                .get();
        scanResultDao.deleteScanResult(deleteCondition);
    }

    @Override
    public void deleteScanResultByRecordIds(StringBuilder recordIds) {
        scanResultDao.deleteScanResultByRecordIds(recordIds);
    }

    @Override
    public ScanResult findOne(String id) {
        ScanResultEntity scanResultEntity = scanResultDao.findScanResult(id);

        ScanResult scanResult = BeanMapper.map(scanResultEntity, ScanResult.class);
        return scanResult;
    }

    @Override
    public List<ScanResult> findList(List<String> idList) {
        List<ScanResultEntity> scanResultEntityList =  scanResultDao.findScanResultList(idList);

        List<ScanResult> scanResultList =  BeanMapper.mapList(scanResultEntityList,ScanResult.class);
        return scanResultList;
    }

    @Override
    public ScanResult findScanResult(@NotNull String id) {
        ScanResult scanResult = findOne(id);

        joinTemplate.joinQuery(scanResult);

        return scanResult;
    }

    @Override
    public List<ScanResult> findAllScanResult() {
        List<ScanResultEntity> scanResultEntityList =  scanResultDao.findAllScanResult();

        List<ScanResult> scanResultList =  BeanMapper.mapList(scanResultEntityList,ScanResult.class);

        joinTemplate.joinQuery(scanResultList);

        return scanResultList;
    }

    @Override
    public List<ScanResult> findScanResultList(ScanResultQuery scanResultQuery) {
        List<ScanResultEntity> scanResultEntityList = scanResultDao.findScanResultList(scanResultQuery);

        List<ScanResult> scanResultList = BeanMapper.mapList(scanResultEntityList,ScanResult.class);

        joinTemplate.joinQuery(scanResultList);

        return scanResultList;
    }

    @Override
    public Pagination<ScanResult> findScanResultPage(ScanResultQuery scanResultQuery) {
        Pagination<ScanResultEntity>  pagination = scanResultDao.findScanResultPage(scanResultQuery);

        List<ScanResult> scanResultList = BeanMapper.mapList(pagination.getDataList(),ScanResult.class);

        joinTemplate.joinQuery(scanResultList);

        return PaginationBuilder.build(pagination,scanResultList);
    }

    @Override
    public List<ScanResult> findScanResultByRecordIds(String[] scanRecordIds) {
        List<ScanResultEntity> scanResultEntityList = scanResultDao.findScanResultByRecordIds(scanRecordIds);
        List<ScanResult> scanResultList = BeanMapper.mapList(scanResultEntityList,ScanResult.class);
        return scanResultList;
    }
}