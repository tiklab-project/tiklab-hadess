package io.tiklab.hadess.repository.service;

import io.tiklab.hadess.common.RepositoryUtil;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.library.model.Library;
import io.tiklab.hadess.library.model.LibraryFile;
import io.tiklab.hadess.library.model.LibraryMaven;
import io.tiklab.hadess.library.model.LibraryVersion;
import io.tiklab.hadess.library.service.LibraryFileService;
import io.tiklab.hadess.library.service.LibraryMavenService;
import io.tiklab.hadess.library.service.LibraryService;
import io.tiklab.hadess.library.service.LibraryVersionService;
import io.tiklab.hadess.repository.model.Repository;
import io.tiklab.hadess.repository.model.RepositoryMaven;
import io.tiklab.hadess.repository.model.RepositoryQuery;
import io.tiklab.core.exception.SystemException;
import io.tiklab.privilege.dmRole.model.DmRole;
import io.tiklab.privilege.dmRole.model.DmRoleQuery;
import io.tiklab.privilege.dmRole.service.DmRoleService;
import io.tiklab.privilege.role.model.Role;
import io.tiklab.privilege.role.service.RoleService;
import io.tiklab.toolkit.context.AppContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/*
* 启动初始化演示制品库
* */
@Service
public class InitializeSampleServiceImpl implements InitializeSampleService{

    private static Logger logger = LoggerFactory.getLogger(InitializeSampleServiceImpl.class);

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryVersionService versionService;

    @Autowired
    LibraryMavenService libraryMavenService;

    @Autowired
    RepositoryMavenService repositoryMavenService;

    @Autowired
    LibraryFileService fileService;

    @Autowired
    XpackYamlDataMaService xpackYamlDataMaService;

    @Autowired
    RoleService roleService;

    @Autowired
    DmRoleService dmRoleService;

    @Override
    public void createSampleData() {

        //查询演示的仓库
        List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setCategory(1));

