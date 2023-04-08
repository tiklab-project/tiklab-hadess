package io.tiklab.xpack.library.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.library.dao.PullInfoDao;
import io.tiklab.xpack.library.entity.PullInfoEntity;
import io.tiklab.xpack.library.model.PullInfo;
import io.tiklab.xpack.library.model.PullInfoQuery;
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