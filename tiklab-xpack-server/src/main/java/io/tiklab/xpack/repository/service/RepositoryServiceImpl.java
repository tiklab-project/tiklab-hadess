package io.tiklab.xpack.repository.service;
import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.join.JoinTemplate;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.xpack.common.RepositoryUtil;
import io.tiklab.xpack.common.XpackYamlDataMaService;
import io.tiklab.xpack.library.service.LibraryFileService;
import io.tiklab.xpack.library.service.LibraryMavenService;
import io.tiklab.xpack.library.service.LibraryService;
import io.tiklab.xpack.library.service.LibraryVersionService;
import io.tiklab.xpack.repository.dao.RepositoryDao;
import io.tiklab.xpack.repository.entity.RepositoryEntity;
import io.tiklab.xpack.repository.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


/**
* RepositoryServiceImpl-制品库
*/
@Service
@Exporter
public class RepositoryServiceImpl implements RepositoryService {

    private static Logger logger = LoggerFactory.getLogger(RepositoryServiceImpl.class);

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

    @Autowired
     DmRoleService dmRoleService;

    @Autowired
     RepositoryMavenService repositoryMavenService;

    @Autowired
    LibraryMavenService libraryMavenService;

    @Autowired
    XpackYamlDataMaService yamlDataMaService;


    @Value("${server.port:8080}")
    private String port;


    @Override
    public String createRepository(@NotNull @Valid Repository repository) {

        RepositoryEntity repositoryEntity = setRepositoryEntity(repository);

        String repositoryId = repositoryDao.createRepository(repositoryEntity);
        String repositoryFile = yamlDataMaService.repositoryAddress() + "/" + repositoryId;
        File file = new File(repositoryFile);
        if (!file.exists()){
            file.mkdirs();
        }
        if (ObjectUtils.isEmpty(repository.getCreateUser())){
            dmRoleService.initDmRoles(repositoryId, LoginContext.getLoginId(), "xpack");
        }else {
            dmRoleService.initDmRoles(repositoryId,repository.getCreateUser(),"xpack");
        }
        return repositoryId;
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


        if (("maven").equals(repository.getType())){
            repositoryMavenService.deleteRepositoryMavenByCondition("repositoryId",id);
            libraryMavenService.deleteLibraryMavenByCondition("repositoryId",id);
        }

        //删除文件
        try {
            String folderPath = yamlDataMaService.repositoryAddress() + "/" + id;
            FileUtils.deleteDirectory(new File(folderPath));
        }catch (Exception e){
            logger.info("删除制品库时删除文件失败:"+e.getMessage());
        }
    }

    @Override
    public Repository findOne(String id) {
        RepositoryEntity repositoryEntity = repositoryDao.findRepository(id);

        Repository repository = BeanMapper.map(repositoryEntity, Repository.class);
        if (!ObjectUtils.isEmpty(repository)){
            repository.setRepositoryUrl(findRepositoryUrl(repository));
        }

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

        if (!ObjectUtils.isEmpty(repository)&&("maven").equals(repository.getType())&&("local").equals(repository.getRepositoryType())) {
            List<RepositoryMaven> mavenList = repositoryMavenService.findRepositoryMavenList(new RepositoryMavenQuery().setRepositoryId(repository.getId()));
            if (CollectionUtils.isNotEmpty(mavenList)) {
                repository.setVersionType(mavenList.get(0).getVersion());
            }
        }
        if (!ObjectUtils.isEmpty(repository)){
            String substring = repository.getRepositoryUrl().substring(0, repository.getRepositoryUrl().indexOf(repository.getName()));
            repository.setPrefixPath(substring);
        }

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
        List<Repository> list = repositoryList.stream().sorted(Comparator.comparing(Repository::getType)).sorted(Comparator.comparing(Repository::getCategory)).collect(Collectors.toList());


        findLibrary(list);

        joinTemplate.joinQuery(repositoryList);

        return list;
    }

    @Override
    public List<Repository> findRepositoryListByType(String type) {
        List<RepositoryEntity> repositoryEntityList = repositoryDao.findRepositoryList(new RepositoryQuery().setRepositoryType(type));
        List<Repository> repositoryList = BeanMapper.mapList(repositoryEntityList,Repository.class);
        return repositoryList;
    }

    @Override
    public Repository findRepositoryByName(String repositoryName) {
        Repository repository=null;

        List<RepositoryEntity> repositoryEntityList = repositoryDao.findRepositoryList(new RepositoryQuery().setName(repositoryName));
        List<Repository> repositoryList = BeanMapper.mapList(repositoryEntityList,Repository.class);
        if (CollectionUtils.isNotEmpty(repositoryList)){
             repository = repositoryList.get(0);
        }
        return repository;
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
        //查询本地、远程制品库
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
                String repositoryUrl = findRepositoryUrl(repository);
                repository.setRepositoryUrl(repositoryUrl);
                Integer libraryNum = libraryService.findLibraryNum(repository.getId(),repository.getRepositoryType());
                repository.setLibraryNum(libraryNum);
            }
        }
   }

    /**
     * 查询制品库地址
     * @param repository
     * @return
     */
    public String findRepositoryUrl(Repository repository){
        String absoluteAddress=null;
       if (!ObjectUtils.isEmpty(repository)){
           String type = repository.getType().toLowerCase();
           //若配置文件配置了地址就取配置的地址 没配置就获取服务器ip
           String serverIp = RepositoryUtil.getServerIp();
           if (("docker").equals(repository.getType())){
               absoluteAddress= serverIp + ":" + port + "/"+repository.getRepositoryUrl();
           }
           if (("generic").equals(repository.getType())){
               absoluteAddress="http://" + serverIp + ":" + port + "/generic/"+repository.getRepositoryUrl();
           }
           if (("npm").equals(repository.getType())||("maven").equals(repository.getType())){
               absoluteAddress="http://" + serverIp + ":" + port + "/repository/"+repository.getRepositoryUrl();
           }
       }
        return absoluteAddress;
    }

    /**
     * 向RepositoryEntity 添加参数
     * @param repository
     * @return
     */
    public RepositoryEntity setRepositoryEntity( Repository repository){
        RepositoryEntity repositoryEntity = BeanMapper.map(repository, RepositoryEntity.class);

        repositoryEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        repositoryEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        String type = repository.getType().toLowerCase();
        repositoryEntity.setType(type);

        return repositoryEntity;

    }
}