package io.tiklab.xpack.scan.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.scan.dao.ScanRelyDao;
import io.tiklab.xpack.scan.entity.ScanRelyEntity;
import io.tiklab.xpack.scan.model.ScanHole;
import io.tiklab.xpack.scan.model.ScanHoleQuery;
import io.tiklab.xpack.scan.model.ScanRely;
import io.tiklab.xpack.scan.model.ScanRelyQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* ScanRelyServiceImpl-扫描依赖
*/
@Service
public class ScanRelyServiceImpl implements ScanRelyService {

    @Autowired
    ScanRelyDao scanRelyDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    ScanHoleService scanHoleService;

    @Override
    public String createScanRely(@NotNull @Valid ScanRely scanRely) {
        ScanRelyEntity scanRelyEntity = BeanMapper.map(scanRely, ScanRelyEntity.class);

        return scanRelyDao.createScanRely(scanRelyEntity);
    }

    @Override
    public void updateScanRely(@NotNull @Valid ScanRely scanRely) {
        ScanRelyEntity scanRelyEntity = BeanMapper.map(scanRely, ScanRelyEntity.class);
        scanRelyEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        scanRelyDao.updateScanRely(scanRelyEntity);
    }

    @Override
    public void deleteScanRely(@NotNull String id) {
        scanRelyDao.deleteScanRely(id);
    }

    @Override
    public void deleteScanRelyByCondition(String key,String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(ScanRelyEntity.class)
                .eq(key, value)
                .get();
        scanRelyDao.deleteScanRely(deleteCondition);
    }

    @Override
    public ScanRely findOne(String id) {
        ScanRelyEntity scanRelyEntity = scanRelyDao.findScanRely(id);

        ScanRely scanRely = BeanMapper.map(scanRelyEntity, ScanRely.class);
        return scanRely;
    }

    @Override
    public List<ScanRely> findList(List<String> idList) {
        List<ScanRelyEntity> scanRelyEntityList =  scanRelyDao.findScanRelyList(idList);

        List<ScanRely> scanRelyList =  BeanMapper.mapList(scanRelyEntityList,ScanRely.class);
        return scanRelyList;
    }

    @Override
    public ScanRely findScanRely(@NotNull String id) {
        ScanRely scanRely = findOne(id);

        joinTemplate.joinQuery(scanRely);

        return scanRely;
    }

    @Override
    public List<ScanRely> findAllScanRely() {
        List<ScanRelyEntity> scanRelyEntityList =  scanRelyDao.findAllScanRely();

        List<ScanRely> scanRelyList =  BeanMapper.mapList(scanRelyEntityList,ScanRely.class);

        joinTemplate.joinQuery(scanRelyList);

        return scanRelyList;
    }

    @Override
    public List<ScanRely> findScanRelyList(ScanRelyQuery scanRelyQuery) {
        List<ScanRelyEntity> scanRelyEntityList = scanRelyDao.findScanRelyList(scanRelyQuery);

        List<ScanRely> scanRelyList = BeanMapper.mapList(scanRelyEntityList,ScanRely.class);

       // joinTemplate.joinQuery(scanRelyList);

        return scanRelyList;
    }

    @Override
    public Pagination<ScanRely> findScanRelyPage(ScanRelyQuery scanRelyQuery) {
        Pagination<ScanRelyEntity>  pagination = scanRelyDao.findScanRelyPage(scanRelyQuery);

        List<ScanRely> scanRelyList = BeanMapper.mapList(pagination.getDataList(),ScanRely.class);

        joinTemplate.joinQuery(scanRelyList);

        return PaginationBuilder.build(pagination,scanRelyList);
    }

    @Override
    public List<ScanRely> findScanRelyTreeList(ScanRelyQuery scanRelyQuery) {
        List<ScanRely> scanRelyList = this.findScanRelyList(scanRelyQuery);

        if (CollectionUtils.isNotEmpty(scanRelyList)){
            //第一层依赖
            List<ScanRely> firstScanRely = scanRelyList.stream().filter(a -> ObjectUtils.isEmpty(a.getRelyOneId())).collect(Collectors.toList());

           for (ScanRely scanRely:firstScanRely){
               List<ScanRely> scanRelies = scanRelyList.stream().filter(a ->(scanRely.getId()).equals( a.getRelyOneId())).collect(Collectors.toList());
              // setChildTree(scanRelyList,scanRelies);
               scanRely.setScanRelyList(scanRelies);
           }

           return firstScanRely;

        }
        return scanRelyList;
    }

    @Override
    public List<ScanRely> findHaveHoleRelyTreeList(ScanRelyQuery scanRelyQuery) {
        List<ScanHole> scanHoleList = scanHoleService.findScanHoleList(new ScanHoleQuery().
                setScanRecordId(scanRelyQuery.getScanRecordId()).setHoleType("relyLibrary"));
        if (CollectionUtils.isNotEmpty(scanHoleList)){
            List<String> RelyLibraryList = scanHoleList.stream().map(ScanHole::getLibraryId).distinct().collect(Collectors.toList());
            List<ScanRely> scanRelyList = this.findList(RelyLibraryList);
            //第一级依赖（通过子级依赖查询他的第一）
            List<String> relyOneId = scanRelyList.stream().filter(a -> !ObjectUtils.isEmpty(a.getRelyOneId())).map(ScanRely::getRelyOneId).distinct().collect(Collectors.toList());
            List<String> stringList = scanRelyList.stream().filter(a -> ObjectUtils.isEmpty(a.getRelyOneId())).map(ScanRely::getId).distinct().collect(Collectors.toList());

            List<String> list = Stream.concat(relyOneId.stream(), stringList.stream()).collect(Collectors.toList());

            List<ScanRely> oneRelyList = this.findList(list);

            for (ScanRely scanRely:oneRelyList){
                //直接依赖的漏洞
                List<ScanHole> scanHole = scanHoleList.stream().filter(a -> a.getLibraryId().equals(scanRely.getId())).sorted(Comparator.comparing(ScanHole::getHoleLevel)).collect(Collectors.toList());
                scanRely.setScanHoleList(scanHole);

                //直接依赖
                List<ScanRely> scanRelies = scanRelyList.stream().filter(a ->(scanRely.getId()).equals( a.getRelyOneId())).collect(Collectors.toList());
                for (ScanRely secondRely:scanRelies){
                    List<ScanHole> scanHoles = scanHoleList.stream().filter(a -> a.getLibraryId().equals(secondRely.getId())).sorted(Comparator.comparing(ScanHole::getHoleLevel)).collect(Collectors.toList());
                    secondRely.setScanHoleList(scanHoles);
                }
                scanRely.setScanRelyList(scanRelies);

            }
            List<ScanRely> relyList = oneRelyList.stream().sorted(Comparator.comparing(a -> {
                return a.getScanRelyList().isEmpty() ? 0 : 1;
            })).collect(Collectors.toList());

            return relyList;
        }
        return null;
    }

    /**
     * 添加child
     * @param scanRelyList
     * @param scanRelyAll
     * @return
     */
    public void setChildTree(List<ScanRely> scanRelyAll,List<ScanRely> scanRelyList){
        for (ScanRely scanRely:scanRelyList){
            List<ScanRely> scanRelies = scanRelyAll.stream().filter(a ->(scanRely.getId()).equals(a.getRelyParentId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(scanRelies)){
                scanRely.setScanRelyList(scanRelies);
                setChildTree(scanRelyAll,scanRelies);
            }
        }
    }
}