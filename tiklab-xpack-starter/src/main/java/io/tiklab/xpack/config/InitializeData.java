package io.tiklab.xpack.config;

import io.tiklab.xpack.repository.model.*;
import io.tiklab.xpack.repository.service.RepositoryGroupService;
import io.tiklab.xpack.repository.service.RepositoryRemoteProxyService;
import io.tiklab.xpack.repository.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class InitializeData {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RepositoryRemoteProxyService proxyService;

    @Autowired
    private RepositoryGroupService repositoryGroupService;


    @Bean
    public void initializeRepository(){
        //创建本地制品库
        Repository localRepository = createLocalRepository();
        //创建远程库
        Repository remoteRepository = createRemoteRepository();
        //创建组合库
        createGroupRepository(localRepository,remoteRepository);

    }

    /**
     * 创建本地制品库
     * @param
     * @return
     */
    public Repository createLocalRepository(){
        List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName("default-local-repository"));
        if (CollectionUtils.isEmpty(repositoryList)){
            Repository repository = new Repository();
            repository.setName("default-local-repository");
            repository.setType("maven");
            repository.setRepositoryType("local");
            repository.setDescription("这是默认的本地制品库");
            repository.setCreateUser("111111");

            repository.setRepositoryUrl("default-local-repository");
            String repositoryId = repositoryService.createRepository(repository);

            repository.setId(repositoryId);
            return repository;
        }else {
            return repositoryList.get(0);
        }
    }

    /**
     * 创建远程制品库
     * @param
     * @return
     */
    public Repository createRemoteRepository(){
        List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName("default-remote-repository"));
        Repository repository = new Repository();
        if (CollectionUtils.isEmpty(repositoryList)){
            repository.setName("default-remote-repository");
            repository.setType("maven");
            repository.setRepositoryType("remote");
            repository.setDescription("这是默认的远程制品库");
            repository.setCreateUser("111111");
            repository.setRepositoryUrl("default-remote-repository");
            String repositoryId = repositoryService.createRepository(repository);


            RepositoryRemoteProxy remoteProxy = new RepositoryRemoteProxy();
            repository.setId(repositoryId);
            remoteProxy.setRepository(repository);
            remoteProxy.setAgencyName("agencyName");
            remoteProxy.setAgencyUrl("https://repo1.maven.org/maven2");
            proxyService.createRepositoryRemoteProxy(remoteProxy);
            return repository;
        }else {
            return repositoryList.get(0);
        }
    }
    /**
     * 创建组合制品库
     * @param  repositoryLocal 需要关联的本地库
     * @param  repositoryRemote 需要关联的远程库
     * @return
     */
    public void createGroupRepository(Repository repositoryLocal,Repository repositoryRemote ){
        List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName("default-group-repository"));
        if(CollectionUtils.isEmpty(repositoryList)){
            Repository groupRepository = new Repository();
            groupRepository.setName("default-group-repository");
            groupRepository.setType("maven");
            groupRepository.setRepositoryType("group");
            groupRepository.setDescription("这是默认的组合制品库");
            groupRepository.setCreateUser("111111");
            groupRepository.setRepositoryUrl("default-group-repository");
            String repositoryGroupId = repositoryService.createRepository(groupRepository);

            RepositoryGroup repositoryGroup = new RepositoryGroup();
            groupRepository.setId(repositoryGroupId);
            repositoryGroup.setRepositoryGroup(groupRepository);

            List<RepositoryGroup> repositoryGroupList = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repositoryGroupId).setRepositoryId(repositoryLocal.getId()));
            if (CollectionUtils.isEmpty(repositoryGroupList)){
                repositoryGroup.setRepository(repositoryLocal);
                repositoryGroupService.createRepositoryGroup(repositoryGroup);
            }

            List<RepositoryGroup> repositoryGroups = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repositoryGroupId).setRepositoryId(groupRepository.getId()));
            if (CollectionUtils.isEmpty(repositoryGroups)){
                repositoryGroup.setRepository(repositoryRemote);
                repositoryGroupService.createRepositoryGroup(repositoryGroup);
            }
        }
    }
}
