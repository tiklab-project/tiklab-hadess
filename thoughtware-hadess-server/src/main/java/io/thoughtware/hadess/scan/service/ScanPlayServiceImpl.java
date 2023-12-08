package io.thoughtware.hadess.scan.service;

import io.thoughtware.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.join.JoinTemplate;
import io.thoughtware.hadess.scan.dao.ScanPlayDao;
import io.thoughtware.hadess.scan.entity.ScanPlayEntity;
import io.thoughtware.hadess.scan.model.ScanLibrary;
import io.thoughtware.hadess.scan.model.ScanLibraryQuery;
import io.thoughtware.hadess.scan.model.ScanPlay;
import io.thoughtware.hadess.scan.model.ScanPlayQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

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
    }

    @Override
    public void deleteScanPlayByCondition(String key,String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(ScanPlayEntity.class)
                .eq(key, value)
                .get();
        scanPlayDao.deleteScanPlay(deleteCondition);
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
            }
        }

        joinTemplate.joinQuery(scanPlayList);

        return PaginationBuilder.build(pagination,scanPlayList);
    }
}