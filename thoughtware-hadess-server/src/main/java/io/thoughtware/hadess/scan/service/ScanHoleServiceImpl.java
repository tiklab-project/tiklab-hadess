package io.thoughtware.hadess.scan.service;

import io.thoughtware.hadess.scan.model.*;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.core.page.Page;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.join.JoinTemplate;
import io.thoughtware.hadess.scan.dao.ScanHoleDao;
import io.thoughtware.hadess.scan.entity.ScanHoleEntity;
import io.thoughtware.hadess.scan.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
* ScanHoleServiceImpl-扫描漏洞
*/
@Service
public class ScanHoleServiceImpl implements ScanHoleService {

    @Autowired
    ScanHoleDao scanHoleDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    ScanLibraryService scanLibraryService;

    @Autowired
    ScanSchemeHoleService schemeHoleService;

    @Autowired
    ScanSchemeService scanSchemeService;

    @Override
    public String createScanHole(@NotNull @Valid ScanHole scanHole) {
        ScanHoleEntity scanHoleEntity = BeanMapper.map(scanHole, ScanHoleEntity.class);
        scanHoleEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return scanHoleDao.createScanHole(scanHoleEntity);
    }

    @Override
    public void updateScanHole(@NotNull @Valid ScanHole scanHole) {
        ScanHoleEntity scanHoleEntity = BeanMapper.map(scanHole, ScanHoleEntity.class);
        scanHoleDao.updateScanHole(scanHoleEntity);
    }

    @Override
    public void deleteScanHole(@NotNull String id) {
        scanHoleDao.deleteScanHole(id);
    }

    @Override
    public void deleteScanHoleByCondition(String key,String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(ScanHoleEntity.class)
                .eq(key, value)
                .get();
        scanHoleDao.deleteScanHole(deleteCondition);
    }

    @Override
    public ScanHole findOne(String id) {
        ScanHoleEntity scanHoleEntity = scanHoleDao.findScanHole(id);

        ScanHole scanHole = BeanMapper.map(scanHoleEntity, ScanHole.class);
        return scanHole;
    }

    @Override
    public List<ScanHole> findList(List<String> idList) {
        List<ScanHoleEntity> scanHoleEntityList =  scanHoleDao.findScanHoleList(idList);

        List<ScanHole> scanHoleList =  BeanMapper.mapList(scanHoleEntityList,ScanHole.class);
        return scanHoleList;
    }

    @Override
    public ScanHole findScanHole(@NotNull String id) {
        ScanHole scanHole = findOne(id);

        joinTemplate.joinQuery(scanHole);

        return scanHole;
    }

    @Override
    public List<ScanHole> findAllScanHole() {
        List<ScanHoleEntity> scanHoleEntityList =  scanHoleDao.findAllScanHole();

        List<ScanHole> scanHoleList =  BeanMapper.mapList(scanHoleEntityList,ScanHole.class);

        joinTemplate.joinQuery(scanHoleList);

        return scanHoleList;
    }

    @Override
    public List<ScanHole> findScanHoleList(ScanHoleQuery scanHoleQuery) {
        List<ScanHoleEntity> scanHoleEntityList = scanHoleDao.findScanHoleList(scanHoleQuery);

        List<ScanHole> scanHoleList = BeanMapper.mapList(scanHoleEntityList,ScanHole.class);

        joinTemplate.joinQuery(scanHoleList);

        return scanHoleList;
    }

    @Override
    public Pagination<ScanHole> findScanHolePage(ScanHoleQuery scanHoleQuery) {
        Pagination<ScanHoleEntity>  pagination = scanHoleDao.findScanHolePage(scanHoleQuery);

        List<ScanHole> scanHoleList = BeanMapper.mapList(pagination.getDataList(),ScanHole.class);

        joinTemplate.joinQuery(scanHoleList);

        return PaginationBuilder.build(pagination,scanHoleList);
    }

    @Override
    public Pagination<ScanHole> findSchemeHolePage(ScanHoleQuery scanHoleQuery) {
        Pagination pagination = new Pagination();
        Pagination<ScanSchemeHole> scanSchemeHolePage = schemeHoleService.findScanSchemeHolePage(new ScanSchemeHoleQuery().setScanSchemeId(scanHoleQuery.getScanSchemeId()));
        List<ScanSchemeHole> schemeHoleList = scanSchemeHolePage.getDataList();
        if (CollectionUtils.isNotEmpty(schemeHoleList)){
            List<String> stringList = schemeHoleList.stream().map(ScanSchemeHole::getScanHoleId).collect(Collectors.toList());
            String[] strings = new String[stringList.size()];
            String[] holeIds = stringList.toArray(strings);

            List<ScanHoleEntity> scanHoleEntity = scanHoleDao.findScanHoleByIds(holeIds);
            List<ScanHole> scanHoleList = BeanMapper.mapList(scanHoleEntity,ScanHole.class);
            pagination.setDataList(scanHoleList);
            pagination.setTotalPage(scanSchemeHolePage.getTotalPage());
            pagination.setCurrentPage(scanSchemeHolePage.getCurrentPage());
        }
        return pagination;
    }

    @Override
    public Pagination<ScanHole> findNotScanHolePage(ScanHoleQuery scanHoleQuery) {
        ScanScheme scanScheme = scanSchemeService.findScanScheme(scanHoleQuery.getScanSchemeId());
        if (ObjectUtils.isEmpty(scanScheme)){
            return null;
        }
        //制品方案下面的漏洞
        List<ScanSchemeHole> schemeHoleList = schemeHoleService.findScanSchemeHoleList(new ScanSchemeHoleQuery().setScanSchemeId(scanHoleQuery.getScanSchemeId()));

        return null;
    }


}