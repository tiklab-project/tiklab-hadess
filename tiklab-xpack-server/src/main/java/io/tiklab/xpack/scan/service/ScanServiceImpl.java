package io.tiklab.xpack.scan.service;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.context.AppHomeContext;
import io.tiklab.core.exception.SystemException;
import io.tiklab.xpack.common.XpackYamlDataMaService;
import io.tiklab.xpack.library.model.*;
import io.tiklab.xpack.library.service.LibraryFileService;
import io.tiklab.xpack.library.service.LibraryService;
import io.tiklab.xpack.library.service.LibraryVersionService;
import io.tiklab.xpack.scan.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
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
    ScanHoleService scanHoleService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    XpackYamlDataMaService xpackYamlDataMaService;


    public static List<ScanQueue> scanExcQueue = new ArrayList<>();

    //扫描结果
    public static Map<String , Map> scanResult = new HashMap<>();


    @Override
    public String excOneScanLibrary(ScanQueue scanQueue) {

        List<ScanQueue> scanQueues;
        if (scanExcQueue.size()>0){
            Scan scan = scanQueue.getScanList().get(0);
            scanQueues = scanExcQueue.stream().filter(a -> (scanQueue.getRepositoryId()).equals(a.getRepositoryId())).collect(Collectors.toList());
            //将需要执行的制品添加到队列中
            if (CollectionUtils.isNotEmpty(scanQueues)){
                List<Scan> scanList = scanQueues.get(0).getScanList();
                if (CollectionUtils.isNotEmpty(scanList)){
                    List<Scan> collect = scanList.stream().filter(a -> scan.getScanLibraryId().equals(a.getScanLibraryId())).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(collect)){
                        throw new SystemException(5000,"该制品正在执行");
                    }
                    scanQueues.get(0).getScanList().add(scan);
                    return "OK";
                }
                scanQueues.get(0).getScanList().add(scan);
            }
        }else {
            //执行的队列中没有数据直接添加
            scanExcQueue.add(scanQueue);
        }
        //执行扫描
        execScan(scanQueue.getRepositoryId());
        return "OK";
    }

    @Override
    public String excMultiScanLibrary(ScanQueue scanQueue) {
        List<ScanQueue> scanQueues;
        if (scanExcQueue.size()>0){
            scanQueues = scanExcQueue.stream().filter(a -> (scanQueue.getRepositoryId()).equals(a.getRepositoryId())).collect(Collectors.toList());
            //将需要执行的制品添加到队列中
            if (CollectionUtils.isNotEmpty(scanQueues)){
                ScanQueue queue = scanExcQueue.get(0);
                queue.setScanList(scanQueue.getScanList());
            }else {
                scanExcQueue.add(scanQueue);
            }
        }else {
            scanExcQueue.add(scanQueue);
        }
        //执行扫描
        execScan(scanQueue.getRepositoryId());
        return "OK";
    }


    @Override
    public List findScanQueue(String repositoryId) {
        if (CollectionUtils.isNotEmpty(scanExcQueue)){
            List<ScanQueue> scanQueues = scanExcQueue.stream().filter(a -> repositoryId.equals(a.getRepositoryId())).collect(Collectors.toList());
            return scanQueues.get(0).getScanList();
        }
        return null;
    }

    @Override
    public Map findOneScanResult(String scanLibraryId) {
        Map<String, Object> resultMap = new HashMap<>();

        Map map = scanResult.get(scanLibraryId);
        if (!ObjectUtils.isEmpty(map)){
            resultMap.put("scanLibraryId",map.get("scanLibraryId"));
            resultMap.put("state",map.get("state"));
            Object state = map.get("state");
            if (!ObjectUtils.isEmpty(state)&&!("start").equals(state)){
                scanResult.remove(scanLibraryId);
            }
        }
        return resultMap;
    }

    /**
     * 执行扫描
     * @param repositoryId 仓库id
     * @return
     */
    public void execScan(String repositoryId){

            List<ScanQueue> scanQueueList = scanExcQueue.stream().filter(a -> (repositoryId).equals(a.getRepositoryId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(scanQueueList)){
                //扫描数据
                List<Scan> scanList = scanQueueList.get(0).getScanList();
                if (CollectionUtils.isNotEmpty(scanList)){
                    Map<String, String> map = new HashMap<>();
                    Scan scan = scanList.get(0);
                    try {
                    map.put("scanLibraryId",scan.getScanLibraryId());
                    map.put("state","start");
                    scanResult.put(scan.getScanLibraryId(), map);

                    List<LibraryVersion> versionList = libraryVersionService.findLibraryVersionList(new LibraryVersionQuery().setVersion(scan.getVersion()).setLibraryId(scan.getLibraryId()));
                    if (!CollectionUtils.isEmpty(versionList)){
                        logger.info("查询版本:"+versionList);
                        LibraryVersion libraryVersion = versionList.get(0);
                        //创建扫描制品结果
                        ScanRecord scanRecord = addScanRecord(scan);
                        //查询制品文件
                        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(scan.getLibraryId()).setLibraryVersionId(libraryVersion.getId()));
                        LibraryFile libraryFile;
                        if (libraryVersion.getLibraryType().equals("maven")){
                            List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> a.getFileName().endsWith(".jar")&&!a.getFileName().endsWith("sources.jar")).collect(Collectors.toList());
                            libraryFile = libraryFiles.get(0);
                        }else {
                            libraryFile = libraryFileList.get(0);
                        }
                        String filePath = xpackYamlDataMaService.repositoryAddress() + "/" + libraryFile.getFileUrl();

                        //扫描设置
                        List<ScanSet> scanSetList = scanSetService.findAllScanSet();
                        ScanSet scanSet = scanSetList.get(0);

                        logger.info("maven扫描:"+versionList);
                        ExecutorService executorService = Executors.newCachedThreadPool();
                        executorService.submit(new Runnable(){
                            @Override
                            public void run() {
                                String openscaPath = new File(AppHomeContext.getAppHome()).getParentFile().getParent()+"/embbed/opensca-1.0.13";
                                //String openscaPath = AppHomeContext.getAppHome() + "/embbed/opensca-1.0.13";
                                try {
                                    String[] changeDirCommand = { "cd", openscaPath };
                                    // 创建 ProcessBuilder 对象并设置工作目录
                                    ProcessBuilder processBuilder = new ProcessBuilder(changeDirCommand);
                                    processBuilder.directory(new File(openscaPath));

                                    // 启动进程并等待执行完成
                                    Process process01 = processBuilder.start();
                                    int exitCode = process01.waitFor();
                                    if (exitCode == 0) {
                                        String scanFolderPath = xpackYamlDataMaService.scanFileAddress()+"/"+String.valueOf(System.currentTimeMillis()).substring(0, 9);
                                        File scanFile = new File(scanFolderPath);
                                        if (!scanFile.exists()){
                                            scanFile.mkdirs();
                                        }
                                        String scanFilePath = scanFolderPath + "/" + libraryFile.getLibrary().getName()+".json";
                                        String[] command = { "./opensca-cli","-url", scanSet.getAddress(),"-token",scanSet.getToken(),"-path",filePath,"-out",scanFilePath };
                                        logger.info("scan路径:"+scanSet.getAddress());
                                        logger.info("scan执行文件路径filePath:"+filePath);
                                        logger.info("scan文件存储路径:"+scanFilePath);
                                        processBuilder = new ProcessBuilder(command);
                                        processBuilder.directory(new File(openscaPath));
                                        logger.info("openscan执行路径:"+openscaPath);
                                        Process process = processBuilder.start();
                                        int exit = process.waitFor();
                                        logger.info("执行结果:"+exit);
                                        //执行成功解析数据
                                        if (exit==0){
                                            logger.info("扫描成功");

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
                                                createScanHole(holeList,scan.getScanLibraryId(),scanRecord,"scanLibrary");
                                            }
                                            updateScanRecord(scanRecord);
                                            logger.info("扫描完成");
                                            map.put("state","success");

                                        }else {
                                            map.put("state","fail,扫描失败");
                                        }
                                        //其他执行日志
                                        StringBuilder output = new StringBuilder();
                                        InputStream errorStream = process.getErrorStream();
                                        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                                        String errorLine;
                                        while ((errorLine = errorReader.readLine()) != null) {
                                            logger.info("扫描执行日志:"+errorLine);
                                            output.append(errorLine);
                                        }
                                    }else {
                                        map.put("state","fail,扫描失败");
                                    }
                                    scanResult.put(scan.getScanLibraryId(), map);
                                    //执行成功后删除队列中数据
                                    removeQueueScan(repositoryId,scan.getScanLibraryId());
                                } catch (Exception e) {
                                    //执行成功后删除队列中数据
                                    removeQueueScan(repositoryId,scan.getScanLibraryId());

                                    map.put("state","fail,"+e.getMessage());
                                    scanResult.put(scan.getScanLibraryId(), map);
                                    logger.info("单个扫描错误日志:"+e.getMessage());
                                }

                                //执行队列中仍有待执行的数据
                                List<ScanQueue> scanQueueList = scanExcQueue.stream().filter(a -> (repositoryId).equals(a.getRepositoryId())).collect(Collectors.toList());
                                List<Scan> scanList = scanQueueList.get(0).getScanList();
                                if (CollectionUtils.isNotEmpty(scanList)){
                                    Scan scan = scanList.get(0);
                                    logger.info("执行队列扫描:"+scan.getScanLibraryId());
                                    execScan(repositoryId);
                                }
                            }
                        });
                    }
                    map.put("state","fail,文件查询失败");
                    scanResult.put(scan.getScanLibraryId(), map);
                }catch (Exception e){
                    //执行成功后删除队列中数据
                   removeQueueScan(repositoryId,scan.getScanLibraryId());
                   map.put("state","fail,扫描失败");
                   throw new SystemException(e.getMessage());
                }
            }
        }
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
     * 创建扫描结果
     * @param scan 扫描制品id
     * @return
     */
    public ScanRecord addScanRecord(Scan scan){
        ScanRecord scanRecord = new ScanRecord();
        scanRecord.setLibraryVersion(scan.getVersion());

        ScanLibrary scanLibrary = new ScanLibrary();
        scanLibrary.setId(scan.getScanLibraryId());
        scanRecord.setScanLibrary(scanLibrary);

        Library library = new Library();
        library.setId(scan.getLibraryId());
        scanRecord.setLibrary(library);
        String scanRecordId = scanRecordService.createScanRecord(scanRecord);
        scanRecord.setId(scanRecordId);
        return scanRecord;
    }


    /**
     * 添加扫描依赖
     * @param childrenList
     * @param scanRecord scanRecord
     * @param  scanRelyId 父级id
     * @param   oneRelyId 第一层依赖id
     * @return
     */
    public void createScanRely(List<JSONObject> childrenList,ScanRecord scanRecord, String scanRelyId,String oneRelyId){

        String scanLibraryId = scanRecord.getScanLibrary().getId();
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
                    createScanHole(holeList,relyId,scanRecord,"relyLibrary");
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
     * 添加扫描漏洞
     * @param holeList 漏洞list
     * @param  type  漏洞类型 relyLibrary、scanLibrary
     * @param  scanRecord scanRecord
     * @return
     */
    public void createScanHole(List<JSONObject> holeList,String scanRelyId,ScanRecord scanRecord,String type){
        for (JSONObject hole:holeList){
            ScanHole scanHole = new ScanHole();

            scanHole.setScanRecordId(scanRecord.getId());
            scanHole.setScanLibraryId(scanRecord.getScanLibrary().getId());
            scanHole.setHoleType(type);
            //漏洞等级
            Integer level = Integer.valueOf(hole.get("security_level_id").toString());
            scanHole.setHoleLevel(level);
            scanHole.setLibraryId(scanRelyId);
            scanHole.setHoleDesc(hole.get("description").toString());
            if (!ObjectUtils.isEmpty(hole.get("suggestion"))){
                scanHole.setRepairSuggest(hole.get("suggestion").toString());
            }
            if (!ObjectUtils.isEmpty(hole.get("release_date"))){
                scanHole.setReleaseTime(hole.get("release_date").toString());
            }
            scanHole.setHoleName(hole.get("name").toString());

            if (!ObjectUtils.isEmpty(hole.get("cwe_id"))){
                scanHole.setHoleCwe(hole.get("cwe_id").toString());
            }
            if (!ObjectUtils.isEmpty(hole.get("cve_id"))){
                scanHole.setHoleCve(hole.get("cve_id").toString());
            }
            if (!ObjectUtils.isEmpty(hole.get("cnvd_id"))){
                scanHole.setHoleCnvd(hole.get("cnvd_id").toString());
            }
            if (!ObjectUtils.isEmpty(hole.get("cnnvd_id"))){
                scanHole.setHoleCnnvd(hole.get("cnnvd_id").toString());
            }
            if (!ObjectUtils.isEmpty(hole.get("id"))){
                scanHole.setHoleXmirror(hole.get("id").toString());
            }
            scanHoleService.createScanHole(scanHole);
        }
    }

    /**
     * 更新扫描制品结果
     * @param  scanRecord scanRecord
     * @return
     */
    public void updateScanRecord(ScanRecord scanRecord){

        List<ScanHole> scanHoleList = scanHoleService.findScanHoleList(new ScanHoleQuery().setScanRecordId(scanRecord.getId()));
        if (CollectionUtils.isNotEmpty(scanHoleList)){
            List<ScanHole> severityHole = scanHoleList.stream().filter(a -> a.getHoleLevel() == 1).collect(Collectors.toList());
            List<ScanHole> highHole = scanHoleList.stream().filter(a -> a.getHoleLevel() == 2).collect(Collectors.toList());
            List<ScanHole> middleHole = scanHoleList.stream().filter(a -> a.getHoleLevel() == 3).collect(Collectors.toList());
            List<ScanHole> lowHole = scanHoleList.stream().filter(a -> a.getHoleLevel() == 4).collect(Collectors.toList());

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
    }




    /**
     * 创建扫描结果
     * @param scanLibraryId 扫描制品id
     * @param libraryId 制品id
     * @param   version 版本
     * @return
     */
    public ScanRecord addScanRecord(String scanLibraryId,String libraryId,String version){
        ScanRecord scanRecord = new ScanRecord();
        scanRecord.setLibraryVersion(version);

        ScanLibrary scanLibrary = new ScanLibrary();
        scanLibrary.setId(scanLibraryId);
        scanRecord.setScanLibrary(scanLibrary);

        Library library = new Library();
        library.setId(libraryId);
        scanRecord.setLibrary(library);
        String scanRecordId = scanRecordService.createScanRecord(scanRecord);
        scanRecord.setId(scanRecordId);
        return scanRecord;

    }

    /**
     * 移除队列中的扫描
     * @param repositoryId repositoryId
     * @return
     */
    public void removeQueueScan(String repositoryId,String scanLibraryId){
        List<ScanQueue> scanQueueList = scanExcQueue.stream().filter(a -> (repositoryId).equals(a.getRepositoryId())).collect(Collectors.toList());
        List<Scan> scanList = scanQueueList.get(0).getScanList();
        List<Scan> collect = scanList.stream().filter(a -> !(scanLibraryId).equals(a.getScanLibraryId())).collect(Collectors.toList());
        scanQueueList.get(0).setScanList(collect);
    }
}
