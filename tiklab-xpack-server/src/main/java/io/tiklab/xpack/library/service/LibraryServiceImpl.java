package io.tiklab.xpack.library.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.xpack.library.dao.LibraryDao;
import io.tiklab.xpack.library.model.*;
import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.library.entity.LibraryEntity;

import io.tiklab.xpack.repository.model.Repository;
import io.tiklab.xpack.repository.model.RepositoryGroup;
import io.tiklab.xpack.repository.model.RepositoryGroupQuery;
import io.tiklab.xpack.repository.service.RepositoryGroupService;
import io.tiklab.xpack.repository.service.RepositoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
* LibraryServiceImpl-制品
*/
@Service
public class LibraryServiceImpl implements LibraryService {

    private static Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);
    @Autowired
    LibraryDao libraryDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    PushLibraryService pushLibraryService;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    LibraryMavenService libraryMavenService;
    
    @Autowired
    RepositoryGroupService repositoryGroupService;

    @Value("${repository.test:null}")
    String testLibrary;

    Map data=new HashMap();

    @Override
    public String createLibrary(@NotNull @Valid Library library) {
        LibraryEntity libraryEntity = BeanMapper.map(library, LibraryEntity.class);
        libraryEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        libraryEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return libraryDao.createLibrary(libraryEntity);
    }

    @Override
    public void updateLibrary(@NotNull @Valid Library library) {
        LibraryEntity libraryEntity = BeanMapper.map(library, LibraryEntity.class);
        libraryEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        libraryDao.updateLibrary(libraryEntity);
    }

    @Override
    public void deleteLibrary(@NotNull String id) {
        Library library = this.findLibrary(id);
        if ("maven".equals(library.getLibraryType())){
            libraryMavenService.deleteLibraryMavenByLibraryId(id);
        }
        libraryVersionService.deleteVersionByCondition("libraryId",id);

        libraryFileService.deleteLibraryFileByCondition("libraryId",id);

        libraryDao.deleteLibrary(id);
    }

    @Override
    public void deleteLibraryByRepository(String repositoryId) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(LibraryEntity.class)
                .eq("repositoryId",repositoryId)
                .get();
        libraryDao.deleteLibrary(deleteCondition);
    }

    @Override
    public Library findOne(String id) {
        LibraryEntity libraryEntity = libraryDao.findLibrary(id);

        Library library = BeanMapper.map(libraryEntity, Library.class);

        return library;
    }

    @Override
    public List<Library> findList(List<String> idList) {
        List<LibraryEntity> libraryEntityList =  libraryDao.findLibraryList(idList);

        List<Library> libraryList =  BeanMapper.mapList(libraryEntityList,Library.class);
        return libraryList;
    }

    @Override
    public Library findLibrary(@NotNull String id) {
        Library library = findOne(id);

        joinTemplate.joinQuery(library);

        return library;
    }

    @Override
    public List<Library> findAllLibrary() {
        List<LibraryEntity> libraryEntityList =  libraryDao.findAllLibrary();

        List<Library> libraryList =  BeanMapper.mapList(libraryEntityList,Library.class);

        joinTemplate.joinQuery(libraryList);

        return libraryList;
    }

    @Override
    public List<Library> findLibraryList(LibraryQuery libraryQuery) {

        findRepositoryGroup(libraryQuery);
        List<LibraryEntity> libraryEntityList = libraryDao.findLibraryList(libraryQuery);

        List<Library> libraryList = BeanMapper.mapList(libraryEntityList,Library.class);

        joinTemplate.joinQuery(libraryList);

        return libraryList;
    }


    @Override
    public List<Library> findLibraryListByRepository(LibraryQuery libraryQuery) {
        findRepositoryGroup(libraryQuery);
        List<Library> mavenLibraryList = libraryDao.findLibraryListByRepository(libraryQuery);

        return mavenLibraryList;
    }

    @Override
    public List<Library> findLibraryListByCondition(LibraryQuery libraryQuery) {
        findRepositoryGroup(libraryQuery);
        List<LibraryEntity> libraryEntityList = libraryDao.findLibraryListByCondition(libraryQuery);
        List<Library> libraryList = BeanMapper.mapList(libraryEntityList,Library.class);

        joinTemplate.joinQuery(libraryList);
        return libraryList;
    }

    @Override
    public List<Library> findNotPushLibraryList(LibraryQuery libraryQuery) {
        List<PushLibrary> pushLibraryList = pushLibraryService.findPushLibraryList(new PushLibraryQuery().setRepositoryId(libraryQuery.getRepositoryId()));
        String[] libraryIds=null;
        if (CollectionUtils.isNotEmpty(pushLibraryList)){
            List<String> libraryId = pushLibraryList.stream().map(a -> a.getLibrary().getId()).collect(Collectors.toList());
            String[] strings = new String[libraryId.size()];
             libraryIds = libraryId.toArray(strings);
        }
        List<LibraryEntity> libraryEntityList = libraryDao.findNotPushLibraryList(libraryIds, libraryQuery.getRepositoryId(),libraryQuery.getName());
        List<Library> libraryList = BeanMapper.mapList(libraryEntityList,Library.class);

        joinTemplate.joinQuery(libraryList);
        return libraryList;
    }


    @Override
    public Pagination<Library> findLibraryPage(LibraryQuery libraryQuery) {
        Pagination<LibraryEntity>  pagination = libraryDao.findLibraryPage(libraryQuery);

        List<Library> libraryList = BeanMapper.mapList(pagination.getDataList(),Library.class);

        joinTemplate.joinQuery(libraryList);

        return PaginationBuilder.build(pagination,libraryList);
    }




    /**
     *  查询组合库关联
     * @param libraryQuery     全路径
     * @return
     */
    public void findRepositoryGroup(LibraryQuery libraryQuery){
        if (StringUtils.isNotEmpty(libraryQuery.getRepositoryId())){
            Repository repository = repositoryService.findRepository(libraryQuery.getRepositoryId());
            if (!ObjectUtils.isEmpty(repository)&& "group".equals(repository.getRepositoryType())){
                List<RepositoryGroup> groupItemsList = repositoryGroupService.findRepositoryGroupList(
                        new RepositoryGroupQuery().setRepositoryGroupId(libraryQuery.getRepositoryId()));
                if (CollectionUtils.isNotEmpty(groupItemsList)){
                    List<String> repositoryIds = groupItemsList.stream().map(item -> item.getRepository().getId()).collect(Collectors.toList());
                    libraryQuery.setRepositoryIds(repositoryIds);
                }
            }
        }
    }

    /**
     *  制品创建
     * @param libraryName     制品名称
     * @param libraryType  制品类型
     * @param repository  制品库信息
     * @return
     */
    public Library createLibraryData(String libraryName,String libraryType,Repository repository){
        Library library = new Library();
        library.setLibraryType(libraryType);
        //查询制品包是否有创建
        List<LibraryEntity> libraryList =libraryDao.findEqLibraryList(new LibraryQuery().setName(libraryName).setRepositoryId(repository.getId()));

        String libraryId=null;
        if (CollectionUtils.isEmpty(libraryList)){
            library.setName(libraryName);
            //创建制品信息
            library.setRepository(repository);
            libraryId = this.createLibrary(library);
        }else {
            libraryId = libraryList.get(0).getId();
        }
        library.setLibraryType(libraryType);
        library.setId(libraryId);

        return library;
    }


}

