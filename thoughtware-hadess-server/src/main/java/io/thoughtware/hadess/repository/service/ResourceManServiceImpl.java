package io.thoughtware.hadess.repository.service;

import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.repository.model.ResourceMan;
import io.thoughtware.licence.licence.model.Version;
import io.thoughtware.licence.licence.service.VersionService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ResourceManServiceImpl implements ResourceManService{

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Autowired
    VersionService versionService;

    @Override
    public ResourceMan findResource() {
        ResourceMan resourceMan = new ResourceMan();

        String repositoryAddress = yamlDataMaService.repositoryAddress();
        File file = new File(repositoryAddress);
        //第一次还未创建文件夹
        if (!file.exists()){
            return null;
        }

        long logBytes = FileUtils.sizeOfDirectory(file);
        String size = RepositoryUtil.countStorageSize(logBytes);
        resourceMan.setUsedSpace(size);

        resourceMan.setAllSpace(getDiskSize(repositoryAddress));

        Version version = versionService.getVersion();
        resourceMan.setVersion(version.getRelease());


        return resourceMan;
    }

    /**
     * 磁盘大小
     * @return
     */
    public String getDiskSize(String repositoryAddress){
        float diskSize = RepositoryUtil.findDiskSize(repositoryAddress);
        return diskSize+"GB";
    }
}
