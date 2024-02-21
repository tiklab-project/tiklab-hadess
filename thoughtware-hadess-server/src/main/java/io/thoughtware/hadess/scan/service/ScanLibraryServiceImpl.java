package io.thoughtware.hadess.scan.service;

import io.thoughtware.hadess.scan.model.*;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.toolkit.join.JoinTemplate;
import io.thoughtware.hadess.scan.dao.ScanLibraryDao;
import io.thoughtware.hadess.scan.entity.ScanLibraryEntity;
import io.thoughtware.hadess.scan.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
* ScanLibraryServiceImpl-扫描结果
*/
@Service
public class ScanLibraryServiceImpl implements ScanLibraryService {

    @Autowired
    ScanLibraryDao scanLibraryDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    ScanResultService scanResultService;

    @Autowired
    ScanRelyService scanRelyService;

    @Autowired
    ScanRecordService scanRecordService;

    @Override
    public String createScanLibrary(@NotNull @Valid ScanLibrary scanLibrary) {
        ScanLibraryEntity scanLibraryEntity = BeanMapper.map(scanLibrary, ScanLibraryEntity.class);
        scanLibraryEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return scanLibraryDao.createScanLibrary(scanLibraryEntity);
    }

    @Override
    public void updateScanLibrary(@NotNull @Valid ScanLibrary scanLibrary) {
        ScanLibraryEntity scanLibraryEntity = BeanMapper.map(scanLibrary, ScanLibraryEntity.class);
        scanLibraryDao.updateScanLibrary(scanLibraryEntity);
    }

    @Override
    public void deleteScanLibrary(@NotNull String id) {
        scanRecordService.deleteScanRecordByCondition("scanLibraryId",id);

        scanRelyService.deleteScanRelyByCondition("scanLibraryId",id);

        scanResultService.deleteScanResultByCondition("scanLibraryId",id);

        scanLibraryDao.deleteScanLibrary(id);
    }

    @Override
    public ScanLibrary findOne(String id) {
        ScanLibraryEntity scanLibraryEntity = scanLibraryDao.findScanLibrary(id);

        ScanLibrary scanLibrary = BeanMapper.map(scanLibraryEntity, ScanLibrary.class);
        return scanLibrary;
    }

    @Override
    public List<ScanLibrary> findList(List<String> idList) {
        List<ScanLibraryEntity> scanLibraryEntityList =  scanLibraryDao.findScanLibraryList(idList);

        List<ScanLibrary> scanLibraryList =  BeanMapper.mapList(scanLibraryEntityList,ScanLibrary.class);

        if (CollectionUtils.isNotEmpty(scanLibraryList)){
            for (ScanLibrary scanLibrary:scanLibraryList){
                ScanRecord newScanRecord = scanRecordService.findNewScanRecord(scanLibrary.getId());
                scanLibrary.setScanRecord(newScanRecord);
            }
        }
        return scanLibraryList;
    }

    @Override
    public ScanLibrary findScanLibrary(@NotNull String id) {
        ScanLibrary scanLibrary = findOne(id);
        joinTemplate.joinQuery(scanLibrary);
        return scanLibrary;
    }

    @Override
    public List<ScanLibrary> findAllScanLibrary() {
        List<ScanLibraryEntity> scanLibraryEntityList =  scanLibraryDao.findAllScanLibrary();

        List<ScanLibrary> scanLibraryList =  BeanMapper.mapList(scanLibraryEntityList,ScanLibrary.class);

        joinTemplate.joinQuery(scanLibraryList);

        return scanLibraryList;
    }

    @Override
    public List<ScanLibrary> findScanLibraryList(ScanLibraryQuery scanLibraryQuery) {
        List<ScanLibraryEntity> scanLibraryEntityList = scanLibraryDao.findScanLibraryList(scanLibraryQuery);

        List<ScanLibrary> scanLibraryList = BeanMapper.mapList(scanLibraryEntityList,ScanLibrary.class);

        joinTemplate.joinQuery(scanLibraryList);

        return scanLibraryList;
    }

    @Override
    public List<ScanLibrary> findScanLibraryListNoJoin(ScanLibraryQuery scanLibraryQuery) {
        List<ScanLibraryEntity> scanLibraryEntityList = scanLibraryDao.findScanLibraryList(scanLibraryQuery);

        List<ScanLibrary> scanLibraryList = BeanMapper.mapList(scanLibraryEntityList,ScanLibrary.class);
        return scanLibraryList;
    }

    @Override
    public Pagination<ScanLibrary> findScanLibraryPage(ScanLibraryQuery scanLibraryQuery) {
        Pagination<ScanLibraryEntity>  pagination = scanLibraryDao.findScanLibraryPage(scanLibraryQuery);

        List<ScanLibrary> scanLibraryList = BeanMapper.mapList(pagination.getDataList(),ScanLibrary.class);

        joinTemplate.joinQuery(scanLibraryList);

        if (CollectionUtils.isNotEmpty(scanLibraryList)){
            scanLibraryList = scanLibraryList.stream().sorted(Comparator.comparing(ScanLibrary::getCreateTime))
                    .sorted(Comparator.comparing(ScanLibrary::getScanState)).collect(Collectors.toList());

            List<ScanRely> scanRelyList = scanRelyService.findScanRelyList(new ScanRelyQuery().setGeneralRecordId(scanLibraryQuery.getGeneralRecordId()));

            for (ScanLibrary scanLibrary:scanLibraryList){

                List<ScanRely> scanRelies = scanRelyList.stream().filter(a -> scanLibrary.getId().equals(a.getScanLibrary().getId())).collect(Collectors.toList());
                if (!ObjectUtils.isEmpty(scanRelies)){
                    scanLibrary.setRelyNum(scanRelies.size());
                    scanLibrary.setScanRecordId(scanRelies.get(0).getScanRecordId());
                    scanLibrary.setScanTime(scanRelies.get(0).getCreateTime());
                }
            }
        }
        return PaginationBuilder.build(pagination,scanLibraryList);
    }

}