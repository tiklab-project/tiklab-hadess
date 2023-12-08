package io.thoughtware.hadess.scan.service;

import com.alibaba.fastjson.JSONObject;
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
import org.apache.commons.collections.CollectionUtils;
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



    @Override
    public String execScan(String scanPlayId) {
        List<ScanLibrary> scanLibraryList = scanLibraryService.findScanLibraryList(new ScanLibraryQuery().setScanPlayId(scanPlayId));
        if (CollectionUtils.isEmpty(scanLibraryList)){
            throw  new SystemException(600,"制品文件为空");
        }

        //添加开始的扫描
        addScanResult("start",scanPlayId,scanLibraryList);

        updateScanPlay(scanPlayId);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable(){
            @Override
            public void run() {
                String math = getRandom();
                for (ScanLibrary scanLibrary:scanLibraryList){
                    long starTime = System.currentTimeMillis();
                    logger.info("扫描制品:"+scanLibrary.getLibrary().getName());
                    List<LibraryVersion> versionList = libraryVersionService.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(scanLibrary.getLibrary().getId()));
                    if (CollectionUtils.isNotEmpty(versionList)){
                        List<LibraryVersion> versions = versionList.stream().sorted(Comparator.comparing(LibraryVersion::getCreateTime).reversed()).collect(Collectors.toList());
                        LibraryVersion libraryVersion = versions.get(0);
                        //创建扫描制品结果
                        ScanRecord scanRecord = addScanRecord(scanLibrary,libraryVersion.getVersion(),scanPlayId,math);

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
                        try {
                            String openscaPath = new File(AppHomeContext.getAppHome()).getParentFile().getParent()+"/embbed/opensca-1.0.13";
                            //String openscaPath = AppHomeContext.getAppHome() + "/embbed/opensca-1.0.13";
                            logger.info("openscan执行路径:"+openscaPath);
                            String[] changeDirCommand = { "cd", openscaPath };
                            // 创建 ProcessBuilder 对象并设置工作目录
                            ProcessBuilder processBuilder = new ProcessBuilder(changeDirCommand);
                            processBuilder.directory(new File(openscaPath));

                            // 启动进程并等待执行完成
                            Process process = processBuilder.start();
                            int exitCode = process.waitFor();
                            if (exitCode == 0) {
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
                                int exit = scanProcess.waitFor();
                                logger.info("执行结果:"+exit);
                                //执行成功解析数据
                                if (exit==0){
                                    logger.info("扫描成功");
                                    //读取扫描的文件
                                    String resultData = readScanFile(scanFilePath);
                                    JSONObject allData =(JSONObject) JSONObject.parse(resultData);
                                    Object children = allData.get("children");
                                    List<JSONObject> childrenList = (List<JSONObject>) children;
                                    //创建扫描依赖和漏洞
                                    createScanRely( childrenList,scanRecord,null,null);

                                    //主制品漏洞
                                    Object vulnerabilities = allData.get("vulnerabilities");
                                    if (!ObjectUtils.isEmpty(vulnerabilities)){
                                        List<JSONObject> holeList = (List<JSONObject>) vulnerabilities;
                                        createScanResult(holeList,scanLibrary.getLibrary().getId(),scanRecord,"scanLibrary");
                                    }
                                    long endTime = System.currentTimeMillis();
                                    ScanRecord record = updateScanRecord(scanRecord,endTime-starTime);
                                    scanLibrary.setScanRecord(record);
                                    scanLibrary.setScanResult("success");
                                    logger.info("扫描成功结束");
                                }else {
                                    scanLibrary.setScanResult("false");
                                    logger.info("执行结果失败"+exit);
                                }
                            }
                            addScanResult("start",scanPlayId,scanLibraryList);
                        }catch (Exception e){
                            logger.info(scanLibrary.getLibrary().getName()+"制品扫描失败:"+e.getMessage());
                            scanLibrary.setScanResult("false");
                            addScanResult("end",scanPlayId,scanLibraryList);
                            throw new SystemException(e.getMessage());
                        }
                    }
                }
                addScanResult("end",scanPlayId,scanLibraryList);
            }
        });
        return "OK";
    }

    @Override
    public ScanQueue findExecResult(String scanPlayId) {
        ScanQueue queue = scanLibraryResult.get(scanPlayId);
        return queue;
    }

    /**
     * 创建扫描结果
     * @param scanLibrary 扫描的制品
     * @return
     */
    public ScanRecord addScanRecord(ScanLibrary scanLibrary,String version,String scanPlayId,String math){
        ScanRecord scanRecord = new ScanRecord();
        scanRecord.setScanGroup(math);
        scanRecord.setScanPlayId(scanPlayId);
        scanRecord.setLibraryVersion(version);
        scanRecord.setScanLibraryId(scanLibrary.getId());
        scanRecord.setLibrary(scanLibrary.getLibrary());
        String scanRecordId = scanRecordService.createScanRecord(scanRecord);
        scanRecord.setId(scanRecordId);
        return scanRecord;
    }


    /**
     * 更新扫描制品结果
     * @param  scanRecord scanRecord
     */
    public ScanRecord updateScanRecord(ScanRecord scanRecord,long interval){
        String timeBad = RepositoryUtil.timeBad(interval);
        scanRecord.setScanTimeLong(timeBad);
        List<ScanResult> scanResultList = scanResultService.findScanResultList(new ScanResultQuery().setScanRecordId(scanRecord.getId()));
        if (CollectionUtils.isNotEmpty(scanResultList)){
            List<ScanResult> severityHole = scanResultList.stream().filter(a -> a.getHoleLevel() == 1).collect(Collectors.toList());
            List<ScanResult> highHole = scanResultList.stream().filter(a -> a.getHoleLevel() == 2).collect(Collectors.toList());
            List<ScanResult> middleHole = scanResultList.stream().filter(a -> a.getHoleLevel() == 3).collect(Collectors.toList());
            List<ScanResult> lowHole = scanResultList.stream().filter(a -> a.getHoleLevel() == 4).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(severityHole)){
                scanRecord.setHoleSeverity(severityHole.size());
            }
            if (CollectionUtils.isNotEmpty(severityHole)){
                scanRecord.setHoleHigh(highHole.size());
            }
            if (CollectionUtils.isNotEmpty(severityHole)){
                scanRecord.setHoleMiddle(middleHole.size());
            }
            if (CollectionUtils.isNotEmpty(severityHole)){
                scanRecord.setHoleLow(lowHole.size());
            }
            scanRecordService.updateScanRecord(scanRecord);
        }
        scanRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return scanRecord;
    }


    /**
     * 扫描方式
     * @param libraryFile libraryFile
     * @return
     */
    public String[] scanType(LibraryFile libraryFile,String scanFilePath){
        //本地漏洞库
        String leakCvePath = new File(AppHomeContext.getAppHome()).getParentFile().getParent()+"/embbed/cve.json";
        //String leakCvePath = AppHomeContext.getAppHome() + "/embbed/cve.json";

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


}
