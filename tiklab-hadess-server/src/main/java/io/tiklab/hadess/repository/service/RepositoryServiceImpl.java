package io.tiklab.hadess.repository.service;
import com.alibaba.fastjson.JSON;
import io.tiklab.core.context.AppHomeContext;
import io.tiklab.hadess.common.*;
import io.tiklab.hadess.library.dao.LibraryDao;
import io.tiklab.hadess.library.service.LibraryFileService;
import io.tiklab.hadess.library.service.LibraryMavenService;
import io.tiklab.hadess.library.service.LibraryService;
import io.tiklab.hadess.library.service.LibraryVersionService;
import io.tiklab.hadess.pushcentral.service.PushGroupService;
import io.tiklab.hadess.pushcentral.service.PushLibraryServiceImpl;
import io.tiklab.hadess.repository.model.*;
import io.tiklab.privilege.role.model.PatchUser;
import io.tiklab.privilege.role.model.RoleUser;
import io.tiklab.privilege.role.service.RoleUserService;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.hadess.repository.dao.RepositoryDao;
import io.tiklab.hadess.repository.entity.RepositoryEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    LibraryDao libraryDao;

    @Autowired
    HadessMessageService hadessMessageService;

    @Autowired
    RepositoryRemoteProxyService remoteProxyService;

    @Autowired
    RemoteProxyService proxyService;

    @Autowired
    PushGroupService pushGroupService;

    @Autowired
    PushLibraryServiceImpl pushLibraryService;

    @Autowired
    RoleUserService roleUserService;

    @Value("${server.port:8080}")
    private String port;


    @Override
    @Transactional
    public String createRepository(@NotNull @Valid Repository repository) {

        RepositoryEntity repositoryEntity = setRepositoryEntity(repository);
        Random random = new Random();
        // 生成0到4之间的随机数
        int randomNum = random.nextInt(5);
        repositoryEntity.setColor(randomNum);


        String repositoryId = getRandom(repository.getRepositoryType());
        repositoryEntity.setId(repositoryId);
        repositoryDao.createRepository(repositoryEntity);

        repository.setId(repositoryId);
        //创建为远程库，且代理地址不为空
        if (repository.getRepositoryType().equals("remote")&&!ObjectUtils.isEmpty(repository.getProxyDataList())){
            List<Object> proxyDataList = repository.getProxyDataList();
            for (Object proxyObject:proxyDataList){
                String object = JSON.toJSONString(proxyObject);
                RemoteProxy remoteProxy = JSON.parseObject(object, RemoteProxy.class);

                //添加的自定义代理源 先创建自定义的代理源到代理源管理表
                if (StringUtils.isEmpty(remoteProxy.getId())){
                    String remoteProxyId = proxyService.createRemoteProxy(remoteProxy);
                    remoteProxy.setId(remoteProxyId);
                }

                RepositoryRemoteProxy repositoryRemoteProxy = new RepositoryRemoteProxy();
                repositoryRemoteProxy.setRepository(repository);
                repositoryRemoteProxy.setRemoteProxy(remoteProxy);
                remoteProxyService.createRepositoryRemoteProxy(repositoryRemoteProxy);
            }
        }

        //创建服务器中的仓库文件夹
        String repositoryBolder = yamlDataMaService.repositoryAddress() + "/" + repositoryId;
        File file = new File(repositoryBolder);
        if (!file.exists()){
            file.mkdirs();
        }

        //初始化helm制品库的索引文件
        RepositoryUtil.initHelmIndexFile(repositoryBolder+"/index.yaml");

        String userId;
        //初始化示例仓库用户id 取Repository里面用户
        if (!ObjectUtils.isEmpty(repository.getCreateUser())&&StringUtils.isNotEmpty(repository.getCreateUser())){
            userId = repository.getCreateUser();
        }else {
            userId =LoginContext.getLoginId();
        }
        List<PatchUser> List = new ArrayList<>();
        PatchUser patchUser = new PatchUser();
        RoleUser userRoleAdmin = roleUserService.findUserRoleAdmin();
        //给系统超级管理员设置成项目超级管理员
        patchUser.setUserId(userRoleAdmin.getUser().getId());
        patchUser.setRoleType(2);
        List.add(patchUser);

        //超级管理员和创建者不同 ，给创建者设置为管理员角色
        if (!(userId).equals(userRoleAdmin.getUser().getId())){
            PatchUser patchUser1 = new PatchUser();
            patchUser1.setUserId(userId);
            patchUser1.setRoleType(1);
            List.add(patchUser1);
        }
        dmRoleService.initPatchDmRole(repositoryId, List);

        //初始化的演示仓库不发送消息
        if (repository.getCategory()!=1){
            //发送消息
            initRepositoryMap(repositoryEntity,"create",null);
        }

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

        //为远程库，且代理地址不为空
        if (repository.getRepositoryType().equals("remote")&&!ObjectUtils.isEmpty(repository.getProxyDataList())){
            //直接清除之前的记录
            remoteProxyService.deleteRepositoryRemoteProxy("repositoryId",repository.getId());

            List<Object> proxyDataList = repository.getProxyDataList();
            for (Object proxyObject:proxyDataList){
                String object = JSON.toJSONString(proxyObject);
                RemoteProxy remoteProxy = JSON.parseObject(object, RemoteProxy.class);

                //添加的自定义代理源 先创建自定义的代理源到代理源管理表
                if (StringUtils.isEmpty(remoteProxy.getId())){
                    String remoteProxyId = proxyService.createRemoteProxy(remoteProxy);
                    remoteProxy.setId(remoteProxyId);
                }

                RepositoryRemoteProxy repositoryRemoteProxy = new RepositoryRemoteProxy();
                repositoryRemoteProxy.setRepository(repository);
                repositoryRemoteProxy.setRemoteProxy(remoteProxy);
                remoteProxyService.createRepositoryRemoteProxy(repositoryRemoteProxy);
            }
        }

        //发送消息
        if (!repositoryEn.getName().equals(repository.getName())){
            initRepositoryMap(repositoryEn,"update",repository.getName());
        }
    }

    @Override
    public void deleteRepository(@NotNull String id) {
        Repository repository = this.findRepository(id);

        repositoryDao.deleteRepository(id);

        //开启新线程删除关联数据
        Thread thread = new Thread() {
            public void run() {
                //删除制品
                libraryService.deleteLibraryByRepository(id);
                libraryVersionService.deleteVersionByCondition("repositoryId",id);
                libraryFileService.deleteLibraryFileByCondition("repositoryId",id);

                //仓库为maven 删除maven制品单独的数据
                if (("maven").equals(repository.getType())){
                    libraryMavenService.deleteLibraryMavenByCondition("repositoryId",id);
                    repositoryMavenService.deleteRepositoryMavenByCondition("repositoryId",id);
                }

                //仓库为远程库 删除关联的代理地址
                if (("remote").equals(repository.getRepositoryType())){
                    remoteProxyService.deleteRepositoryRemoteProxy("repositoryId",id);
                }

                //删除组合库关联数据
                if (("group").equals(repository.getRepositoryType())){
                    groupItemsService.deleteGroupItemByCondition("repositoryGroupId",id);
                }else {
                    groupItemsService.deleteGroupItemByCondition("repositoryId",id);
                }

                //仓库为组合库 删除关联的制品库
                if (("remote").equals(repository.getRepositoryType())){
                    remoteProxyService.deleteRepositoryRemoteProxy("repositoryId",id);
                }

                //删除服务器中制品仓库
                try {
                    String folderPath = yamlDataMaService.repositoryAddress() + "/" + id;
                    FileUtils.deleteDirectory(new File(folderPath));

                    //删除演示仓库的时候 清除项目中的示例包文件
                    if (repository.getCategory()==1){
                        String fileName=("npm").equals(repository.getType())?"npm-sample.tgz":"maven-sample.zip";
                        File tgzFile = new File(AppHomeContext.getAppHome()+"/file/"+fileName);
                        FileUtils.delete(tgzFile);
                    }
                }catch (Exception e){
                    logger.info("删除制品库:"+id+"失败:"+e.getMessage());
                }

                //删除制品下面的扫描计划
              /*  scanPlayService.deleteScanPlayByCondition("repositoryId",id);*/

                //删除推送
                pushGroupService.deleteVersionByCondition("repositoryId",id);
                pushLibraryService.deleteVersionByCondition("repositoryId",id);


                //删除制品库后发送消息
                RepositoryEntity repositoryEntity = BeanMapper.map(repository, RepositoryEntity.class);
                initRepositoryMap(repositoryEntity,"delete",null);
            }
        };
        thread.start();
    }


    @Override
    public Repository findOneRepository(String id) {
        RepositoryEntity repositoryEntity = repositoryDao.findRepository(id);

        Repository repository = BeanMapper.map(repositoryEntity, Repository.class);


        return repository;
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
        if (!ObjectUtils.isEmpty(repository)&&StringUtils.isNotEmpty(repository.getRepositoryUrl())){
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
        //查询所有类型库
        if (("all").equals(repositoryQuery.getRepositoryType())){
            repositoryQuery.setRepositoryType(null);
        }
        List<RepositoryEntity> repositoryEntityList = repositoryDao.findRepositoryList(repositoryQuery);

        List<Repository> repositoryList = BeanMapper.mapList(repositoryEntityList,Repository.class);
        List<Repository> list = repositoryList.stream().sorted(Comparator.comparing(Repository::getType)).sorted(Comparator.comparing(Repository::getCategory)).collect(Collectors.toList());


        findLibrary(list);

        joinTemplate.joinQuery(repositoryList);

        return list;
    }

    @Override
    public List<Repository> findPublicRepositoryList(String type,String repositoryType) {
        List<RepositoryEntity> repositoryEntityList = repositoryDao.findRepositoryList(new RepositoryQuery()
                .setRepositoryType(repositoryType).setType(type));
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
    public Repository findRepository(String repositoryName, String type) {
        Repository repository=null;

        List<RepositoryEntity> repositoryEntityList = repositoryDao.findRepositoryList(new RepositoryQuery().setName(repositoryName).setType(type));
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
        //查询所有类型库
        if (("all").equals(repositoryQuery.getRepositoryType())){
            repositoryQuery.setRepositoryType(null);
        }

        Pagination<RepositoryEntity>  pagination = repositoryDao.findRepositoryPage(repositoryQuery);

        List<Repository> repositoryList = BeanMapper.mapList(pagination.getDataList(),Repository.class);

        joinTemplate.joinQuery(repositoryList);

        findLibrary(repositoryList);

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


    /**
     * 查询制品库下面制品数量
     * @param repositoryList 制品库
     * @return
     */
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
            String visitAddress = yamlDataMaService.findVisitAddress();
           if (!ObjectUtils.isEmpty(visitAddress)){
               if (("docker").equals(type)){
                   if (visitAddress.startsWith("http://")){
                       String s = StringUtils.substringAfter(visitAddress, "http://");
                       absoluteAddress= s + ":80/"+repository.getRepositoryUrl();
                   }
                   if (visitAddress.startsWith("https://")){
                       String s = StringUtils.substringAfter(visitAddress, "https://");
                       absoluteAddress= s + "/"+repository.getRepositoryUrl();
                   }
               }
               if (("generic").equals(type)){
                   absoluteAddress=visitAddress+ "/generic/"+repository.getRepositoryUrl();
               }
               if (("npm").equals(type)||("maven").equals(type)){
                   absoluteAddress=visitAddress + "/repository/"+repository.getRepositoryUrl();
               }
               if (("helm").equals(type)){
                   absoluteAddress=visitAddress + "/helm/"+repository.getRepositoryUrl();
               }
               if (("go").equals(type)){
                   absoluteAddress=visitAddress + "/go/"+repository.getRepositoryUrl();
               }
           }else {
               //若配置文件配置了地址就取配置的地址 没配置就获取服务器ip
               String serverIp = RepositoryUtil.getServerIp();
               if (("docker").equals(type)){
                   absoluteAddress= serverIp + ":" + port + "/"+repository.getRepositoryUrl();
               }
               if (("generic").equals(type)){
                   absoluteAddress="http://" + serverIp + ":" + port + "/generic/"+repository.getRepositoryUrl();
               }
               if (("npm").equals(type)||("maven").equals(type)){
                   absoluteAddress="http://" + serverIp + ":" + port + "/repository/"+repository.getRepositoryUrl();
               }
               if (("helm").equals(type)){
                   absoluteAddress="http://" + serverIp + ":" + port + "/helm/"+repository.getRepositoryUrl();
               }
               if (("go").equals(type)){
                   absoluteAddress="http://" + serverIp + ":" + port + "/go/"+repository.getRepositoryUrl();
               }
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