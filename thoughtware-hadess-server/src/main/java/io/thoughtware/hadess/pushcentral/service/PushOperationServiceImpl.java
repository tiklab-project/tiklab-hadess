package io.thoughtware.hadess.pushcentral.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.thoughtware.core.exception.SystemException;
import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.hadess.common.HadessFinal;
import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.library.model.LibraryFile;
import io.thoughtware.hadess.library.model.LibraryFileQuery;
import io.thoughtware.hadess.library.model.LibraryVersion;
import io.thoughtware.hadess.library.model.LibraryVersionQuery;
import io.thoughtware.hadess.library.service.LibraryFileService;
import io.thoughtware.hadess.library.service.LibraryVersionService;
import io.thoughtware.hadess.pushcentral.model.*;
import io.thoughtware.hadess.scan.model.ScanRecord;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    XpackYamlDataMaService xpakYamlDataMaService;


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
                String jsonString = JSON.toJSONString(pushOperationQuery.getPushGroupIds());

                JSONArray objects = JSON.parseArray(jsonString);
                for (Object value:objects){
                    //    String pushGroupId = JSON.toJSONString(value);
                    String pushGroupId = value.toString();
                    //查询推送组下面的制品
                    List<PushLibrary> pushLibraryList = pushLibraryService.findPushLibraryList(new PushLibraryQuery().setPushGroupId(pushGroupId));
                    if (CollectionUtils.isNotEmpty(pushLibraryList)){
                        String libraryType = pushLibraryList.get(0).getLibrary().getLibraryType();
                        if (("maven").equals(libraryType)){
                            mavenPush(pushLibraryList);
                        }
                        if (("npm").equals(libraryType)){

                            npmPush(pushLibraryList,pushOperation,loginId);
                            //推送成功
                            initPushState(pushOperation,"success");
                        }
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
     * maven类型推送
     * @param pushLibraryList
     */
    public void mavenPush(List<PushLibrary> pushLibraryList){

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
            extractPath=xpakYamlDataMaService.repositoryAddress()+"/"+extractPath;

            try {
                //文件在服务器中的存储位置
                String filePath=xpakYamlDataMaService.repositoryAddress()+"/"+libraryFile.getFileUrl();
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

                    logger.info(""+pushLibrary.getLibrary().getName()+"成功");
                    joinScanLog(key,"解压制品"+pushLibrary.getLibrary().getName()+"成功");
                    String[] changeDirCommand = { "cd", extractPath };
                    // 创建 ProcessBuilder 对象并设置工作目录
                    ProcessBuilder cdProcessBuilder = new ProcessBuilder(changeDirCommand);
                    cdProcessBuilder.directory(new File(extractPath));
                    // 启动进程并等待执行完成
                    Process cdProcess = cdProcessBuilder.start();
                    int cdExitCode = cdProcess.waitFor();

                    if (cdExitCode == 0) {
                        RepositoryUtil.modifyPackageJson(extractPath,"https://registry.npmjs.org");
                        String username = "tiklab";
                        String password = "Darth2020...";

                        //连接npm 中央仓库
                        joinScanLog(key,"推送制品"+pushLibrary.getLibrary().getName()+"到中央仓库");
                        String npmCommand = "npm publish";
                        String registryUrl = "https://registry.npmjs.org/";
                        ProcessBuilder processBuilder = new ProcessBuilder(npmCommand);

                        // 设置环境变量传递用户信息
                        processBuilder.environment().put("NPM_USERNAME", username);
                        processBuilder.environment().put("NPM_PASSWORD", password);
                        processBuilder.environment().put("NPM_REGISTRY", registryUrl);
                        Process npmProcess = processBuilder.start();
                        //执行日志
                        readFile(key,npmProcess);
                        int npmCode = npmProcess.waitFor();
                        // Integer npmCode = exec(processBuilder, "pushNpmCommand", key);
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
                        FileUtils.deleteDirectory(new File(extractPath+"/package"));
                    }


                }else {
                    logger.info("解压："+filePath+"失败");
                    joinScanLog(key,"解压失败");
                    initPushState(pushOperation,"fail");
                    this.updatePushLibrary(pushLibrary,"fail");
                    throw new SystemException("npm推送解压tgz包失败");
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
     * 执行 命令
     * @param builder
     * @param  type
     * @return
     */
    public Integer exec( ProcessBuilder builder,String type,String key) throws IOException, InterruptedException {
        Process process = builder.start();

        // 获取命令行输出
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        // 读取命令行输出
        StringBuilder excOutput = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            logger.info(type+"执行命令日志01:"+line);
            joinScanLog(key,line);
            excOutput.append(line);
        }

        StringBuilder output = new StringBuilder();
        InputStream errorStream = process.getErrorStream();
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            logger.info("执行命令日志02:"+errorLine);
            joinScanLog(key,errorLine);
            excOutput.append(line);

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
