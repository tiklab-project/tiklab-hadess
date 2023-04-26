package io.tiklab.xpack.repository.service;
import io.tiklab.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.library.model.Library;
import io.tiklab.xpack.library.model.LibraryQuery;
import io.tiklab.xpack.library.service.LibraryFileService;
import io.tiklab.xpack.library.service.LibraryService;
import io.tiklab.xpack.library.service.LibraryVersionService;
import io.tiklab.xpack.repository.dao.RepositoryDao;
import io.tiklab.xpack.repository.entity.RepositoryEntity;
import io.tiklab.xpack.repository.model.Repository;
import io.tiklab.xpack.repository.model.RepositoryGroup;
import io.tiklab.xpack.repository.model.RepositoryGroupQuery;
import io.tiklab.xpack.repository.model.RepositoryQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


/**
* RepositoryServiceImpl-制品库
*/
@Service
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    RepositoryDao repositoryDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    RepositoryGroupService groupItemsService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    LibraryFileService libraryFileService;

    @Value("${server.port:8080}")
    private String port;

    @Value("${repository.code:null}")
    private String repositoryCode;

    @Value("${repository.address:null}")
    private String repositoryAddress;

    @Override
    public String createRepository(@NotNull @Valid Repository repository) {
        RepositoryEntity repositoryEntity = BeanMapper.map(repository, RepositoryEntity.class);

        repositoryEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        repositoryEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        String type = repository.getType().toLowerCase();
        String absoluteAddress=null;

        //若配置文件配置了地址就取配置的地址 没配置就获取服务器ip
        if (StringUtils.isEmpty(repositoryAddress)){
            String ip;
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                ip = "172.0.0.1";
            }
             absoluteAddress="http://" + ip + ":" + port + "/"+repositoryCode+"/"+type+"/";
        }else {
            absoluteAddress=repositoryAddress+"/"+repositoryCode+"/"+type+"/";
        }
        repositoryEntity.setType(type);
        repositoryEntity.setRepositoryUrl(absoluteAddress);

        return repositoryDao.createRepository(repositoryEntity);
    }

    @Override
    public void updateRepository(@NotNull @Valid Repository repository) {
        RepositoryEntity repositoryEntity = BeanMapper.map(repository, RepositoryEntity.class);
        if (StringUtils.isNotEmpty(repository.getType())){
            String type = repository.getType().toLowerCase();
            repositoryEntity.setType(type);
        }
        repositoryDao.updateRepository(repositoryEntity);
    }

    @Override
    public void deleteRepository(@NotNull String id) {
        Repository repository = this.findRepository(id);
        if (("group").equals(repository.getRepositoryType())){
            groupItemsService.deleteGroupItemByCondition("repositoryGroupId",id);
        }else {
            groupItemsService.deleteGroupItemByCondition("repositoryId",id);
        }
        
        repositoryDao.deleteRepository(id);

        libraryService.deleteLibraryByRepository(id);

        libraryVersionService.deleteVersionByCondition("repositoryId",id);

        libraryFileService.deleteLibraryFileByCondition("repositoryId",id);


    }

    @Override
    public Repository findOne(String id) {
        RepositoryEntity repositoryEntity = repositoryDao.findRepository(id);

        Repository repository = BeanMapper.map(repositoryEntity, Repository.class);
        return repository;
    }

    @Override
    public List<Repository> findList(List<String> idList) {
        List<RepositoryEntity> repositoryEntityList =  repositoryDao.findRepositoryList(idList);

        List<Repository> repositoryList =  BeanMapper.mapList(repositoryEntityList,Repository.class);
        return repositoryList;
    }

    @Override
    public Repository findRepository(@NotNull String id) {
        Repository repository = findOne(id);

        joinTemplate.joinQuery(repository);

        return repository;
    }

    @Override
    public List<Repository> findAllRepository() {
        List<RepositoryEntity> repositoryEntityList =  repositoryDao.findAllRepository();

        List<Repository> repositoryList =  BeanMapper.mapList(repositoryEntityList,Repository.class);

        joinTemplate.joinQuery(repositoryList);

        return repositoryList;
    }

    @Override
    public List<Repository> findRepositoryList(RepositoryQuery repositoryQuery) {
        List<RepositoryEntity> repositoryEntityList = repositoryDao.findRepositoryList(repositoryQuery);

        List<Repository> repositoryList = BeanMapper.mapList(repositoryEntityList,Repository.class);

        findLibrary(repositoryList);

        joinTemplate.joinQuery(repositoryList);

        return repositoryList;
    }

    @Override
    public List<Repository> findLocalAndRemoteRepository(String type) {
        List<RepositoryEntity> repositoryEntityList = repositoryDao.findLocalAndRemoteRepository(type);

        List<Repository> repositoryList = BeanMapper.mapList(repositoryEntityList,Repository.class);

        findLibrary(repositoryList);

        joinTemplate.joinQuery(repositoryList);

        return repositoryList;
    }

    @Override
    public Pagination<Repository> findRepositoryPage(RepositoryQuery repositoryQuery) {
        Pagination<RepositoryEntity>  pagination = repositoryDao.findRepositoryPage(repositoryQuery);

        List<Repository> repositoryList = BeanMapper.mapList(pagination.getDataList(),Repository.class);

        joinTemplate.joinQuery(repositoryList);

        return PaginationBuilder.build(pagination,repositoryList);
    }

    @Override
    public List<Repository> findRepositoryByGroup(String repositoryGroupId) {
        List<RepositoryGroup> repositoryGroupList = groupItemsService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repositoryGroupId));
        List<Repository> repositoryList = repositoryGroupList.stream().map(RepositoryGroup::getRepository).collect(Collectors.toList());
        return repositoryList;
    }

    @Override
    public List<Repository> findUnRelevanceRepository(String repositoryType,String repositoryGroupId) {
        List<Repository> localAndRemoteRepository = findLocalAndRemoteRepository(repositoryType);
        List<Repository> repositoryByGroup = findRepositoryByGroup(repositoryGroupId);
        if (CollectionUtils.isNotEmpty(repositoryByGroup)){
            for (Repository repository:repositoryByGroup){
                localAndRemoteRepository = localAndRemoteRepository.stream().filter(a -> !repository.getId().equals(a.getId())).collect(Collectors.toList());
            }
        }
        return localAndRemoteRepository;
    }

    public void findLibrary(List<Repository> repositoryList){
        if (CollectionUtils.isNotEmpty(repositoryList)) {
            for (Repository repository : repositoryList) {
                List<Library> libraryList = libraryService.findLibraryList(new LibraryQuery().setRepositoryId(repository.getId()));
                if (CollectionUtils.isNotEmpty(libraryList)) {
                    repository.setLibraryNum(libraryList.size());
                } else {
                    repository.setLibraryNum(0);
                }
            }
        }
   }
}