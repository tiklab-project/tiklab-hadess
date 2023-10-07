package io.tiklab.xpack.scan.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.scan.dao.ScanResultDao;
import io.tiklab.xpack.scan.entity.ScanResultEntity;
import io.tiklab.xpack.scan.model.ScanResult;
import io.tiklab.xpack.scan.model.ScanResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanResultServiceImpl-扫描结果
*/
@Service
public class ScanRelyServiceImpl implements ScanResultService {

    @Autowired
    ScanResultDao scanResultDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createScanResult(@NotNull @Valid ScanResult scanResult) {
        ScanResultEntity scanResultEntity = BeanMapper.map(scanResult, ScanResultEntity.class);

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
}