package io.thoughtware.hadess.pushcentral.entity;

import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.library.model.*;
import io.thoughtware.core.exception.SystemException;
import io.thoughtware.hadess.library.service.*;
import io.thoughtware.hadess.pushcentral.model.PushLibrary;
import io.thoughtware.hadess.pushcentral.model.PushLibraryQuery;
import io.thoughtware.hadess.pushcentral.service.PushCenWarehouse;
import io.thoughtware.hadess.pushcentral.service.PushLibraryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class PushCenWarehouseImpl implements PushCenWarehouse {
    private static Logger logger = LoggerFactory.getLogger(PushCenWarehouseImpl.class);
    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    LibraryMavenService libraryMavenService;

    @Autowired
    PushLibraryService pushLibraryService;

    @Autowired
    XpackYamlDataMaService xpakYamlDataMaService;
    //推送结果
    public static Map<String , String> pushResultMap = new HashMap<>();
    //执行列表
    public static Map<String , List<PushLibrary>> execQueue = new HashMap<>();
    //存放的LibraryList
    public static Map<String , List<PushLibrary>> libraryListMap = new HashMap<>();

    @Override
    public String pushCentralWare(PushLibrary pushLibrary) {
        //每次重新执行的时候会清空之前的数据
        if (ObjectUtils.isEmpty(execQueue.get(pushLibrary.getRepositoryId()))){
            libraryListMap.remove(pushLibrary.getRepositoryId());
        }
        //当LibraryList 里面为空的时候存放入
        if (ObjectUtils.isEmpty(libraryListMap.get(pushLibrary.getRepositoryId()))){
            List<PushLibrary> pushLibraryList = pushLibraryService.findPushLibraryList(new PushLibraryQuery().setRepositoryId(pushLibrary.getRepositoryId()));
            libraryListMap.put(pushLibrary.getRepositoryId(),pushLibraryList);

        }
        updateLibraryExecState(pushLibrary,"waite",pushLibrary.getLastPushResult());
        String result=null;
        if (("maven".equals(pushLibrary.getLibrary().getLibraryType()))){
            
            List<PushLibrary> pushLibraries;
            if (ObjectUtils.isEmpty(execQueue.get(pushLibrary.getRepositoryId()))){
                pushLibraries = new ArrayList<>();
                pushLibraries.add(pushLibrary);
                //执行推送
                result = mavenPush(pushLibrary);
            }else {
                 pushLibraries = execQueue.get(pushLibrary.getRepositoryId());
                 pushLibraries.add(pushLibrary);
            }
             execQueue.put(pushLibrary.getRepositoryId(),pushLibraries);
        }
        if (("npm").equals(pushLibrary.getLibrary().getLibraryType())){
            npmPush(pushLibrary);
        }
        return result;
    }

    @Override
    public List<PushLibrary> pushResult(String repositoryId) {
        List<PushLibrary> pushLibraries = libraryListMap.get(repositoryId);
        if (CollectionUtils.isEmpty(pushLibraries)){
            return  Collections.emptyList();
        }

        return pushLibraries;
    }

    /**
     * maven  中央仓库提交
     * @param pushLibrary
     */
    public String mavenPush(PushLibrary pushLibrary){
        String libraryId = pushLibrary.getLibrary().getId();
        //修改执行状态
        updateLibraryExecState(pushLibrary,"exec",pushLibrary.getLastPushResult());


        //查询gpg 是否安装
           /* ProcessBuilder builder = new ProcessBuilder("which", "gpg");
            Integer code = exec(builder, "gpgIsInstall",libraryId);
            if (code==1){
              *//*  //安装gpg
                ProcessBuilder builder01 = new ProcessBuilder("brew", "install", "gpg");
                exec(builder01,"gpgInstall",libraryId);*//*

              *//*  //生成gpg 密钥
                ProcessBuilder builder02 = new ProcessBuilder("gpg", "--gen-key");
                exec(builder02,"gpgSecretKey",versionId);*//*
            }
           *//* //生成gpg 密钥
            ProcessBuilder builder02 = new ProcessBuilder("gpg", "--gen-key");
            exec(builder02,"gpgSecretKey");*//*

            //查询gpg 密钥
            ProcessBuilder builder03 = new ProcessBuilder("gpg", "--list-keys");
            exec(builder03,"gpgKey",libraryId);*/
            List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryId));
            if (CollectionUtils.isNotEmpty(libraryFileList)){
                ExecutorService executorService = Executors.newCachedThreadPool();
                executorService.submit(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            //查询pom
                            List<LibraryFile> libraryPomFiles = libraryFileList.stream().filter(a -> a.getFileName().endsWith(".pom"))
                                    .sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
                            LibraryFile libraryFile = libraryPomFiles.get(0);
                            List<LibraryMaven> mavenList = libraryMavenService.findLibraryMavenList(new LibraryMavenQuery().setLibraryId(libraryFile.getLibrary().getId()));
                            LibraryMaven libraryMaven = mavenList.get(0);

                            String version = libraryFile.getLibraryVersion().getVersion();

                            //执行jar
                            List<LibraryFile> libraryJarFiles = libraryFileList.stream().filter(a -> a.getFileName().endsWith(version+".jar"))
                                    .sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(libraryJarFiles)){
                                LibraryFile libraryJarFile = libraryJarFiles.get(0);
                                mavenExec(libraryMaven,libraryJarFile,libraryId,"jar");
                            }

                            //执行zip
                            List<LibraryFile> libraryZipFiles = libraryFileList.stream().filter(a -> a.getFileName().endsWith(".zip"))
                                    .sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(libraryZipFiles)){
                                LibraryFile libraryZipFile = libraryZipFiles.get(0);
                                mavenExec(libraryMaven,libraryZipFile,libraryId,"zip");
                            }
                            //执行pom
                            Integer integer = mavenExec(libraryMaven, libraryFile, libraryId, "pom");
                            if (integer==0){
                                //修改执行状态
                                updateLibraryExecState(pushLibrary,"end","succeed");

                                updatePushLibrary(pushLibrary,"succeed");
                                //推送后删除队列里面这条数据
                                deleteQueueAndExec(pushLibrary);
                            }else {
                                updateLibraryExecState(pushLibrary,"end","fail");

                                updatePushLibrary(pushLibrary,"fail");
                            }


                        }catch (Exception e) {
                            updateLibraryExecState(pushLibrary,"end","fail");
                            updatePushLibrary(pushLibrary,"fail");
                            logger.info("maven推送失败"+e.getMessage());
                            //推送后删除队列里面这条数据
                            deleteQueueAndExec(pushLibrary);
                        }
                    }
                });
        }else {
                updateLibraryExecState(pushLibrary,"end","fail");
                updatePushLibrary(pushLibrary,"fail");
                //推送后删除队列里面这条数据
                deleteQueueAndExec(pushLibrary);
            }
         return "ok";
    }

    /**
     * maven  zhix
     * @param libraryId  制品id
     */
    public Integer mavenExec( LibraryMaven libraryMaven, LibraryFile libraryFile,String libraryId,String type) throws Exception {
        String durl;
        String libraryAddress = xpakYamlDataMaService.repositoryAddress() + "/" + libraryFile.getFileUrl();
        if (StringUtils.isNotEmpty(libraryFile.getSnapshotVersion())){
            durl="https://s01.oss.sonatype.org/content/repositories/snapshots";
        }else {
            durl="https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/";
        }
       // String type = libraryFile.getFileName().endsWith("jar") ? "jar" : "pom";

        String name = libraryFile.getFileName().substring(0, libraryFile.getFileName().lastIndexOf("."));

        String url="/Users/limingliang/source";
        String oldFile=url+"/test-sources.jar";
        String source=url+"/"+name+"-sources.jar";
        String javaDoc=url+"/"+name+"-javadoc.jar";
        RepositoryUtil.copyFile(oldFile,source);
        RepositoryUtil.copyFile(oldFile,javaDoc);
        ProcessBuilder builder04;
        if (("jar").equals(type)){
            //执行
             builder04 = new ProcessBuilder(
                    "mvn",
                    "gpg:sign-and-deploy-file",
                    "-Dgpg.executable=/opt/homebrew/bin/gpg",
                    "-Durl="+durl,
                    "-DrepositoryId=sonatype-snapshots",
                    "-DgroupId="+libraryMaven.getGroupId(),
                    "-DartifactId="+libraryMaven.getArtifactId(),
                    "-Dversion="+libraryFile.getLibraryVersion().getVersion(),
                    "-Dpackaging="+type,
                    "-Dfile="+libraryAddress,
                    "-Dsources="+source,
                    "-Djavadoc="+javaDoc,
                    "-Dlicense.skipCheck"
            );
        }else {
            //执行
             builder04 = new ProcessBuilder(
                    "mvn",
                    "gpg:sign-and-deploy-file",
                    "-Dgpg.executable=/opt/homebrew/bin/gpg",
                    "-Durl="+durl,
                    "-DrepositoryId=sonatype-snapshots",
                    "-DgroupId="+libraryMaven.getGroupId(),
                    "-DartifactId="+libraryMaven.getArtifactId(),
                    "-Dversion="+libraryFile.getLibraryVersion().getVersion(),
                    "-Dpackaging="+type,
                    "-Dfile="+libraryAddress,
                    "-Dlicense.skipCheck");
        };

        builder04.command();
        Integer pushCode = exec(builder04, "push", libraryId);
        if (pushCode!=0){
            FileUtils.deleteQuietly(new File(source));
            FileUtils.deleteQuietly(new File(javaDoc));
        }
        FileUtils.deleteQuietly(new File(source));
        FileUtils.deleteQuietly(new File(javaDoc));
        return pushCode;
    }

    /**
     * npm  中央仓库提交
     * @param pushLibrary  制品id
     */
    public void npmPush(PushLibrary pushLibrary) {
        String libraryId = pushLibrary.getLibrary().getId();

        List<LibraryFile> fileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryId));
        if (CollectionUtils.isNotEmpty(fileList)){
            //查询最新的版本
            List<LibraryFile> libraryFiles = fileList.stream().sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
            LibraryFile libraryFile = libraryFiles.get(0);
            String extractPath = libraryFile.getFileUrl().substring(0, libraryFile.getFileUrl().lastIndexOf(libraryFile.getFileName()));
            extractPath=xpakYamlDataMaService.repositoryAddress()+"/"+extractPath;
            try {
                String filePath=xpakYamlDataMaService.repositoryAddress()+"/"+libraryFile.getFileUrl();
                // 解压
                ProcessBuilder decompression = new ProcessBuilder("tar", "-xzf", filePath, "-C", extractPath);
                Process process = decompression.start();
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    extractPath = extractPath + "package";

                    RepositoryUtil.modifyPackageJson(extractPath,"https://registry.npmjs.org");

                    //解压包路径
                    String npmCommand = "npm publish";
                    String registryUrl = "https://registry.npmjs.org/";
                    String username = "tiklab";
                    String password = "Darth2020...";
                    ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", npmCommand);
                    processBuilder.directory(new File(extractPath));
                    // 设置环境变量传递用户信息
                    processBuilder.environment().put("NPM_USERNAME", username);
                    processBuilder.environment().put("NPM_PASSWORD", password);
                    processBuilder.environment().put("NPM_REGISTRY", registryUrl);
                    Integer pushCode = exec(processBuilder, "pushNpmCommand", libraryId);
                    if (pushCode==0){
                        updateLibraryExecState(pushLibrary,"end","succeed");
                        this.updatePushLibrary(pushLibrary,"succeed");
                    }else {
                        updateLibraryExecState(pushLibrary,"end","fail");
                        this.updatePushLibrary(pushLibrary,"fail");
                    }
                    /**
                     *  删除解压后的文件
                     */
                  //  FileUtils.deleteDirectory(new File(extractPath));
                }else {
                    updateLibraryExecState(pushLibrary,"end","fail");
                    this.updatePushLibrary(pushLibrary,"fail");
                    pushResultMap.put(libraryId,"解压文件失败");
                    throw new SystemException("npm推送解压tgz包失败");
                }
            } catch (IOException | InterruptedException e) {
                updateLibraryExecState(pushLibrary,"end","fail");
                this.updatePushLibrary(pushLibrary,"fail");
                pushResultMap.put(libraryId,"解压文件失败");
               throw new SystemException(e.getMessage());
            }
        }
    }


    /**
     * 执行 命令
     * @param builder
     * @param  type
     * @return
     */
    public Integer exec( ProcessBuilder builder,String type,String libraryId) throws IOException, InterruptedException {
        Process process = builder.start();

        // 获取命令行输出
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        // 读取命令行输出
        StringBuilder excOutput = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            logger.info(type+"执行命令日志01:"+line);
            excOutput.append(line);
        }

        StringBuilder output = new StringBuilder();
        InputStream errorStream = process.getErrorStream();
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            logger.info(type+"执行命令日志:"+errorLine);
            output.append(errorLine);
        }
        // 等待命令执行完成
        int exitCode = process.waitFor();
        logger.info(type+"执行命令code:"+exitCode);

        // 检查命令的退出码和输出来确定gpg是否安装
        if (("gpgIsInstall").equals(type)){
            if (exitCode == 0 && output.length() > 0) {
              return 0;
            } else {
               return 1;
            }
        }
        if ("pushNpmCommand".equals(type)){
            if ( output.toString().contains("code E403")) {
                pushResultMap.put(libraryId,"403 推送中央仓库的版本不能重复且必须大于仓库中存在的版本");
            }
        }

        return exitCode;
    }

    /**
     * 修改推送的结果
     * @param pushLibrary pushLibrary
     * @param  resultState  结果状态
     * @return
     */
    public void updatePushLibrary(PushLibrary pushLibrary,String resultState ){
        pushLibrary.setLastPushTime(new Timestamp(System.currentTimeMillis()));
        pushLibrary.setLastPushResult(resultState);
        pushLibraryService.updatePushLibrary(pushLibrary);
    }
    /**
     * 修改制品执行的状态
     * @param pushLibrary pushLibrary
     * @param execState 执行状态 waite、exec、end
     * @param  resultState 结果状态
     * @return
     */
    public void updateLibraryExecState(PushLibrary pushLibrary,String execState,String resultState){
        List<PushLibrary> pushLibraries = libraryListMap.get(pushLibrary.getRepositoryId());
        List<Object> objectList = pushLibraries.stream().map(Libraries -> {
            if (pushLibrary.getId() .equals(Libraries.getId()) ) {
                Libraries.setExecState(execState);
                Libraries.setLastPushResult(resultState);
                Libraries.setLastPushTime(new Timestamp(System.currentTimeMillis()));
            }
            return Libraries;
        }).collect(Collectors.toList());
    }


    /**
     * 推送完成后删除list里面的队列、如果队列中仍有数据并执行
     * @param pushLibrary pushLibrary
     * @return
     */
    public void deleteQueueAndExec(PushLibrary pushLibrary){
        List<PushLibrary> pushLibraries = execQueue.get(pushLibrary.getRepositoryId());
        Iterator<PushLibrary> iterator = pushLibraries.iterator();
        while (iterator.hasNext()) {
            PushLibrary library = iterator.next();
            if (library.getId() == pushLibrary.getId()) {
                iterator.remove();
                break;
            }
        }
        //删除后查询队列中是否还有
        List<PushLibrary> Libraries = execQueue.get(pushLibrary.getRepositoryId());
        if (CollectionUtils.isNotEmpty(Libraries)){
            mavenPush(Libraries.get(0));
        }else {
            libraryListMap.remove(pushLibrary.getRepositoryId());
        }
    }



}