        try {
            List<Repository> npmRepositories = repositoryList.stream().filter(a -> ("npm").equals(a.getType())).collect(Collectors.toList());
           if (CollectionUtils.isEmpty(npmRepositories)){
               String s = AppContext.getAppHome() + "/file/npm-sample.tgz";
               if (new File(s).exists()){
                   //创建npm 示例数据
                   String repositoryNpmId = createSampleRepository("npm");
                   copyData(repositoryNpmId,"npm");
               }
            }

            List<Repository> mavenRepositories = repositoryList.stream().filter(a -> ("maven").equals(a.getType())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(mavenRepositories)){
                //示例文件不存在 不创建示例数据
                String s = AppContext.getAppHome() + "/file/maven-sample.zip";
                if (!new File(s).exists()){
                  return;
                }

                //创建maven 示例数据
                String repositoryMvnId = createSampleRepository("maven");
                String filePath = copyData(repositoryMvnId, "maven");
                RepositoryUtil.decompressionZip(filePath+"/maven-sample.zip",filePath);
                //修改压缩后仓库名字
                File sourceFile = new File(filePath + "/maven-sample");

                // 创建新的文件对象，包含新的文件名和原始文件所在目录路径
                File newFile = new File(filePath, repositoryMvnId);
                // 使用renameTo()方法重命名.git 仓库
                sourceFile.renameTo(newFile);

                File RpyZipFile = new File(filePath + "/maven-sample.zip");
                FileUtils.deleteQuietly(RpyZipFile);
            }

        }catch (Exception e) {

            throw new SystemException(e);
        }
    }

    @Override
    public void updateRepRole() {
        List<Role> allRole = roleService.findAllRole();


        if (CollectionUtils.isNotEmpty(allRole)){
            for (Role role:allRole){
                //移除项目管理员
                if ((("3").equals(role.getParentId()))||("3").equals(role.getId())){
                    roleService.deleteRole(role.getId());

                    DmRoleQuery dmRoleQuery = new DmRoleQuery();
                    dmRoleQuery.setRoleId(role.getId());
                    List<DmRole> dmRoleList = dmRoleService.findDmRoleListByQuery(dmRoleQuery);
                    if (CollectionUtils.isNotEmpty(dmRoleList)){
                        for (DmRole dmRole:dmRoleList){
                            dmRoleService.deleteDmRole(dmRole.getId());
                        }
                    }
                }

                //修改项目超级管理员
                if (("pro_111111").equals(role.getParentId()) ||("pro_111111").equals(role.getId())){
                    role.setName("项目管理员");
                    roleService.updateRole(role);
                }

                if (("管理员角色").equals(role.getName())){
                    role.setName("管理员");
                    roleService.updateRole(role);
                }
                if (("普通角色").equals(role.getName())){
                    role.setName("普通用户");
                    roleService.updateRole(role);
                }
            }
        }
    }


    /**
     * 创建示例仓库
     * @param type 类型 maven、npm
     */
    public String createSampleRepository(String type) throws IOException {
        String version = ("npm").equals(type) ? "1.0.0" : "1.0.0-SNAPSHOT";
        //创建仓库
        Repository repository = new Repository();
        String repositoryName=type+"-sample-local";
        repository.setName(repositoryName);
        repository.setRepositoryUrl(repositoryName);
        repository.setType(type);
        repository.setRepositoryType("local");
        repository.setDescription("示例制品库");
        repository.setCreateUser("111111");
        repository.setCategory(1);
        String repositoryId = repositoryService.createRepository(repository);


        //创建制品
        Library library = new Library();
        repository.setId(repositoryId);
        library.setLibraryType(type);
        library.setName(type+"-sample");
        library.setRepository(repository);

        library.setNewVersion(version);
        String libraryId = libraryService.createLibrary(library);


        //创建制品版本
        LibraryVersion libraryVersion = new LibraryVersion();
        library.setId(libraryId);
        libraryVersion.setRepository(repository);
        libraryVersion.setLibrary(library);
        libraryVersion.setLibraryType(type);
        libraryVersion.setSize(6144L);

        libraryVersion.setVersion(version);
        libraryVersion.setHash("32bd90901dda737da18c5ccbd6de20b0fff4cbe6");
        libraryVersion.setPullUser("111111");
        if (("npm").equals(type)){
            // 文件路径
            String filePath = AppContext.getAppHome()+"/file/npm-sample-data";
            File file = new File(filePath);
            String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            libraryVersion.setContentJson(content);
        }
        String libraryVersionId = versionService.createLibraryVersion(libraryVersion);


        //创建制品文件信息
        LibraryFile libraryFile = new LibraryFile();
        libraryVersion.setId(libraryVersionId);
        libraryFile.setRepository(repository);
        libraryFile.setLibrary(library);
        libraryFile.setLibraryVersion(libraryVersion);
        libraryFile.setSize(6144L);
        libraryFile.setFileSize("96KB");
        if ("npm".equals(type)){
            libraryFile.setFileName("npm-sample.tgz");
            //文件大小
            File zipFile = new File(AppContext.getAppHome()+"/file/npm-sample.tgz");
            long length = zipFile.length();
            double i =(double)length / 1000;
            long round = Math.round(i);
            libraryFile.setFileUrl(repositoryId+"/npm-sample/"+"npm-sample.tgz");
            libraryFile.setSize(length);
            createLibraryFile(libraryFile,"npm-sample.tgz",round);
        }

        if ("maven".equals(type)){
            libraryFile.setSnapshotVersion("1.0.0-SNAPSHOT");
            String filePath = AppContext.getAppHome()+"/file/maven-sample-data";
            File file = new File(filePath);
            String fileName = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            String result = fileName.replaceAll("\\s", "");
            String[] split = result.split(",");
            for (String name:split){
                libraryFile.setFileName(name);
                String relativePath="sample/tiklab/tiklab-sample-server/1.0.0-SNAPSHOT/20230801.091147-1/"+name;
                libraryFile.setFileUrl(repositoryId+"/"+relativePath);
                libraryFile.setSize(122880L);
                createLibraryFile(libraryFile,relativePath,120);
            }

            //创建maven 制品定位数据
            LibraryMaven libraryMaven = new LibraryMaven();
            libraryMaven.setLibrary(library);
            libraryMaven.setGroupId("sample.tiklab");
            libraryMaven.setRepositoryId(repositoryId);
            libraryMaven.setArtifactId("tiklab-sample-server");
            libraryMavenService.createLibraryMaven(libraryMaven);

            //创建maven仓库版本  快照版和正式版
            RepositoryMaven repositoryMaven = new RepositoryMaven();
            repositoryMaven.setRepository(repository);
            repositoryMaven.setVersion("Snapshot");
            repositoryMavenService.createRepositoryMaven(repositoryMaven);

        }
        return  repositoryId;
    }


    /**
     * 创建示例制品文件
     * @param libraryFile libraryFile
     * @param relativePath relativePath
     */
    public void createLibraryFile( LibraryFile libraryFile,String relativePath,long size){
        libraryFile.setFileSize(size+"KB");
        libraryFile.setRelativePath(relativePath);
        fileService.createLibraryFile(libraryFile);
    }

    /**
     * 复制数据
     * @param repositoryId 制品库id
     * @param type 类型 maven 、npm
     */
    public String copyData(String repositoryId,String type) throws IOException {
        logger.info("appHome路径:"+AppContext.getAppHome());
        String fileName=("npm").equals(type)?"npm-sample.tgz":"maven-sample.zip";

        File tgzFile = new File(AppContext.getAppHome()+"/file/"+fileName);
        String memoryAddress=xpackYamlDataMaService.repositoryAddress();
        if (("npm").equals(type)){
            memoryAddress= memoryAddress+"/"+repositoryId+"/npm-sample";
        }

        File memoryAddressFile = new File(memoryAddress);
        if (!memoryAddressFile.exists()) {
            memoryAddressFile.mkdirs();
        }
        //将zip 拷贝到租户存储仓库文件下面
        FileUtils.copyFileToDirectory(tgzFile,memoryAddressFile);
        return memoryAddress;
    }
}
