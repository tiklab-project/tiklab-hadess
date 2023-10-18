package io.tiklab.xpack.scan.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.scan.dao.ScanHoleDao;
import io.tiklab.xpack.scan.entity.ScanHoleEntity;
import io.tiklab.xpack.scan.model.ScanHole;
import io.tiklab.xpack.scan.model.ScanHoleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
* ScanHoleServiceImpl-扫描结果
*/
@Service
public class ScanHoleServiceImpl implements ScanHoleService {

    @Autowired
    ScanHoleDao scanHoleDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createScanHole(@NotNull @Valid ScanHole scanHole) {
        ScanHoleEntity scanHoleEntity = BeanMapper.map(scanHole, ScanHoleEntity.class);
        scanHoleEntity.setCreatTime(new Timestamp(System.currentTimeMillis()));
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
}