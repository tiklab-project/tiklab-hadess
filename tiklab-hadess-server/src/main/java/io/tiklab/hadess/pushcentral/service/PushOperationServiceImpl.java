package io.tiklab.hadess.pushcentral.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.tiklab.core.exception.SystemException;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.hadess.common.HadessFinal;
import io.tiklab.hadess.common.RepositoryUtil;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.library.model.*;
import io.tiklab.hadess.library.service.LibraryFileService;
import io.tiklab.hadess.library.service.LibraryMavenService;
import io.tiklab.hadess.library.service.LibraryVersionService;
import io.tiklab.hadess.pushcentral.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class PushOperationServiceImpl implements PushOperationService{

    private static Logger logger = LoggerFactory.getLogger(PushOperationServiceImpl.class);

    @Autowired
    PushLibraryService pushLibraryService;

    @Autowired
    PushGroupService pushGroupService;

    @Autowired
    LibraryFileService libraryFileService;


    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Autowired
    LibraryMavenService libraryMavenService;


    //推送日志
    public static Map<String , String> pushResultLog = new HashMap<>();

    //推送结果
    public static Map<String , PushOperation> pushResultMap = new HashMap<>();

    @Override
    public String pushGroup(PushOperationQuery pushOperationQuery){
        String loginId = LoginContext.getLoginId();
        pushResultMap.remove(loginId);
        pushResultLog.remove(loginId);

        //初始化推送结果
        PushOperation pushOperation = new PushOperation();
        pushOperation.setStartTime(new Timestamp(System.currentTimeMillis()));
        pushOperation.setResultKey(loginId);
        initPushState(pushOperation,"run");
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable(){
            @Override
            public void run() {
                List<String> pushGroupIds = pushOperationQuery.getPushGroupIds();

              /*  String jsonString = JSON.toJSONString(pushOperationQuery.getPushGroupIds());
                JSONArray objects = JSON.parseArray(jsonString);*/

                for (int i = 0; i < pushGroupIds.size(); i++){
                    String pushGroupId = pushGroupIds.get(i);
                    //查询推送组下面的制品
                    List<PushLibrary> pushLibraryList = pushLibraryService.findPushLibraryList(new PushLibraryQuery().setPushGroupId(pushGroupId));

                    PushGroup pushGroup = pushGroupService.findPushGroup(pushGroupId);
                    if (CollectionUtils.isNotEmpty(pushLibraryList)){
                        joinScanLog(loginId,"开始推送组"+pushGroup.getGroupName());
                        //推送中央仓库
                        if (("center").equals(pushOperationQuery.getPushType())){
                            String libraryType = pushLibraryList.get(0).getLibrary().getLibraryType();
                            if (("maven").equals(libraryType)){
                                mavenPush(pushLibraryList,pushOperation,loginId);
                            }
                            if (("npm").equals(libraryType)){
                                npmPush(pushLibraryList,pushOperation,loginId);
                            }
                        }
                        //推送到hadess
                        if (("hadess").equals(pushOperationQuery.getPushType())){
                            hadessPush(pushLibraryList,pushOperation);
                        }
                    }
                    //推送组里面推送制品为空
                    if (i+1==pushGroupIds.size()&&CollectionUtils.isEmpty(pushLibraryList)){
                        logger.info("仓库组"+pushGroup.getGroupName()+"没有推送制品");
                        joinScanLog(loginId,"仓库组"+pushGroup.getGroupName()+"没有推送制品");
                        initPushState(pushOperation,"fail");
                    }
                }
                //推送成功
                initPushState(pushOperation,"success");
            }});

        return "ok";
    }

    @Override
    public String pushLibrary(PushOperationQuery pushOperationQuery) {
        String loginId = LoginContext.getLoginId();
        pushResultMap.remove(loginId);
        pushResultLog.remove(loginId);

        //初始化推送结果
        PushOperation pushOperation = new PushOperation();
        pushOperation.setStartTime(new Timestamp(System.currentTimeMillis()));
        pushOperation.setResultKey(loginId);
        initPushState(pushOperation,"run");
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable(){
            @Override
            public void run() {
                String jsonString = JSON.toJSONString(pushOperationQuery.getPushLibraryIds());

                JSONArray jsonArray = JSON.parseArray(jsonString);

                //将jsonArray 转为list
                List<String> stringList = new ArrayList<>();
                for (Object value:jsonArray){
                    stringList.add(value.toString());
                }
                //查询将要推送的制品
                List<PushLibrary> pushLibraryList = pushLibraryService.findList(stringList);
                if (CollectionUtils.isNotEmpty(pushLibraryList)){
                    String libraryType = pushLibraryList.get(0).getLibrary().getLibraryType();
                    if (("maven").equals(libraryType)){
                        mavenPush(pushLibraryList,pushOperation,loginId);
                        //推送成功
                        initPushState(pushOperation,"success");
                    }
                    if (("npm").equals(libraryType)){
                        npmPush(pushLibraryList,pushOperation,loginId);
                        //推送成功
                        initPushState(pushOperation,"success");
                    }
                }
            }});

        return "ok";
    }

    @Override
    public PushOperation getPushResult(String key) {
        PushOperation pushOperation = pushResultMap.get(key);

        long startTime = pushOperation.getStartTime().getTime();
        //计算扫描耗时
        String time = RepositoryUtil.timeBad(System.currentTimeMillis()-startTime);
        pushOperation.setPushTimeLong(time);

        pushOperation.setLog(pushResultLog.get(key));

        return pushOperation;
    }

    /**
     * 推送hadess
     * @param pushLibraryList
     */
    public void hadessPush(List<PushLibrary> pushLibraryList,PushOperation pushOperation){
        String loginId = LoginContext.getLoginId();
        String libraryType = pushLibraryList.get(0).getLibrary().getLibraryType();
        try {
            for (PushLibrary pushLibrary:pushLibraryList){
                logger.info("开始推送制品"+pushLibrary.getLibrary().getName());
                joinScanLog(loginId, "开始推送制品" + pushLibrary.getLibrary().getName());
                String libraryId = pushLibrary.getLibrary().getId();

                // 查询最新的版本
                LibraryVersion newVersion = libraryVersionService.findLibraryNewVersion(new LibraryVersionQuery().setLibraryId(libraryId));

                //maven 方式推送
                if (("maven").equals(libraryType)){
                    List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery()
                            .setLibraryId(libraryId)
                            .setLibraryVersionId(newVersion.getId()));
                    if (CollectionUtils.isNotEmpty(libraryFileList)) {
                        for (LibraryFile libraryFile:libraryFileList){
                            //推送的时候排除源码包
                            if (libraryFile.getFileName().contains("sources.jar")){
                                continue;
                            }
                            joinScanLog(loginId, "开始推送制品"+pushLibrary.getLibrary().getName()+"的文件："+libraryFile.getFileName());
                            String address = HadessFinal.HADESS_PATH + "repository/maven-releases/" + libraryFile.getRelativePath();
                            URL url = new URL(address);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setReadTimeout(30000); //设置连接超时时间 单位毫秒
                            conn.setRequestMethod("PUT");
                            conn.setDoOutput(true);

                            //添加头部信息
                            byte[] bytes = "hadess".getBytes();
                            String authorization = Base64.getEncoder().encodeToString(bytes);
                            // 添加自定义头部用户认证信息
                            conn.setRequestProperty("Authorization", authorization);

                            String filePath=yamlDataMaService.repositoryAddress() + "/" +libraryFile.getFileUrl();
                            File file = new File(filePath);
                            byte[] fileData = RepositoryUtil.readFileByte(file);
                            // 传递数据流
                            try (OutputStream os = conn.getOutputStream()) {
                                os.write(fileData);
                                os.flush();
                            }

                            int responseCode = conn.getResponseCode();
                            if (responseCode==200||responseCode==201){
                                joinScanLog(loginId, "制品" + pushLibrary.getLibrary().getName()+" 推送成功");
                            }else {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    joinScanLog(loginId, "制品" + pushLibrary.getLibrary().getName()+" 推送失败："+line);
                                }
                                reader.close();
                            }
                        }
                    }else {
                        joinScanLog(loginId, pushLibrary.getLibrary().getName()+ " 制品不存在文件");
                    }
                }
                // npm 类型推送
                if (("npm").equals(libraryType)){
                    String address =  HadessFinal.HADESS_PATH+"repository/npm-hosted/"+pushLibrary.getLibrary().getName();
                    URL url = new URL(address);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(30000); //设置连接超时时间 单位毫秒
                    conn.setRequestMethod("PUT");
                    conn.setDoOutput(true);

                    //添加head信息
                    conn.setRequestProperty("user-agent", "npm/6.14.15 node");
                    conn.setRequestProperty("referer", "publish");
                    // 使用 Base64 编码
                    byte[] bytes = "admin".getBytes();
                    String authorization = Base64.getEncoder().encodeToString(bytes);
                    // 添加自定义头部用户认证信息
                    conn.setRequestProperty("Authorization", authorization);

                    String contentJson = newVersion.getContentJson();
                    byte[] fileData = contentJson.getBytes();
                    // 传递数据流
                    try (OutputStream os = conn.getOutputStream()) {
                        os.write(fileData);
                        os.flush();
                    }
                    int responseCode = conn.getResponseCode();
                    if (responseCode==200){
                        joinScanLog(loginId, "制品" + pushLibrary.getLibrary().getName()+" 推送成功");
                    }else {
                        //推送失败 获取失败信息
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            joinScanLog(loginId, "制品" + pushLibrary.getLibrary().getName()+" 推送失败："+line);
                        }
                        reader.close();
                    }
                }
            }
        }catch (Exception e){
            joinScanLog(loginId, "推送失败："+e.getMessage());
        }
    }


    /**
     * maven类型推送
     * @param pushLibraryList
     */
    public void mavenPush(List<PushLibrary> pushLibraryList,PushOperation pushOperation,String key) {

        for (PushLibrary pushLibrary : pushLibraryList) {
            try {
                logger.info("开始推送制品"+pushLibrary.getLibrary().getName());
                joinScanLog(key, "开始推送制品" + pushLibrary.getLibrary().getName());
                String libraryId = pushLibrary.getLibrary().getId();
                List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryId));
                if (CollectionUtils.isNotEmpty(libraryFileList)) {
                    //查询pom 并排序
                    List<LibraryFile> libraryPomFiles = libraryFileList.stream().filter(a -> a.getFileName().endsWith(".pom"))
                            .sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
                    LibraryFile libraryFile = libraryPomFiles.get(0);
                    List<LibraryMaven> mavenList = libraryMavenService.findLibraryMavenList(new LibraryMavenQuery().setLibraryId(libraryFile.getLibrary().getId()));
                    LibraryMaven libraryMaven = mavenList.get(0);

                    String version = libraryFile.getLibraryVersion().getVersion();

                    //推送jar
                    List<LibraryFile> libraryJarFiles = libraryFileList.stream().filter(a -> a.getFileName().endsWith(version + ".jar"))
                            .sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(libraryJarFiles)) {
                        LibraryFile libraryJarFile = libraryJarFiles.get(0);
                        mavenExec(libraryMaven, libraryJarFile, key, "jar");
                    }

                    //推送zip
                    List<LibraryFile> libraryZipFiles = libraryFileList.stream().filter(a -> a.getFileName().endsWith(".zip"))
                            .sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(libraryZipFiles)) {
                        LibraryFile libraryZipFile = libraryZipFiles.get(0);
                        mavenExec(libraryMaven, libraryZipFile, key, "zip");
                    }

                    //推送pom
                    Integer integer = mavenExec(libraryMaven, libraryFile, key, "pom");
                    if (integer == 0) {
                        updatePushLibrary(pushLibrary, "succeed");
                    } else {
                        joinFailResult(pushLibrary,pushOperation,key,"推送pom失败");
                    }
                }else {
                    joinFailResult(pushLibrary,pushOperation,key,"没有制品文件");
                }
            } catch (Exception e) {
                joinFailResult(pushLibrary,pushOperation,key,e.getMessage());
            }
        }
    }


    /**
     * maven  zhix
     * @param key  key
     */
    public Integer mavenExec( LibraryMaven libraryMaven, LibraryFile libraryFile,String key,String type) throws Exception {
        String durl;
        String libraryAddress = yamlDataMaService.repositoryAddress() + "/" + libraryFile.getFileUrl();
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
        ProcessBuilder builder;
        if (("jar").equals(type)){
            //执行
            builder = new ProcessBuilder(
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
            builder = new ProcessBuilder(
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

        builder.command();
       // Integer exitCode = exec(builder, "push","");
        builder.command();
        Process process = builder.start();
        //执行日志
        readFile(key,process);
        int exitCode = process.waitFor();
        if (exitCode!=0){
            FileUtils.deleteQuietly(new File(source));
            FileUtils.deleteQuietly(new File(javaDoc));
        }
        FileUtils.deleteQuietly(new File(source));
        FileUtils.deleteQuietly(new File(javaDoc));
        return exitCode;
    }


    /**
     * npm类型推送
     * @param pushLibraryList 推送的制品
     * @param  key 推送制品组id
     */
    public void npmPush(List<PushLibrary> pushLibraryList,PushOperation pushOperation,String key){

        for (PushLibrary pushLibrary:pushLibraryList){
            joinScanLog(key,"开始推送制品"+pushLibrary.getLibrary().getName());
            String libraryId = pushLibrary.getLibrary().getId();

            //通过制品ID、版本查询制品版本
            List<LibraryVersion> libraryVersionList = libraryVersionService.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(libraryId).setVersion(pushLibrary.getLibraryVersion()));
            if (CollectionUtils.isEmpty(libraryVersionList)){
                joinScanLog(key,"制品："+pushLibrary.getLibrary().getName()+"不存在版本");
            }

            List<LibraryFile> fileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryId).setLibraryVersionId(libraryVersionList.get(0).getId()));
            if (CollectionUtils.isEmpty(fileList)){
                joinScanLog(key,"制品："+pushLibrary.getLibrary().getName()+"版本："+pushLibrary.getLibraryVersion()+"不存在");
            }

            //获取该文件上一层文件夹
            LibraryFile libraryFile = fileList.get(0);
            String extractPath = libraryFile.getFileUrl().substring(0, libraryFile.getFileUrl().lastIndexOf(libraryFile.getFileName()));

            //文件的文件夹在服务器中存储的位置
            extractPath=yamlDataMaService.repositoryAddress()+"/"+extractPath;

            try {
                //文件在服务器中的存储位置
                String filePath=yamlDataMaService.repositoryAddress()+"/"+libraryFile.getFileUrl();
                joinScanLog(key,"获取制品存储位置"+filePath);

                // 解压tgz
                ProcessBuilder decompression = new ProcessBuilder("tar", "-xzf", filePath, "-C", extractPath);
                joinScanLog(key,"开始解压制品文件"+ StringUtils.substringAfter(filePath,"/"));
                Process process = decompression.start();
                //执行日志
               // readFile(key,process);
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    logger.info("解压制品"+pushLibrary.getLibrary().getName()+"成功");
                    joinScanLog(key,"解压制品"+pushLibrary.getLibrary().getName()+"成功");
                    joinScanLog(key,"解压成功");

                    extractPath = extractPath + "package";

                    logger.info("解压制品"+pushLibrary.getLibrary().getName()+"成功");
                    joinScanLog(key,"解压制品"+pushLibrary.getLibrary().getName()+"成功");

                    String[] changeDirCommand = { "cd", extractPath };
                    // 创建 ProcessBuilder 对象并设置工作目录
                    ProcessBuilder cdProcessBuilder = new ProcessBuilder(changeDirCommand);
                    cdProcessBuilder.directory(new File(extractPath));

                    // 启动进程并等待执行完成
                    Process cdProcess = cdProcessBuilder.start();
                    int cdExitCode = cdProcess.waitFor();

                    if (cdExitCode == 0) {
                        logger.info("进入推送工作目录"+extractPath);
                        joinScanLog(key,"进入推送工作目录"+extractPath);
                        RepositoryUtil.modifyPackageJson(extractPath,"https://registry.npmjs.org");


                        //连接npm 中央仓库
                        joinScanLog(key,"推送制品"+pushLibrary.getLibrary().getName()+"到中央仓库");
                        String npmCommand = "npm publish";
                        String username = "tiklab";
                        String password = "Darth2020...";
                        String registryUrl = "https://registry.npmjs.org/";
                        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", npmCommand);
                        processBuilder.directory(new File(extractPath));

                        // 设置环境变量传递用户信息
                        processBuilder.environment().put("NPM_USERNAME", username);
                        processBuilder.environment().put("NPM_PASSWORD", password);
                        processBuilder.environment().put("NPM_REGISTRY", registryUrl);
                        Process npmProcess = processBuilder.start();
                        //执行日志
                        readFile(key,npmProcess);
                        int npmCode = npmProcess.waitFor();
                        if (npmCode==0){

                            logger.info("推送制品"+pushLibrary.getLibrary().getName()+"成功");
                            joinScanLog(key,"推送制品"+pushLibrary.getLibrary().getName()+"结果：success");
                            this.updatePushLibrary(pushLibrary,"success");
                        }else {
                            logger.info("推送制品"+pushLibrary.getLibrary().getName()+"失败");
                            joinScanLog(key,"推送制品"+pushLibrary.getLibrary().getName()+"结果：fail");
                            initPushState(pushOperation,"fail");
                            this.updatePushLibrary(pushLibrary,"fail");
                        }
                        /*
                         * 删除解压后的文件
                         * */
                        FileUtils.deleteDirectory(new File(extractPath));
                    }

                }else {
                    logger.info("解压："+filePath+"失败");
                    joinScanLog(key,"解压失败");
                    initPushState(pushOperation,"fail");
                    this.updatePushLibrary(pushLibrary,"fail");
                    throw new SystemException(HadessFinal.FILE_EXCEPTION,"npm推送解压tgz包失败");
                }
            } catch (IOException | InterruptedException e) {
                logger.info("推送制品"+pushLibrary.getLibrary().getName()+"失败："+e.getMessage());
                joinScanLog(key,"推送制品"+pushLibrary.getLibrary().getName()+"失败:"+e.getMessage());
                initPushState(pushOperation,"fail");
                this.updatePushLibrary(pushLibrary,"fail");
                throw new SystemException(e);
            }
            }
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
     *  执行日志
     *  @param process:process
     * @param  key key
     */
    public void  readFile(String key,Process process) throws IOException {
        // 获取命令行输出
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {
            logger.info("执行命令日志:"+line);
            joinScanLog(key,line);
        }

        InputStream errorStream = process.getErrorStream();
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            logger.info("执行命令日志02:"+errorLine);
            joinScanLog(key,errorLine);
        }
    }

    /**
     *  初始化推送结果数据状态
     *  @param pushOperation 推送
     * @param  state
     */
    public void initPushState(PushOperation pushOperation,String state ) {

        pushOperation.setPushResult(state);

        pushResultMap.put(pushOperation.getResultKey(),pushOperation);
    }

    /**
     *  拼接扫描日志
     * @param  key key
     *  @param log 日志
     */
    public void joinScanLog(String key,String log){
        LocalDateTime now = LocalDateTime.now();
        // 自定义时间格式
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String customDateTime = now.format(customFormatter);
        //拼接的日志
        String resultLog = "["+customDateTime + "] " + log;
        String logs = pushResultLog.get(key);
        if (org.apache.commons.lang3.StringUtils.isEmpty(logs)){
            pushResultLog.put(key,resultLog);
        }else {
            pushResultLog.put(key,logs+"\n"+resultLog);
        }
    }

    /**
     *  拼接失败的错误信息
     * @param  key key
     *  @param pushLibrary pushLibrary
     */
    public void joinFailResult(PushLibrary pushLibrary,PushOperation pushOperation,
                               String key,String massage){
        logger.info("推送制品"+pushLibrary.getLibrary().getName()+"失败："+massage);
        joinScanLog(key,"推送制品"+pushLibrary.getLibrary().getName()+"失败:"+massage);
        initPushState(pushOperation,"fail");
        this.updatePushLibrary(pushLibrary,"fail");
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


        return exitCode;
    }

}
