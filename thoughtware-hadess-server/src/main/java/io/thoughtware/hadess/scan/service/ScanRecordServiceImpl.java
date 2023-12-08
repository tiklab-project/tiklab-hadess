package io.thoughtware.hadess.scan.service;

import io.thoughtware.hadess.scan.model.*;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.join.JoinTemplate;
import io.thoughtware.hadess.scan.dao.ScanRecordDao;
import io.thoughtware.hadess.scan.entity.ScanRecordEntity;
import io.thoughtware.hadess.scan.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    ScanResultService scanResultService;

    @Autowired
    ScanRelyService scanRelyService;

    @Autowired
    ScanRecordService scanRecordService;

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

        scanResultService.deleteScanResultByCondition("scanRecordId",id);

        scanRecordDao.deleteScanRecord(id);
    }

    @Override
    public void deleteScanRecordByCondition(String key, String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(ScanRecordEntity.class)
                .eq(key,value)
                .get();
        scanRecordDao.deleteScanRecord(deleteCondition);
    }

    @Override
    public void deleteScanRecordByGroup(String scanGroup) {
        List<ScanRecord> scanRecordList = this.findScanRecordList(new ScanRecordQuery().setScanGroup(scanGroup));
        List<String> stringList = scanRecordList.stream().map(ScanRecord::getId).collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        for (String part : stringList) {
            builder.append("'").append(part).append("',");
        }
        builder.setLength(builder.length() - 1);

        scanRelyService.deleteScanRelyByRecordIds(builder);
        scanResultService.deleteScanResultByRecordIds(builder);
        deleteScanRecordByCondition("scanGroup",scanGroup);

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

        //List<ScanResult> scanResultList = scanResultService.findScanResultList(new ScanResultQuery().setScanRecordId(id));
        List<ScanRely> scanRelyList = scanRelyService.findScanRelyList(new ScanRelyQuery().setScanRecordId(id));
        if (CollectionUtils.isNotEmpty(scanRelyList)){
            scanRecord.setRelyNum(scanRelyList.size());
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

    @Override
    public List<ScanRecord> findScanRecordByPlay(ScanRecordQuery scanRecordQuery) {
        List<ScanRecord> arrayList = new ArrayList<>();
        List<ScanRecord> scanRecordList = this.findScanRecordList(scanRecordQuery);
        if (CollectionUtils.isEmpty(scanRecordList)){
            return null;
        }
        Map<String, List<ScanRecord>> listMap = scanRecordList.stream().collect(Collectors.groupingBy(ScanRecord::getScanGroup));
        Set<String> stringSet = listMap.keySet();
        for (String key:stringSet){
            List<ScanRecord> scanRecords = listMap.get(key);
            arrayList.add(addScanRecord(scanRecords));
        }
        return arrayList;
    }

    @Override
    public ScanRecord findScanRecordByGroup(String scanGroup) {
        List<ScanRecord> scanRecordList = this.findScanRecordList(new ScanRecordQuery().setScanGroup(scanGroup));
        if (CollectionUtils.isEmpty(scanRecordList)){
            return null;
        }
        ScanRecord scanRecord = addScanRecord(scanRecordList);

        List<String> scanRecordIds = scanRecordList.stream().map(ScanRecord::getId).collect(Collectors.toList());
        String[] RecordId = new String[scanRecordIds.size()];
        String[] strings = scanRecordIds.toArray(RecordId);

        List<ScanRely> scanRelyListByRecordIds = scanRelyService.findScanRelyListByRecordIds(strings);
        int size = CollectionUtils.isNotEmpty(scanRelyListByRecordIds) ? scanRelyListByRecordIds.size() : 0;
        scanRecord.setRelyNum(size);

        return scanRecord;
    }

    @Override
    public List<ScanRecord> findHaveHoleRelyTreeList(String scanGroup) {
        List<ScanRecord> scanRecordList = scanRecordService.findScanRecordList(new ScanRecordQuery().setScanGroup(scanGroup));
        if (CollectionUtils.isNotEmpty(scanRecordList)){
            List<ScanRely> relyList=null;

            List<String> groups = scanRecordList.stream().map(ScanRecord::getId).collect(Collectors.toList());
            String[] RecordId = new String[groups.size()];
            String[] strings = groups.toArray(RecordId);
            List<ScanResult> scanResultList = scanResultService.findScanResultByRecordIds(strings);
            if (CollectionUtils.isNotEmpty(scanResultList)){
                List<String> RelyLibraryList = scanResultList.stream().map(ScanResult::getLibraryId).distinct().collect(Collectors.toList());
                List<ScanRely> scanRelyList = scanRelyService.findList(RelyLibraryList);
                //第一级依赖（通过子级依赖查询他的第一）
                List<String> relyOneId = scanRelyList.stream().filter(a -> !ObjectUtils.isEmpty(a.getRelyOneId())).map(ScanRely::getRelyOneId).distinct().collect(Collectors.toList());
                List<String> stringList = scanRelyList.stream().filter(a -> ObjectUtils.isEmpty(a.getRelyOneId())).map(ScanRely::getId).distinct().collect(Collectors.toList());

                List<String> list = Stream.concat(relyOneId.stream(), stringList.stream()).collect(Collectors.toList());

                List<ScanRely> oneRelyList = scanRelyService.findList(list);

                for (ScanRely scanRely:oneRelyList){
                    //直接依赖的漏洞
                    List<ScanResult> scanResult = scanResultList.stream().filter(a -> a.getLibraryId().equals(scanRely.getId())).sorted(Comparator.comparing(ScanResult::getHoleLevel)).collect(Collectors.toList());
                    scanRely.setScanResultList(scanResult);

                    //直接依赖
                    List<ScanRely> scanRelies = scanRelyList.stream().filter(a ->(scanRely.getId()).equals( a.getRelyOneId())).collect(Collectors.toList());
                    for (ScanRely secondRely:scanRelies){
                        List<ScanResult> scanResults = scanResultList.stream().filter(a -> a.getLibraryId().equals(secondRely.getId())).sorted(Comparator.comparing(ScanResult::getHoleLevel)).collect(Collectors.toList());
                        secondRely.setScanResultList(scanResults);
                    }
                    scanRely.setScanRelyList(scanRelies);

                }
                relyList = oneRelyList.stream().sorted(Comparator.comparing(a -> {
                    return a.getScanRelyList().isEmpty() ? 0 : 1;
                })).collect(Collectors.toList());
            }

            for (ScanRecord scanRecord:scanRecordList){
                List<ScanRely> relyList1 = relyList.stream().filter(a -> scanRecord.getId().equals(a.getScanRecordId())).collect(Collectors.toList());
                scanRecord.setScanRelyList(relyList1);
            }
        }
        return scanRecordList;
    }


    public ScanRecord addScanRecord(List<ScanRecord> scanRecords){
        int holeSeverity = scanRecords.stream().mapToInt(ScanRecord::getHoleSeverity).sum();
        int holeHigh = scanRecords.stream().mapToInt(ScanRecord::getHoleHigh).sum();
        int holeMiddle = scanRecords.stream().mapToInt(ScanRecord::getHoleMiddle).sum();
        int holeLow = scanRecords.stream().mapToInt(ScanRecord::getHoleLow).sum();

        ScanRecord scanRecord = new ScanRecord();
        scanRecord.setHoleSeverity(holeSeverity);
        scanRecord.setHoleHigh(holeHigh);
        scanRecord.setHoleMiddle(holeMiddle);
        scanRecord.setHoleLow(holeLow);
        scanRecord.setScanGroup(scanRecords.get(0).getScanGroup());
        scanRecord.setCreateTime(scanRecords.get(0).getCreateTime());

        return scanRecord;
    }

}