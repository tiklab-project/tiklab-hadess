package io.thoughtware.hadess.library.service;

import io.thoughtware.hadess.library.dao.PullInfoDao;
import io.thoughtware.hadess.library.entity.PullInfoEntity;
import io.thoughtware.hadess.library.model.PullInfo;
import io.thoughtware.hadess.library.model.PullInfoQuery;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.toolkit.join.JoinTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
* PullInfoServiceImpl-制品拉取信息
*/
@Service
public class PullInfoServiceImpl implements PullInfoService {

    @Autowired
    PullInfoDao pullInfoDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Override
    public String createPullInfo(@NotNull @Valid PullInfo pullInfo) {
        PullInfoEntity pullInfoEntity = BeanMapper.map(pullInfo, PullInfoEntity.class);
        pullInfoEntity.setPullCreate(new Timestamp(System.currentTimeMillis()));
        return pullInfoDao.createPullInfo(pullInfoEntity);
    }

    @Override
    public void updatePullInfo(@NotNull @Valid PullInfo pullInfo) {
        PullInfoEntity pullInfoEntity = BeanMapper.map(pullInfo, PullInfoEntity.class);

        pullInfoDao.updatePullInfo(pullInfoEntity);
    }

    @Override
    public void deletePullInfo(@NotNull String id) {
        pullInfoDao.deletePullInfo(id);
    }

    @Override
    public PullInfo findOne(String id) {
        PullInfoEntity pullInfoEntity = pullInfoDao.findPullInfo(id);

        PullInfo pullInfo = BeanMapper.map(pullInfoEntity, PullInfo.class);
        return pullInfo;
    }

    @Override
    public List<PullInfo> findList(List<String> idList) {
        List<PullInfoEntity> pullInfoEntityList =  pullInfoDao.findPullInfoList(idList);

        List<PullInfo> pullInfoList =  BeanMapper.mapList(pullInfoEntityList,PullInfo.class);
        return pullInfoList;
    }

    @Override
    public PullInfo findPullInfo(@NotNull String id) {
        PullInfo pullInfo = findOne(id);

        joinTemplate.joinQuery(pullInfo);

        return pullInfo;
    }

    @Override
    public List<PullInfo> findAllPullInfo() {
        List<PullInfoEntity> pullInfoEntityList =  pullInfoDao.findAllPullInfo();

        List<PullInfo> pullInfoList =  BeanMapper.mapList(pullInfoEntityList,PullInfo.class);

        joinTemplate.joinQuery(pullInfoList);

        return pullInfoList;
    }

    @Override
    public List<PullInfo> findPullInfoList(PullInfoQuery pullInfoQuery) {
        List<PullInfoEntity> pullInfoEntityList = pullInfoDao.findPullInfoList(pullInfoQuery);

        List<PullInfo> pullInfoList = BeanMapper.mapList(pullInfoEntityList,PullInfo.class);

        joinTemplate.joinQuery(pullInfoList);
        return pullInfoList;
    }

    @Override
    public Pagination<PullInfo> findPullInfoPage(PullInfoQuery pullInfoQuery) {
        Pagination<PullInfoEntity>  pagination = pullInfoDao.findPullInfoPage(pullInfoQuery);

        List<PullInfo> pullInfoList = BeanMapper.mapList(pagination.getDataList(),PullInfo.class);

        joinTemplate.joinQuery(pullInfoList);

        return PaginationBuilder.build(pagination,pullInfoList);
    }

}