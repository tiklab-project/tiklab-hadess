package io.tiklab.xpack.scan.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.scan.dao.ScanRecordDao;
import io.tiklab.xpack.scan.entity.ScanRecordEntity;
import io.tiklab.xpack.scan.entity.ScanRelyEntity;
import io.tiklab.xpack.scan.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
* ScanRecordServiceImpl-扫描结果
*/
@Service
public class ScanRecordServiceImpl implements ScanRecordService {

    @Autowired
    ScanRecordDao scanRecordDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    ScanHoleService scanHoleService;

    @Autowired
    ScanRelyService scanRelyService;

    @Override
    public String createScanRecord(@NotNull @Valid ScanRecord scanRecord) {
        ScanRecordEntity scanRecordEntity = BeanMapper.map(scanRecord, ScanRecordEntity.class);
        scanRecordEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return scanRecordDao.createScanRecord(scanRecordEntity);
    }

    @Override
    public void updateScanRecord(@NotNull @Valid ScanRecord scanRecord) {
        ScanRecordEntity scanRecordEntity = BeanMapper.map(scanRecord, ScanRecordEntity.class);
        scanRecordDao.updateScanRecord(scanRecordEntity);
    }

    @Override
    public void deleteScanRecord(@NotNull String id) {
        scanRelyService.deleteScanRelyByCondition("scanRecordId",id);

        scanHoleService.deleteScanHoleByCondition("scanRecordId",id);

        scanRecordDao.deleteScanRecord(id);
    }

    @Override
    public void deleteScanRecordByCondition(String key, String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(ScanRelyEntity.class)
                .eq(key,value)
                .get();
        scanRecordDao.deleteScanRecord(deleteCondition);
    }

    @Override
    public ScanRecord findOne(String id) {
        ScanRecordEntity scanRecordEntity = scanRecordDao.findScanRecord(id);

        ScanRecord scanRecord = BeanMapper.map(scanRecordEntity, ScanRecord.class);
        return scanRecord;
    }

    @Override
    public List<ScanRecord> findList(List<String> idList) {
        List<ScanRecordEntity> scanRecordEntityList =  scanRecordDao.findScanRecordList(idList);

        List<ScanRecord> scanRecordList =  BeanMapper.mapList(scanRecordEntityList,ScanRecord.class);
        return scanRecordList;
    }

    @Override
    public ScanRecord findScanRecord(@NotNull String id) {
        ScanRecord scanRecord = findOne(id);
        joinTemplate.joinQuery(scanRecord);

        List<ScanHole> scanHoleList = scanHoleService.findScanHoleList(new ScanHoleQuery().setScanRecordId(id));
        if (CollectionUtils.isNotEmpty(scanHoleList)){
            scanRecord.setRelyNum(scanHoleList.size());
        }
        return scanRecord;
    }

    @Override
    public List<ScanRecord> findAllScanRecord() {
        List<ScanRecordEntity> scanRecordEntityList =  scanRecordDao.findAllScanRecord();

        List<ScanRecord> scanRecordList =  BeanMapper.mapList(scanRecordEntityList,ScanRecord.class);

        joinTemplate.joinQuery(scanRecordList);

        return scanRecordList;
    }

    @Override
    public List<ScanRecord> findScanRecordList(ScanRecordQuery scanRecordQuery) {
        List<ScanRecordEntity> scanRecordEntityList = scanRecordDao.findScanRecordList(scanRecordQuery);

        List<ScanRecord> scanRecordList = BeanMapper.mapList(scanRecordEntityList,ScanRecord.class);

        joinTemplate.joinQuery(scanRecordList);

        return scanRecordList;
    }

    @Override
    public ScanRecord findNewScanRecord(String scanRecordId) {
        List<ScanRecordEntity> scanRecordEntityList = scanRecordDao.findScanRecordList(new ScanRecordQuery().setScanLibraryId(scanRecordId));
        List<ScanRecord> scanRecordList = BeanMapper.mapList(scanRecordEntityList,ScanRecord.class);
        if (CollectionUtils.isNotEmpty(scanRecordList)){
            List<ScanRecord> scanRecords = scanRecordList.stream().sorted(Comparator.comparing(ScanRecord::getCreateTime).reversed()).collect(Collectors.toList());
            return scanRecords.get(0);
        }
        return null;
    }

    @Override
    public List<ScanRecord> findScanRecordListNoJoin(ScanRecordQuery scanRecordQuery) {
        List<ScanRecordEntity> scanRecordEntityList = scanRecordDao.findScanRecordList(scanRecordQuery);

        List<ScanRecord> scanRecordList = BeanMapper.mapList(scanRecordEntityList,ScanRecord.class);
        return scanRecordList;
    }

    @Override
    public Pagination<ScanRecord> findScanRecordPage(ScanRecordQuery scanRecordQuery) {
        Pagination<ScanRecordEntity>  pagination = scanRecordDao.findScanRecordPage(scanRecordQuery);

        List<ScanRecord> scanRecordList = BeanMapper.mapList(pagination.getDataList(),ScanRecord.class);

        joinTemplate.joinQuery(scanRecordList);

        if (CollectionUtils.isNotEmpty(scanRecordList)){
            scanRecordList = scanRecordList.stream().sorted(Comparator.comparing(ScanRecord::getCreateTime)).collect(Collectors.toList());

        }
        return PaginationBuilder.build(pagination,scanRecordList);
    }

}