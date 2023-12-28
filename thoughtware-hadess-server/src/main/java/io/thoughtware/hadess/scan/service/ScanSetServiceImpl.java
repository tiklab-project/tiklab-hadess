package io.thoughtware.hadess.scan.service;

import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.toolkit.join.JoinTemplate;
import io.thoughtware.hadess.scan.dao.ScanSetDao;
import io.thoughtware.hadess.scan.entity.ScanSetEntity;
import io.thoughtware.hadess.scan.model.ScanSet;
import io.thoughtware.hadess.scan.model.ScanSetQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanSetServiceImpl-扫描设置
*/
@Service
public class ScanSetServiceImpl implements ScanSetService {

    @Autowired
    ScanSetDao scanSetDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createScanSet(@NotNull @Valid ScanSet scanSet) {
        ScanSetEntity scanSetEntity = BeanMapper.map(scanSet, ScanSetEntity.class);

        return scanSetDao.createScanSet(scanSetEntity);
    }

    @Override
    public void updateScanSet(@NotNull @Valid ScanSet scanSet) {
        ScanSetEntity scanSetEntity = BeanMapper.map(scanSet, ScanSetEntity.class);

        scanSetDao.updateScanSet(scanSetEntity);
    }

    @Override
    public void deleteScanSet(@NotNull String id) {
        scanSetDao.deleteScanSet(id);
    }

    @Override
    public ScanSet findOne(String id) {
        ScanSetEntity scanSetEntity = scanSetDao.findScanSet(id);

        ScanSet scanSet = BeanMapper.map(scanSetEntity, ScanSet.class);
        return scanSet;
    }

    @Override
    public List<ScanSet> findList(List<String> idList) {
        List<ScanSetEntity> scanSetEntityList =  scanSetDao.findScanSetList(idList);

        List<ScanSet> scanSetList =  BeanMapper.mapList(scanSetEntityList,ScanSet.class);
        return scanSetList;
    }

    @Override
    public ScanSet findScanSet(@NotNull String id) {
        ScanSet scanSet = findOne(id);

        joinTemplate.joinQuery(scanSet);

        return scanSet;
    }

    @Override
    public List<ScanSet> findAllScanSet() {
        List<ScanSetEntity> scanSetEntityList =  scanSetDao.findAllScanSet();

        List<ScanSet> scanSetList =  BeanMapper.mapList(scanSetEntityList,ScanSet.class);

        joinTemplate.joinQuery(scanSetList);

        return scanSetList;
    }

    @Override
    public List<ScanSet> findScanSetList(ScanSetQuery scanSetQuery) {
        List<ScanSetEntity> scanSetEntityList = scanSetDao.findScanSetList(scanSetQuery);

        List<ScanSet> scanSetList = BeanMapper.mapList(scanSetEntityList,ScanSet.class);

        joinTemplate.joinQuery(scanSetList);

        return scanSetList;
    }


}