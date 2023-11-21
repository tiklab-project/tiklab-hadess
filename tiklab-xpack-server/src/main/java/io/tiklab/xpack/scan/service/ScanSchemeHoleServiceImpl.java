package io.tiklab.xpack.scan.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.scan.dao.ScanSchemeHoleDao;
import io.tiklab.xpack.scan.entity.ScanSchemeHoleEntity;
import io.tiklab.xpack.scan.model.ScanLibrary;
import io.tiklab.xpack.scan.model.ScanLibraryQuery;
import io.tiklab.xpack.scan.model.ScanSchemeHole;
import io.tiklab.xpack.scan.model.ScanSchemeHoleQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
* ScanSchemeHoleServiceImpl-扫描方案漏洞关系
*/
@Service
public class ScanSchemeHoleServiceImpl implements ScanSchemeHoleService {

    @Autowired
    ScanSchemeHoleDao scanSchemeHoleDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    ScanLibraryService scanLibraryService;

    @Override
    public String createScanSchemeHole(@NotNull @Valid ScanSchemeHole scanSchemeHole) {
        ScanSchemeHoleEntity scanSchemeHoleEntity = BeanMapper.map(scanSchemeHole, ScanSchemeHoleEntity.class);
        scanSchemeHoleEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return scanSchemeHoleDao.createScanSchemeHole(scanSchemeHoleEntity);
    }

    @Override
    public void updateScanSchemeHole(@NotNull @Valid ScanSchemeHole scanSchemeHole) {
        ScanSchemeHoleEntity scanSchemeHoleEntity = BeanMapper.map(scanSchemeHole, ScanSchemeHoleEntity.class);
        scanSchemeHoleDao.updateScanSchemeHole(scanSchemeHoleEntity);
    }

    @Override
    public void deleteScanSchemeHole(@NotNull String id) {
        scanSchemeHoleDao.deleteScanSchemeHole(id);
    }

    @Override
    public void deleteScanSchemeHoleByCondition(String key,String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(ScanSchemeHoleEntity.class)
                .eq(key, value)
                .get();
        scanSchemeHoleDao.deleteScanSchemeHole(deleteCondition);
    }

    @Override
    public ScanSchemeHole findOne(String id) {
        ScanSchemeHoleEntity scanSchemeHoleEntity = scanSchemeHoleDao.findScanSchemeHole(id);

        ScanSchemeHole scanSchemeHole = BeanMapper.map(scanSchemeHoleEntity, ScanSchemeHole.class);
        return scanSchemeHole;
    }

    @Override
    public List<ScanSchemeHole> findList(List<String> idList) {
        List<ScanSchemeHoleEntity> scanSchemeHoleEntityList =  scanSchemeHoleDao.findScanSchemeHoleList(idList);

        List<ScanSchemeHole> scanSchemeHoleList =  BeanMapper.mapList(scanSchemeHoleEntityList,ScanSchemeHole.class);
        return scanSchemeHoleList;
    }

    @Override
    public ScanSchemeHole findScanSchemeHole(@NotNull String id) {
        ScanSchemeHole scanSchemeHole = findOne(id);

        joinTemplate.joinQuery(scanSchemeHole);

        return scanSchemeHole;
    }

    @Override
    public List<ScanSchemeHole> findAllScanSchemeHole() {
        List<ScanSchemeHoleEntity> scanSchemeHoleEntityList =  scanSchemeHoleDao.findAllScanSchemeHole();

        List<ScanSchemeHole> scanSchemeHoleList =  BeanMapper.mapList(scanSchemeHoleEntityList,ScanSchemeHole.class);

        joinTemplate.joinQuery(scanSchemeHoleList);

        return scanSchemeHoleList;
    }

    @Override
    public List<ScanSchemeHole> findScanSchemeHoleList(ScanSchemeHoleQuery scanSchemeHoleQuery) {
        List<ScanSchemeHoleEntity> scanSchemeHoleEntityList = scanSchemeHoleDao.findScanSchemeHoleList(scanSchemeHoleQuery);

        List<ScanSchemeHole> scanSchemeHoleList = BeanMapper.mapList(scanSchemeHoleEntityList,ScanSchemeHole.class);

        joinTemplate.joinQuery(scanSchemeHoleList);

        return scanSchemeHoleList;
    }

    @Override
    public Pagination<ScanSchemeHole> findScanSchemeHolePage(ScanSchemeHoleQuery scanSchemeHoleQuery) {
        Pagination<ScanSchemeHoleEntity>  pagination = scanSchemeHoleDao.findScanSchemeHolePage(scanSchemeHoleQuery);

        List<ScanSchemeHole> scanSchemeHoleList = BeanMapper.mapList(pagination.getDataList(),ScanSchemeHole.class);

        joinTemplate.joinQuery(scanSchemeHoleList);

        return PaginationBuilder.build(pagination,scanSchemeHoleList);
    }
}