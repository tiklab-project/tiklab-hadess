package io.thoughtware.hadess.scan.service;

import com.alibaba.fastjson.JSONObject;
import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.UuidGenerator;
import io.thoughtware.hadess.library.model.LibraryFile;
import io.thoughtware.hadess.library.model.LibraryFileQuery;
import io.thoughtware.hadess.library.model.LibraryVersion;
import io.thoughtware.hadess.library.model.LibraryVersionQuery;
import io.thoughtware.hadess.scan.model.*;
import io.thoughtware.core.context.AppHomeContext;
import io.thoughtware.core.exception.SystemException;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.library.model.*;
import io.thoughtware.hadess.library.service.LibraryFileService;
import io.thoughtware.hadess.library.service.LibraryService;
import io.thoughtware.hadess.library.service.LibraryVersionService;
import io.thoughtware.hadess.scan.model.*;
import io.thoughtware.user.user.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.Size;
import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class ScanServiceImpl implements ScanService {

    private static Logger logger = LoggerFactory.getLogger(ScanServiceImpl.class);

    @Autowired
    ScanSetService scanSetService;

    @Autowired
    ScanLibraryService scanLibraryService;

    @Autowired
    ScanRecordService scanRecordService;

    @Autowired
    ScanRelyService scanRelyService;

    @Autowired
    ScanResultService scanResultService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    XpackYamlDataMaService xpackYamlDataMaService;

    @Autowired
    ScanPlayService scanPlayService;

    public static Map<String,ScanQueue> scanLibraryResult = new HashMap<>();

    //执行扫描的日志
    public static Map<String , String> scanExecLog = new HashMap<>();

    //执行扫描的状态
    public static Map<String , ScanRecord> scanExecRecord = new HashMap<>();


    //执行扫描执行开始时间
    public static Map<String , Long> scanExecStarTime = new HashMap<>();

    //扫描漏洞数量
    public static Map<String , Map<String,Integer>> scanHoleNum = new HashMap<>();


    @Override
    public String execScan(String scanPlayId) {

        long starTime = System.currentTimeMillis();

        //执行开始时间
        scanExecStarTime.put(scanPlayId,starTime);
        //每次执行先清空当前计划的日志
        scanExecLog.remove(scanPlayId);
        scanExecRecord.remove(scanPlayId);
        scanHoleNum.remove(scanPlayId);

        //初始化扫描记录数据
        ScanRecord scanRecord = initScanRecord(scanPlayId,starTime);
        //总的扫描记录id
        String generalId = scanRecord.getId();
        //拼接日志
        joinScanLog(scanPlayId, "获取扫描任务");

        List<ScanLibrary> scanLibraryList = scanLibraryService.findScanLibraryList(new ScanLibraryQuery().setScanPlayId(scanPlayId));
        if (CollectionUtils.isEmpty(scanLibraryList)){
            //拼接日志
            joinScanLog(scanPlayId, "扫描失败制品文件为空");
            initScanRecordState(scanRecord,"fail");

            //更新总的扫描记录
            updateGeneralScanRecord(scanRecord);
            return null;
        }

        //添加开始的扫描
        //addScanResult("start",scanPlayId,scanLibraryList);

       // updateScanPlay(scanPlayId);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable(){
            @Override
            public void run() {

                try {
                    //拼接日志
                    joinScanLog(scanPlayId, "获取扫描环境");
                    String openscaPath = xpackYamlDataMaService.getOpenScanUrl();
                    logger.info("openscan执行路径:"+openscaPath);
                    String[] changeDirCommand = { "cd", openscaPath };
                    // 创建 ProcessBuilder 对象并设置工作目录
                    ProcessBuilder processBuilder = new ProcessBuilder(changeDirCommand);
                    processBuilder.directory(new File(openscaPath));

                    // 启动进程并等待执行完成
                    Process process = processBuilder.start();
                    //执行日志
                    readFile(scanPlayId,process);

                    int exitCode = process.waitFor();

                    if (exitCode == 0) {
                        //拼接日志
                        joinScanLog(scanPlayId, "扫描环境获取成功");

                        for (ScanLibrary scanLibrary:scanLibraryList){
                            joinScanLog(scanPlayId, "开始扫描制品:"+scanLibrary.getLibrary().getName());
                            logger.info("开始扫描制品:"+scanLibrary.getLibrary().getName());
                            List<LibraryVersion> versionList = libraryVersionService.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(scanLibrary.getLibrary().getId()));
                            if (CollectionUtils.isNotEmpty(versionList)) {
                                List<LibraryVersion> versions = versionList.stream().sorted(Comparator.comparing(LibraryVersion::getCreateTime).reversed()).collect(Collectors.toList());
                                LibraryVersion libraryVersion = versions.get(0);
                                //创建扫描制品结果
                                addScanRecord(scanRecord,scanLibrary,libraryVersion.getVersion());

                                //查询制品文件
                                List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(scanLibrary.getLibrary().getId()).setLibraryVersionId(libraryVersion.getId()));
                                LibraryFile libraryFile;
                                if (libraryVersion.getLibraryType().equals("maven")){
                                    List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> a.getFileName().endsWith(".pom")).collect(Collectors.toList());

                                    // List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> a.getFileName().endsWith(".jar")&&!a.getFileName().endsWith("sources.jar")).collect(Collectors.toList());
                                    libraryFile = libraryFiles.get(0);
                                }else {
                                    libraryFile = libraryFileList.get(0);
                                }
                                logger.info("扫描文件名称:"+libraryFile);

                                //扫描输出的结果文件存储位置
                                String scanFolderPath = xpackYamlDataMaService.scanFileAddress() + "/" + String.valueOf(System.currentTimeMillis()).substring(0, 9);
                                File scanFile = new File(scanFolderPath);
                                if (!scanFile.exists()) {
                                    scanFile.mkdirs();
                                }
                                String scanFilePath = scanFolderPath + "/" + libraryFile.getLibrary().getName() + ".json";
                                //选择扫描类型
                                String[] command = scanType(libraryFile, scanFilePath);
                                logger.info("scan文件存储路径:"+scanFilePath);
                                processBuilder = new ProcessBuilder(command);
                                processBuilder.directory(new File(openscaPath));

                                Process scanProcess = processBuilder.start();
                                //执行日志
                                readFile(scanPlayId,process);

                                int exit = scanProcess.waitFor();
                                logger.info("执行结果:"+exit);
                                //执行成功解析数据
                                if (exit==0){

                                    //拼接日志
                                    joinScanLog(scanPlayId, "扫描制品成功:"+scanLibrary.getLibrary().getName());
                                    //initScanRecordState(scanRecord,"success");

                                    logger.info("扫描成功");
                                    //读取扫描的文件
                                    String resultData = readScanFile(scanFilePath);
                                    JSONObject allData =(JSONObject) JSONObject.parse(resultData);
                                    Object children = allData.get("children");
                                    List<JSONObject> childrenList = (List<JSONObject>) children;
                                    //创建扫描依赖
                                    createScanRely( childrenList,scanRecord,null,null);

                                    //主制品漏洞
                                    Object vulnerabilities = allData.get("vulnerabilities");
                                    if (!ObjectUtils.isEmpty(vulnerabilities)){
                                        List<JSONObject> holeList = (List<JSONObject>) vulnerabilities;
                                        createScanResult(holeList,scanLibrary.getLibrary().getId(),scanRecord,"scanLibrary");
                                    }
                                    //单个制品扫描成功
                                    ScanRecord record = updateScanRecord(scanRecord);
                                    scanLibrary.setScanRecord(record);
                                    scanLibrary.setScanResult("success");

                                    logger.info("扫描成功结束");
                                }else {
                                    /*
                                    * 扫描计划中的一个制品扫描失败跳出当前循环、不再执行
                                    * */
                                    logger.info("扫描制品失败"+scanLibrary.getLibrary().getName());
                                    //拼接日志
                                    joinScanLog(scanPlayId, "扫描制品失败:"+scanLibrary.getLibrary().getName());
                                    initScanRecordState(scanRecord,"fail");
                                    scanLibrary.setScanResult("false");


                                    //单个制品扫描失败
                                    initScanRecordState(scanRecord,"fail");
                                    updateScanRecord(scanRecord);
                                    //更新总的扫描记录
                                    scanRecord.setId(generalId);
                                    updateGeneralScanRecord(scanRecord);
                                    return;
                                }
                            }
                        }

                        //循环完成、制品扫描成功
                        logger.info("扫描计划扫描成功");
                        initScanRecordState(scanRecord,"success");
                        joinScanLog(scanRecord.getScanPlayId(), "解析扫描结果");
                        //更新总的扫描记录
                        scanRecord.setId(generalId);
                        updateGeneralScanRecord(scanRecord);
                    }else {
                        /*
                        * 获取扫描环境失败
                        * */
                        logger.info("获取扫描环境失败");
                        //拼接日志
                        joinScanLog(scanPlayId, "获取扫描环境失败");
                        initScanRecordState(scanRecord,"fail");
                        //更新总的扫描记录
                        scanRecord.setId(generalId);
                        updateGeneralScanRecord(scanRecord);
                        return;
                    }
                    //addScanResult("end",scanPlayId,scanLibraryList);
                }catch (Exception e){
                   /* logger.info("制品扫描失败:"+e.getMessage());
                    scanLibrary.setScanResult("false");*/
                    //拼接日志
                    joinScanLog(scanPlayId, "扫描失败:"+e.getMessage());
                    initScanRecordState(scanRecord,"fail");


                    //更新总的扫描记录
                    scanRecord.setId(generalId);
                    updateGeneralScanRecord(scanRecord);

                    addScanResult("end",scanPlayId,scanLibraryList);
                    throw new SystemException(e.getMessage());
                }
            }
        });
        return "OK";
    }

    @Override
    public ScanQueue findExecResult(String scanPlayId) {
       /* Long startTime = scanExecStarTime.get(scanPlayId);

        //计算扫描耗时
        String time = RepositoryUtil.timeBad(System.currentTimeMillis()-startTime);
        ScanRecord scanRecord = scanExecRecord.get(scanPlayId);
        if (!ObjectUtils.isEmpty(scanRecord)){
            scanRecord.setScanTimeLong(time);
            scanRecord.setLog(scanExecLog.get(scanPlayId));
        }*/

       ScanQueue queue = scanLibraryResult.get(scanPlayId);
        return queue;
    }

    @Override
    public ScanRecord findExecState(String scanPlayId) {
         Long startTime = scanExecStarTime.get(scanPlayId);

        //计算扫描耗时
        String time = RepositoryUtil.timeBad(System.currentTimeMillis()-startTime);
        ScanRecord scanRecord = scanExecRecord.get(scanPlayId);
        if (!ObjectUtils.isEmpty(scanRecord)){
            scanRecord.setScanTimeLong(time);
            scanRecord.setLog(scanExecLog.get(scanPlayId));
        }

        return scanRecord;
    }

    /**
     * 创建扫描结果
     * @param scanLibrary 扫描的制品
     * @return
     */
    public void addScanRecord(ScanRecord scanRecord,ScanLibrary scanLibrary,String version){
        scanRecord.setLibraryVersion(version);
        scanRecord.setScanLibraryId(scanLibrary.getId());
        scanRecord.setLibrary(scanLibrary.getLibrary());
        scanRecord.setRecordType("each");
        String scanRecordId = scanRecordService.createScanRecord(scanRecord);
        scanRecord.setId(scanRecordId);
    }


    /**
     * 更新各制品的扫描结果
     * @param  scanRecord scanRecord
     */
    public ScanRecord updateScanRecord(ScanRecord scanRecord){

        String timeBad = RepositoryUtil.timeBad(System.currentTimeMillis()-scanRecord.getCreateTime().getTime());
        scanRecord.setScanTimeLong(timeBad);
        //scanRecord.setLog(scanExecLog.get(scanRecord.getScanPlayId()));
        List<ScanResult> scanResultList = scanResultService.findScanResultList(new ScanResultQuery().setScanRecordId(scanRecord.getId()));
        if (CollectionUtils.isNotEmpty(scanResultList)){
            List<ScanResult> severityHole = scanResultList.stream().filter(a -> a.getHoleLevel() == 1).collect(Collectors.toList());
            List<ScanResult> highHole = scanResultList.stream().filter(a -> a.getHoleLevel() == 2).collect(Collectors.toList());
            List<ScanResult> middleHole = scanResultList.stream().filter(a -> a.getHoleLevel() == 3).collect(Collectors.toList());
            List<ScanResult> lowHole = scanResultList.stream().filter(a -> a.getHoleLevel() == 4).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(severityHole)){
                putHoleMap("severityHole",severityHole.size(),scanRecord.getScanPlayId());
                scanRecord.setHoleSeverity(severityHole.size());
            }
            if (CollectionUtils.isNotEmpty(highHole)){
                putHoleMap("highHole",highHole.size(),scanRecord.getScanPlayId());
                scanRecord.setHoleHigh(highHole.size());
            }
            if (CollectionUtils.isNotEmpty(middleHole)){
                putHoleMap("middleHole",middleHole.size(),scanRecord.getScanPlayId());
                scanRecord.setHoleMiddle(middleHole.size());
            }
            if (CollectionUtils.isNotEmpty(lowHole)){
                putHoleMap("lowHole",lowHole.size(),scanRecord.getScanPlayId());
                scanRecord.setHoleLow(lowHole.size());
            }
        }
        scanRecordService.updateScanRecord(scanRecord);
        scanRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return scanRecord;
    }

    //更新总的扫描结果
    public void updateGeneralScanRecord(ScanRecord scanRecord){
        Long startTime = scanExecStarTime.get(scanRecord.getScanPlayId());
        String timeBad = RepositoryUtil.timeBad(System.currentTimeMillis()-startTime);
        scanRecord.setScanTimeLong(timeBad);

        String log = scanExecLog.get(scanRecord.getScanPlayId());
        scanRecord.setLog(log);
        scanRecord.setRecordType("general");
        if (("success").equals(scanRecord.getScanResult())){
            Map<String, Integer> holeMap = scanHoleNum.get(scanRecord.getScanPlayId());
            if (!ObjectUtils.isEmpty(holeMap)){
                scanRecord.setHoleSeverity(holeMap.get("severityHole"));
                scanRecord.setHoleHigh(holeMap.get("highHole"));
                scanRecord.setHoleMiddle(holeMap.get("middleHole"));
                scanRecord.setHoleLow(holeMap.get("lowHole"));
            }
            joinScanLog(scanRecord.getScanPlayId(), "解析成功,扫描完成");
        }

        scanRecordService.updateScanRecord(scanRecord);
    }


    /**
     * 扫描方式
     * @param libraryFile libraryFile
     * @return
     */
    public String[] scanType(LibraryFile libraryFile,String scanFilePath){
        //本地漏洞库
        String leakCvePath = xpackYamlDataMaService.getLocalHoleUrl();
        //扫描设置
        List<ScanSet> scanSetList = scanSetService.findAllScanSet();
        ScanSet scanSet = scanSetList.get(0);

        String filePath = xpackYamlDataMaService.repositoryAddress() + "/" + libraryFile.getFileUrl();
        //String[] command = { "./opensca-cli","-url", scanSet.getAddress(),"-token",scanSet.getToken(),"-path",filePath,"-out",scanFilePath };

        String[] command = { "./opensca-cli","-db", leakCvePath,"-path",filePath,"-out",scanFilePath };

        logger.info("scan路径:"+scanSet.getAddress());
        logger.info("scan执行文件路径filePath:"+filePath);
        return command;
    }

    /**
     * 读取扫描文件
     * @param scanFilePath 扫描结果文件
     * @return
     */
    public String readScanFile(String scanFilePath) throws IOException {
        File scanFile = new File(scanFilePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) scanFile.length());
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(scanFile));
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }

        String string = bos.toString();
        return string;
    }

    /**
     * 添加扫描漏洞
     * @param holeList 漏洞list
     * @param  type  漏洞类型 relyLibrary、scanLibrary
     * @param  scanRecord scanRecord
     * @return
     */
    public void createScanResult(List<JSONObject> holeList,String scanRelyId,ScanRecord scanRecord,String type){
        for (JSONObject hole:holeList){
            ScanResult scanResult = new ScanResult();

            scanResult.setScanRecordId(scanRecord.getId());
            scanResult.setScanLibraryId(scanRecord.getScanLibraryId());
            scanResult.setHoleType(type);
            //漏洞等级
            Integer level = Integer.valueOf(hole.get("security_level_id").toString());
            scanResult.setHoleLevel(level);
            scanResult.setLibraryId(scanRelyId);
            scanResult.setHoleDesc(hole.get("description").toString());
            if (!ObjectUtils.isEmpty(hole.get("suggestion"))){
                scanResult.setRepairSuggest(hole.get("suggestion").toString());
            }
            if (!ObjectUtils.isEmpty(hole.get("release_date"))){
                scanResult.setReleaseTime(hole.get("release_date").toString());
            }
            scanResult.setHoleName(hole.get("name").toString());

            if (!ObjectUtils.isEmpty(hole.get("cwe_id"))){
                scanResult.setHoleCwe(hole.get("cwe_id").toString());
            }
            if (!ObjectUtils.isEmpty(hole.get("cve_id"))){
                scanResult.setHoleCve(hole.get("cve_id").toString());
            }
            if (!ObjectUtils.isEmpty(hole.get("cnvd_id"))){
                scanResult.setHoleCnvd(hole.get("cnvd_id").toString());
            }
            if (!ObjectUtils.isEmpty(hole.get("cnnvd_id"))){
                scanResult.setHoleCnnvd(hole.get("cnnvd_id").toString());
            }
            if (!ObjectUtils.isEmpty(hole.get("id"))){
                scanResult.setHoleXmirror(hole.get("id").toString());
            }
            scanResultService.createScanResult(scanResult);
        }
    }


    /**
     * 添加扫描依赖
     * @param childrenList
     * @param scanRecord scanRecord
     * @param  scanRelyId 父级id
     * @param   oneRelyId 第一层依赖id
     */
    public void createScanRely(List<JSONObject> childrenList,ScanRecord scanRecord, String scanRelyId,String oneRelyId){

        String scanLibraryId = scanRecord.getScanLibraryId();
        if (!ObjectUtils.isEmpty(childrenList)&&childrenList.size()>0){
            for (JSONObject data:childrenList){
                ScanRely scanRely = new ScanRely();
                if (!ObjectUtils.isEmpty(scanRelyId)){
                    scanRely.setRelyParentId(scanRelyId);
                }

                ScanLibrary scanLibrary = new ScanLibrary();
                scanLibrary.setId(scanLibraryId);
                scanRely.setScanLibrary(scanLibrary);
                scanRely.setScanRecordId(scanRecord.getId());
                scanRely.setGeneralRecordId(scanRecord.getGeneraRecordId());
                //licenses
                List<JSONObject> licenses = (List<JSONObject>) data.get("licenses");
                if (CollectionUtils.isNotEmpty(licenses)){
                    List<Object> licenseName = licenses.stream().map(a -> a.get("name")).collect(Collectors.toList());
                    String license = licenseName.toString();
                    if (!("[]").equals(license)){
                        String replace = license.replace("[", "").replace("]", "");
                        scanRely.setRelyLicenses(replace);
                    }
                }
                if (StringUtils.isNotEmpty(oneRelyId)){
                    scanRely.setRelyOneId(oneRelyId);
                }

                scanRely.setRelyLanguage(data.get("language").toString());
                scanRely.setRelyName(data.get("name").toString());
                scanRely.setRelyVendor(data.get("vendor").toString());
                scanRely.setRelyVersion(data.get("version").toString());
                //path
                String paths = data.get("paths").toString();
                String path = paths.substring(paths.indexOf("/[")+1);
                String substring = path.substring(0, path.length() - 2);
                scanRely.setRelyPath(substring);

                //漏洞数量
                Object indirectVulnerabilities = data.get("indirect_vulnerabilities");
                if (!ObjectUtils.isEmpty(indirectVulnerabilities)){
                    scanRely.setHoleCount(Integer.valueOf(indirectVulnerabilities.toString()));
                }
                //依赖
                Object direct = data.get("direct");
                if (!ObjectUtils.isEmpty(direct)){
                    if (("true").equals(data.get("direct").toString())){
                        scanRely.setRelyType("direct");
                    }else {
                        scanRely.setRelyType("indirect");
                    }
                }

                String relyId = scanRelyService.createScanRely(scanRely);

                //漏洞
                Object vulnerabilities = data.get("vulnerabilities");
                if (!ObjectUtils.isEmpty(vulnerabilities)){
                    List<JSONObject> holeList = (List<JSONObject>) vulnerabilities;
                    createScanResult(holeList,relyId,scanRecord,"relyLibrary");
                }
                Object dataChildren = data.get("children");
                if (!ObjectUtils.isEmpty(dataChildren)){
                    //scanRelyId 为空 为第一层依赖
                    if (ObjectUtils.isEmpty(scanRelyId)){
                        createScanRely( (List<JSONObject>) dataChildren,scanRecord,relyId,relyId);
                    }else {
                        createScanRely( (List<JSONObject>) dataChildren,scanRecord,relyId,oneRelyId);
                    }
                }
            }
        }
    }

    /**
     * 更新扫描计划
     * @param  scanPlayId 扫描计划的id
     */
    public void updateScanPlay(String scanPlayId){
        ScanPlay scanPlay = scanPlayService.findScanPlay(scanPlayId);

        scanPlayService.updateScanPlay(scanPlay);
    }



    /**
     * 添加扫描结果
     * @param  state 扫描状态
     * @param  scanPlayId 扫描计划的id
     * @param  scanLibraryList  扫描制品list
     */
    public void addScanResult(String state,String scanPlayId,List<ScanLibrary> scanLibraryList){
        ScanQueue scanQueue = new ScanQueue();
        scanQueue.setState(state);
        scanQueue.setScanPlayId(scanPlayId);
        scanQueue.setScanLibraryList(scanLibraryList);
        scanLibraryResult.put(scanPlayId,scanQueue);
    }
    /**
     * 获取随机数
     */
    public String getRandom(){
        String math = UuidGenerator.gen(8);
        List<ScanRecord> scanRecordList = scanRecordService.findScanRecordList(new ScanRecordQuery().setScanGroup(math));
        if (CollectionUtils.isNotEmpty(scanRecordList)){
            getRandom();
        }
        return math;
    }

    /**
     *  初始化扫描结果数据
     *  @param scanPlayId 扫描计划
     * @param  startTime 开始时间
     */
    public ScanRecord initScanRecord(String  scanPlayId,long startTime ) {
        String math = getRandom();
        ScanRecord scanRecord = new ScanRecord();
        scanRecord.setScanGroup(math);
        scanRecord.setScanPlayId(scanPlayId);
        scanRecord.setScanResult("run");
        scanRecord.setScanWay("hand");
        scanRecord.setCreateTime(new Timestamp(startTime));

        User user = new User();
        user.setId(LoginContext.getLoginId());
        scanRecord.setScanUser(user);
        scanExecRecord.put(scanPlayId,scanRecord);

        //创建总的扫描记录
        String scanRecordId = scanRecordService.createScanRecord(scanRecord);
        scanRecord.setId(scanRecordId);
        scanRecord.setGeneraRecordId(scanRecordId);
        return scanRecord;
    }

    /**
     *  初始化扫描结果数据状态
     *  @param scanRecord 扫描计划
     * @param  state
     */
    public void initScanRecordState(ScanRecord scanRecord,String state ) {

        scanRecord.setScanResult(state);

        scanExecRecord.put(scanRecord.getScanPlayId(),scanRecord);
    }

    /**
     *  拼接扫描日志
     * @param  scanPlayId 扫描记录
     *  @param log 日志
     */
    public void joinScanLog(String scanPlayId,String log){
        LocalDateTime now = LocalDateTime.now();
        // 自定义时间格式
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String customDateTime = now.format(customFormatter);
        //拼接的日志
        String resultLog = "["+customDateTime + "] " + log;
        String logs = scanExecLog.get(scanPlayId);
        if (org.apache.commons.lang3.StringUtils.isEmpty(logs)){
            scanExecLog.put(scanPlayId,resultLog);
        }else {
            scanExecLog.put(scanPlayId,logs+"\n"+resultLog);
        }
    }

    /**
     *  执行日志
     *  @param process:process
     * @param  scanPlayId 扫描计划的id
     */
    public void  readFile(String scanPlayId,Process process) throws IOException {

        // 获取命令行输出
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {
            logger.info("执行命令日志:"+line);
            joinScanLog(scanPlayId,line);

        }

        InputStream errorStream = process.getErrorStream();
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            logger.info("执行命令日志02:"+errorLine);
            joinScanLog(scanPlayId,errorLine);
        }
    }


    /**
     *  添加漏洞数
     *  @param type:type
     * @param  num num
     * @param scanPlayId 扫描计划id
     */
    public void putHoleMap(String type,Integer num,String scanPlayId){
        Map<String, Integer> hashMap = new HashMap<>();
        //将漏洞数放入缓存
        Map<String, Integer> holeMap = scanHoleNum.get(scanPlayId);
        if (!ObjectUtils.isEmpty(holeMap)){
            int severityHole = ObjectUtils.isEmpty(holeMap.get(type)) ? 0 : holeMap.get(type);
            holeMap.put(type, severityHole+num);
            scanHoleNum.put(scanPlayId,holeMap);
        }else {
            hashMap.put(type,num);
            scanHoleNum.put(scanPlayId,hashMap);
        }
    }
}
