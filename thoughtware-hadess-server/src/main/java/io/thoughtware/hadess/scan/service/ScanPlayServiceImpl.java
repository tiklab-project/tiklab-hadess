package io.thoughtware.hadess.scan.service;

import io.thoughtware.hadess.timedtask.model.TimeTask;
import io.thoughtware.hadess.timedtask.model.TimeTaskQuery;
import io.thoughtware.hadess.timedtask.service.TimeTaskService;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.hadess.scan.model.*;
import io.thoughtware.toolkit.join.JoinTemplate;
import io.thoughtware.hadess.scan.dao.ScanPlayDao;
import io.thoughtware.hadess.scan.entity.ScanPlayEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;
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
* ScanPlayServiceImpl-扫描结果
*/
@Service
public class ScanPlayServiceImpl implements ScanPlayService {

    @Autowired
    ScanPlayDao scanPlayDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    ScanLibraryService scanLibraryService;

    @Autowired
    ScanRecordService scanRecordService;

    @Autowired
    TimeTaskService timeTaskService;


    @Override
    public String createScanPlay(@NotNull @Valid ScanPlay scanPlay) {
        ScanPlayEntity scanPlayEntity = BeanMapper.map(scanPlay, ScanPlayEntity.class);
        scanPlayEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return scanPlayDao.createScanPlay(scanPlayEntity);
    }

    @Override
    public void updateScanPlay(@NotNull @Valid ScanPlay scanPlay) {
        ScanPlayEntity scanPlayEntity = BeanMapper.map(scanPlay, ScanPlayEntity.class);
        scanPlayEntity.setScanTime(new Timestamp(System.currentTimeMillis()));
        scanPlayDao.updateScanPlay(scanPlayEntity);
    }

    @Override
    public void deleteScanPlay(@NotNull String id) {
        scanPlayDao.deleteScanPlay(id);

        //开启新线程删除关联数据
        Thread thread = new Thread() {
            public void run() {
                deleteSubData(id);
            }
        };
        thread.start();
    }

    @Override
    public void deleteScanPlayByCondition(String key,String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(ScanPlayEntity.class)
                .eq(key, value)
                .get();
        scanPlayDao.deleteScanPlay(deleteCondition);

        //删除制品库时 删除制品库下面的扫描相关数据
        if (("repositoryId").equals(key)){
            List<ScanPlay> scanPlayList = this.findScanPlayList(new ScanPlayQuery().setRepositoryId(value));
            if (CollectionUtils.isNotEmpty(scanPlayList)){
               for (ScanPlay scanPlay:scanPlayList){
                   deleteSubData(scanPlay.getId());
               }
            }
        }
    }

    @Override
    public ScanPlay findOne(String id) {
        ScanPlayEntity scanPlayEntity = scanPlayDao.findScanPlay(id);

        ScanPlay scanPlay = BeanMapper.map(scanPlayEntity, ScanPlay.class);
        return scanPlay;
    }

    @Override
    public List<ScanPlay> findList(List<String> idList) {
        List<ScanPlayEntity> scanPlayEntityList =  scanPlayDao.findScanPlayList(idList);

        List<ScanPlay> scanPlayList =  BeanMapper.mapList(scanPlayEntityList,ScanPlay.class);
        return scanPlayList;
    }

    @Override
    public ScanPlay findScanPlay(@NotNull String id) {
        ScanPlay scanPlay = findOne(id);
        addScanRecord(scanPlay);
        joinTemplate.joinQuery(scanPlay);

        return scanPlay;
    }

    @Override
    public List<ScanPlay> findAllScanPlay() {
        List<ScanPlayEntity> scanPlayEntityList =  scanPlayDao.findAllScanPlay();

        List<ScanPlay> scanPlayList =  BeanMapper.mapList(scanPlayEntityList,ScanPlay.class);

        joinTemplate.joinQuery(scanPlayList);

        return scanPlayList;
    }

    @Override
    public List<ScanPlay> findScanPlayList(ScanPlayQuery scanPlayQuery) {
        List<ScanPlayEntity> scanPlayEntityList = scanPlayDao.findScanPlayList(scanPlayQuery);

        List<ScanPlay> scanPlayList = BeanMapper.mapList(scanPlayEntityList,ScanPlay.class);

        joinTemplate.joinQuery(scanPlayList);

        return scanPlayList;
    }

    @Override
    public Pagination<ScanPlay> findScanPlayPage(ScanPlayQuery scanPlayQuery) {
        Pagination<ScanPlayEntity>  pagination = scanPlayDao.findScanPlayPage(scanPlayQuery);

        List<ScanPlay> scanPlayList = BeanMapper.mapList(pagination.getDataList(),ScanPlay.class);

        if (CollectionUtils.isNotEmpty(scanPlayList)){
            for (ScanPlay scanPlay:scanPlayList){
                List<ScanLibrary> scanLibraryList = scanLibraryService.findScanLibraryList(new ScanLibraryQuery().setScanPlayId(scanPlay.getId()));
                int size = CollectionUtils.isNotEmpty(scanLibraryList) ? scanLibraryList.size() : 0;
                scanPlay.setLibraryNum(size);

                addScanRecord(scanPlay);
            }
        }

        joinTemplate.joinQuery(scanPlayList);

        return PaginationBuilder.build(pagination,scanPlayList);
    }

    /**
     * 添加扫描记录
     * @param scanPlay
     * @return
     */
    public void addScanRecord(ScanPlay scanPlay){
        List<ScanRecord> scanRecordList = scanRecordService.findScanRecordList(new ScanRecordQuery().setScanPlayId(scanPlay.getId()));

        if (CollectionUtils.isNotEmpty(scanRecordList)){
            List<ScanRecord> scanRecords = scanRecordList.stream().sorted(Comparator.comparing(ScanRecord::getCreateTime).reversed()).collect(Collectors.toList());
            ScanRecord scanRecord = scanRecords.get(0);
            if(!ObjectUtils.isEmpty(scanRecord.getScanUser())){
                String userName = StringUtils.isNotEmpty(scanRecord.getScanUser().getNickname()) ? scanRecord.getScanUser().getNickname() :
                        scanRecord.getScanUser().getName();
                scanPlay.setUserName(userName);
            }
            scanPlay.setResult(scanRecord.getScanResult());
            scanPlay.setNewScanTime(scanRecord.getCreateTime());
            scanPlay.setScanGroup(scanRecord.getScanGroup());
            scanPlay.setScanState("true");
            scanPlay.setNewScanRecordId(scanRecord.getId());
        }else {
            scanPlay.setScanState("false");  //没有扫描
        }
    }

    /**
     * 删除扫描子数据
     * @param scanPlayId 扫描计划的id
     * @return
     */
    public void deleteSubData(String scanPlayId){
        //删除扫描计划下面的制品和扫描结果
        List<ScanLibrary> scanLibraryList = scanLibraryService.findScanLibraryList(new ScanLibraryQuery().setScanPlayId(scanPlayId));
        if (CollectionUtils.isNotEmpty(scanLibraryList)){
            for (ScanLibrary scanLibrary:scanLibraryList){
                scanLibraryService.deleteScanLibrary(scanLibrary.getId());
            }
        }

        //删除扫描计划下面的定时任务
        List<TimeTask> timeTaskList = timeTaskService.findTimeTaskList(scanPlayId);
        if (CollectionUtils.isNotEmpty(timeTaskList)){
            for (TimeTask timeTask:timeTaskList){
                timeTaskService.deleteTimeTask(timeTask.getId());
            }
        }
    }
}