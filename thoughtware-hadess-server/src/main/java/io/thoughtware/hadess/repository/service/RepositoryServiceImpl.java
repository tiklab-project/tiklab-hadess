package io.thoughtware.hadess.repository.service;
import io.thoughtware.hadess.common.*;
import io.thoughtware.hadess.library.dao.LibraryDao;
import io.thoughtware.hadess.library.entity.LibraryEntity;
import io.thoughtware.hadess.library.model.LibraryFile;
import io.thoughtware.hadess.library.model.LibraryFileQuery;
import io.thoughtware.hadess.library.service.LibraryFileService;
import io.thoughtware.hadess.library.service.LibraryMavenService;
import io.thoughtware.hadess.library.service.LibraryService;
import io.thoughtware.hadess.library.service.LibraryVersionService;
import io.thoughtware.hadess.repository.model.*;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.toolkit.join.JoinTemplate;
import io.thoughtware.privilege.dmRole.service.DmRoleService;
import io.thoughtware.rpc.annotation.Exporter;
import io.thoughtware.hadess.repository.dao.RepositoryDao;
import io.thoughtware.hadess.repository.entity.RepositoryEntity;
import io.thoughtware.hadess.repository.model.*;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    @Autowired
    LibraryDao libraryDao;

    @Autowired
    HadessMessageService hadessMessageService;

    @Value("${server.port:8080}")
    private String port;


    @Override
    public String createRepository(@NotNull @Valid Repository repository) {

        RepositoryEntity repositoryEntity = setRepositoryEntity(repository);
        Random random = new Random();
        // 生成0到4之间的随机数
        int randomNum = random.nextInt(5);
        repositoryEntity.setColor(randomNum);


        String repositoryId = getRandom(repository.getRepositoryType());
        repositoryEntity.setId(repositoryId);

        repositoryDao.createRepository(repositoryEntity);
        String repositoryFile = yamlDataMaService.repositoryAddress() + "/" + repositoryId;
        File file = new File(repositoryFile);
        if (!file.exists()){
            file.mkdirs();
        }
        if (ObjectUtils.isEmpty(repository.getCreateUser())){
            dmRoleService.initDmRoles(repositoryId, LoginContext.getLoginId(), "hadess");
        }else {
            dmRoleService.initDmRoles(repositoryId,repository.getCreateUser(),"hadess");
        }

        //发送消息
        initRepositoryMap(repositoryEntity,"create",null);
        return repositoryId;
    }


    @Override
    public void updateRepository(@NotNull @Valid Repository repository) {
        RepositoryEntity repositoryEn = repositoryDao.findRepository(repository.getId());

        RepositoryEntity repositoryEntity = BeanMapper.map(repository, RepositoryEntity.class);
        if (StringUtils.isNotEmpty(repository.getType())){
            String type = repository.getType().toLowerCase();
            repositoryEntity.setType(type);
        }
        repositoryDao.updateRepository(repositoryEntity);

        //发送消息
        if (!repositoryEn.getName().equals(repository.getName())){
            initRepositoryMap(repositoryEn,"update",repository.getName());
        }
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
        RepositoryEntity repositoryEntity = BeanMapper.map(repository, RepositoryEntity.class);
        initRepositoryMap(repositoryEntity,"delete",null);
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

    /**
     * 获取随机数
     * @param repositoryType 仓库类型 local 本地、remote 远程、group 组合
     */
    public String getRandom(String repositoryType){
        String math =  UuidGenerator.gen(10);

        if (("local").equals(repositoryType)){
             math = math + "bd";
        }
        if (("remote").equals(repositoryType)){
            math = math + "yc";
        }
        if (("group").equals(repositoryType)){
            math = math + "zh";
        }

        Repository repository = this.findOne(math);
        if (!ObjectUtils.isEmpty(repository)){
            getRandom(repositoryType);
        }
        return math;
    }

    public void updateRep(){

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable(){
            @Override
            public void run() {
                List<LibraryEntity> allLibrary = libraryDao.findAllLibrary();

                for (LibraryEntity libraryEntity:allLibrary){
                   Long size=0L;
                    List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryEntity.getId()));
                    if(CollectionUtils.isNotEmpty(libraryFileList)){
                        List<LibraryFile> libraryFiles = libraryFileList.stream().sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(libraryFiles)){
                            LibraryFile libraryFile = libraryFiles.get(0);
                            String id = libraryFile.getLibraryVersion().getId();

                            List<LibraryFile> libraryFiles1 = libraryFiles.stream().filter(a -> (id).equals(a.getLibraryVersion().getId())).collect(Collectors.toList());
                            long length=size;
                            for (LibraryFile libraryFile1:libraryFiles1){
                                String filePath = yamlDataMaService.repositoryAddress() + "/" + libraryFile1.getFileUrl();
                                File file = new File(filePath);
                                if (file.exists()){
                                    length += file.length();
                                }
                            }

                            libraryEntity.setSize(Math.toIntExact(length));
                            libraryDao.updateLibrary(libraryEntity);

                            logger.info("修改成功："+libraryEntity.getName());
                        }
                    }
                }
            }});
    }


    /**
     *操作制品库发送消息
     * @param oldRepository 操作的制品库
     * @param type  操作类型
     * @param  updateName 更新名字
     */
    public void initRepositoryMap(RepositoryEntity oldRepository, String type, String updateName){

        HashMap<String, Object> map = hadessMessageService.initMap();

        map.put("repositoryId",oldRepository.getId());
        map.put("action",oldRepository.getName());
        if (("delete").equals(type)){
            map.put("message", "删除了制品库"+oldRepository.getName());
            map.put("link", HadessFinal.LOG_RPY_DELETE);
            hadessMessageService.settingMessage(map,HadessFinal.LOG_TYPE_DELETE);
            hadessMessageService.settingLog(map,HadessFinal.LOG_TYPE_DELETE,"repository");
        }

        if (("update").equals(type)){
            map.put("message", oldRepository.getName()+"更改为"+updateName);
            map.put("link",HadessFinal.LOG_RPY_UPDATE);

            hadessMessageService.settingMessage(map,HadessFinal.LOG_TYPE_UPDATE);
            hadessMessageService.settingLog(map,HadessFinal.LOG_TYPE_UPDATE,"repository");
        }

        if (("create").equals(type)){
            map.put("message", "创建了制品库"+oldRepository.getName());
            map.put("link",HadessFinal.LOG_RPY_CREATE);
            hadessMessageService.settingMessage(map,HadessFinal.LOG_TYPE_CREATE);
            hadessMessageService.settingLog(map,HadessFinal.LOG_TYPE_CREATE,"repository");
        }
    }
}