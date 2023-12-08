package io.thoughtware.hadess.library.service;

import io.thoughtware.hadess.library.dao.PushLibraryDao;
import io.thoughtware.hadess.library.entity.PushLibraryEntity;
import io.thoughtware.hadess.library.model.Library;
import io.thoughtware.hadess.library.model.LibraryQuery;
import io.thoughtware.hadess.library.model.PushLibrary;
import io.thoughtware.hadess.library.model.PushLibraryQuery;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.join.JoinTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    PushCenWarehouseImpl pushCenWarehouse;

    @Autowired
    LibraryService libraryService;


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
    public void deleteVersionByCondition(String field, String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(PushLibraryEntity.class)
                .eq(field, value)
                .get();
        pushLibraryDao.deletePushLibrary(deleteCondition);
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

        if (pushLibraryList.stream() != null) {
            pushLibraryList = pushLibraryList.stream().sorted(Comparator.comparing(a ->a.getLibrary().getName())).collect(Collectors.toList());
        }

        return pushLibraryList;
    }




    @Override
    public Pagination<PushLibrary> findPushLibraryPage(PushLibraryQuery pushLibraryQuery) {
        String[] LibraryIdList=null;
        String libraryName = pushLibraryQuery.getLibraryName();
        if (StringUtils.isNotEmpty(libraryName)){
            List<Library> libraryList = libraryService.likeLibraryByName(new LibraryQuery().setRepositoryId(pushLibraryQuery.getRepositoryId()).setName(pushLibraryQuery.getLibraryName()));
            if (CollectionUtils.isNotEmpty(libraryList)){
                List<String> LibraryIds = libraryList.stream().map(Library::getId).distinct().collect(Collectors.toList());
                String[] LibraryIdSize = new String[LibraryIds.size()];
                LibraryIdList = LibraryIds.toArray(LibraryIdSize);
            }else {
                return PaginationBuilder.build(new Pagination<>(),null);
            }
        }

        Pagination<PushLibraryEntity>  pagination = pushLibraryDao.findPushLibraryPage(pushLibraryQuery,LibraryIdList);

        List<PushLibrary> pushLibraryList = BeanMapper.mapList(pagination.getDataList(),PushLibrary.class);

        joinTemplate.joinQuery(pushLibraryList);

        return PaginationBuilder.build(pagination,pushLibraryList);
    }


    @Override
    public String pushCentralWare(String pushLibraryId) {
        PushLibrary library = this.findPushLibrary(pushLibraryId);
        library.setExecState("waite");
        return pushCenWarehouse.pushCentralWare(library);

    }

    @Override
    public List<PushLibrary> pushResult(String repositoryId) {
        return  pushCenWarehouse.pushResult(repositoryId);
    }

}

