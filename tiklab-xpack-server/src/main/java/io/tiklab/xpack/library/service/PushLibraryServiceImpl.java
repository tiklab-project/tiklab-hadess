package io.tiklab.xpack.library.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.library.dao.PushLibraryDao;
import io.tiklab.xpack.library.entity.PushLibraryEntity;
import io.tiklab.xpack.library.model.PushLibrary;
import io.tiklab.xpack.library.model.PushLibraryQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* PushLibraryServiceImpl-制品
*/
@Service
public class PushLibraryServiceImpl implements PushLibraryService {

    private static Logger logger = LoggerFactory.getLogger(PushLibraryServiceImpl.class);
    @Autowired
    PushLibraryDao pushLibraryDao;

    @Autowired
    JoinTemplate joinTemplate;


    @Override
    public String createPushLibrary(@NotNull @Valid PushLibrary pushLibrary) {
        PushLibraryEntity pushLibraryEntity = BeanMapper.map(pushLibrary, PushLibraryEntity.class);
        return pushLibraryDao.createPushLibrary(pushLibraryEntity);
    }

    @Override
    public void updatePushLibrary(@NotNull @Valid PushLibrary pushLibrary) {
        PushLibraryEntity pushLibraryEntity = BeanMapper.map(pushLibrary, PushLibraryEntity.class);
        pushLibraryDao.updatePushLibrary(pushLibraryEntity);
    }

    @Override
    public void deletePushLibrary(@NotNull String id) {


        pushLibraryDao.deletePushLibrary(id);
    }


    @Override
    public PushLibrary findOne(String id) {
        PushLibraryEntity pushLibraryEntity = pushLibraryDao.findPushLibrary(id);

        PushLibrary pushLibrary = BeanMapper.map(pushLibraryEntity, PushLibrary.class);

        return pushLibrary;
    }

    @Override
    public List<PushLibrary> findList(List<String> idList) {
        List<PushLibraryEntity> pushLibraryEntityList =  pushLibraryDao.findPushLibraryList(idList);

        List<PushLibrary> pushLibraryList =  BeanMapper.mapList(pushLibraryEntityList,PushLibrary.class);
        return pushLibraryList;
    }

    @Override
    public PushLibrary findPushLibrary(@NotNull String id) {
        PushLibrary pushLibrary = findOne(id);

        joinTemplate.joinQuery(pushLibrary);

        return pushLibrary;
    }

    @Override
    public List<PushLibrary> findAllPushLibrary() {
        List<PushLibraryEntity> pushLibraryEntityList =  pushLibraryDao.findAllPushLibrary();

        List<PushLibrary> pushLibraryList =  BeanMapper.mapList(pushLibraryEntityList,PushLibrary.class);

        joinTemplate.joinQuery(pushLibraryList);

        return pushLibraryList;
    }

    @Override
    public List<PushLibrary> findPushLibraryList(PushLibraryQuery pushLibraryQuery) {

        List<PushLibraryEntity> pushLibraryEntityList = pushLibraryDao.findPushLibraryList(pushLibraryQuery);

        List<PushLibrary> pushLibraryList = BeanMapper.mapList(pushLibraryEntityList,PushLibrary.class);

        joinTemplate.joinQuery(pushLibraryList);

        return pushLibraryList;
    }




    @Override
    public Pagination<PushLibrary> findPushLibraryPage(PushLibraryQuery pushLibraryQuery) {
        Pagination<PushLibraryEntity>  pagination = pushLibraryDao.findPushLibraryPage(pushLibraryQuery);

        List<PushLibrary> pushLibraryList = BeanMapper.mapList(pagination.getDataList(),PushLibrary.class);

        joinTemplate.joinQuery(pushLibraryList);

        return PaginationBuilder.build(pagination,pushLibraryList);
    }

}

