package io.tiklab.hadess.upload.service;

import io.tiklab.core.Result;
import io.tiklab.core.exception.SystemException;
import io.tiklab.hadess.common.RepositoryUtil;
import io.tiklab.hadess.common.UserCheckService;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.library.model.Library;
import io.tiklab.hadess.library.model.LibraryFile;
import io.tiklab.hadess.library.model.LibraryVersion;
import io.tiklab.hadess.library.service.LibraryFileService;
import io.tiklab.hadess.library.service.LibraryService;
import io.tiklab.hadess.library.service.LibraryVersionService;
import io.tiklab.hadess.repository.model.Repository;
import io.tiklab.hadess.repository.service.RepositoryService;
import io.tiklab.hadess.upload.common.UploadTool;
import io.tiklab.hadess.upload.model.LibraryHelmClient;
import io.tiklab.hadess.upload.model.LibraryHelmServer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HelmUploadServiceImpl implements HelmUploadService {
    private static Logger logger = LoggerFactory.getLogger(HelmUploadServiceImpl.class);

    @Autowired
    UserCheckService userCheckService;

    @Autowired
    RepositoryService repositoryService;


    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    LibraryFileService libraryFileService;

    @Override
    public Result<byte[]> connectWarehouse(String authorization,String pathData) {

        Result<String> result = userCheck(authorization);
        if (result.getCode()==0){
            String repositoryName = StringUtils.substringBefore(pathData, "/");

            Repository repository = repositoryService.findRepositoryByName(repositoryName);
            if (ObjectUtils.isEmpty(repository)){
                logger.info("执行helm repo add 失败："+repositoryName +"仓库不存在");
                return Result.error(404,"Repository not found");
            }

            String filePath = yamlDataMaService.repositoryAddress() + "/" + repository.getId()+"/index.yaml";

            //String bytes = readFileData(filePath);
            byte[] bytes = UploadTool.readFileDataByte(filePath);

            return Result.ok(bytes);
        }
        return Result.error(result.getCode(),result.getMsg());
    }


    @Override
    public Result<String> uploadData(LibraryHelmClient helmClient) {
        InputStream inputStream = helmClient.getInputStream();
        //查询制品库
        Repository repository = repositoryService.findRepositoryByName(helmClient.getRepositoryName());
        if (ObjectUtils.isEmpty(repository)){
            logger.info(helmClient.getRepositoryName()+" 仓库不存在");
            return Result.error(404,helmClient.getRepositoryName()+" 仓库不存在");
        }

        //创建helm临时文件
        LibraryHelmServer scratchFile = createScratchFile(inputStream, helmClient.getFileName());

        // 写入正式文件
        writeOffData(scratchFile,repository.getId(),helmClient.getFileName());


        //更新index.yaml索引文件
        updateIndexFile(scratchFile,repository.getId(),helmClient.getFileName());

        //创建helm制品信息
        createLibraryInfo(helmClient,repository,scratchFile);

        //删除临时文件夹
        String folder = StringUtils.substringBeforeLast(scratchFile.getScratchFilePath(), "/" + helmClient.getFileName());
        try {
           FileUtils.deleteDirectory(new File(folder));
        } catch (IOException e) {
            logger.info("删除helm临时文件夹："+folder+" 失败");
            throw new RuntimeException(e);
        }
        return Result.ok("Chart uploaded successfully");
    }

    @Override
    public Result<byte[]> pullData(String pathData) {
        String repositoryName = StringUtils.substringBefore(pathData, "/");
        Repository repository = repositoryService.findRepositoryByName(repositoryName);
        if (ObjectUtils.isEmpty(repository)){
            logger.info(repositoryName+" 仓库不存在");
            return Result.error(404,repositoryName+" 仓库不存在");
        }

        String s = StringUtils.substringAfter(pathData, "/");
        String filePath = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + s;

        byte[] bytes = UploadTool.readFileDataByte(filePath);
        return Result.ok(bytes);
    }

    @Override
    public Result<String> userCheck(String authorization) {
        //认证信息为空
        if (StringUtils.isEmpty(authorization)){
            return Result.error(401,"用户信息不存在");
        }
        try {
            //解析用户信息
            String basic = authorization.replace("Basic", "").trim();
            byte[] decode = Base64.getDecoder().decode(basic);
            String userData = new String(decode, "UTF-8");
            String[] split = userData.split(":");
            String userName = split[0];

            userCheckService.basicsUserCheck(userData);
            return Result.ok(userName);
        }catch (Exception e){
            return Result.error(401,e.getMessage());
        }
    }

    /**
     * 创建临时文件
     * @param inputStream 客户端上传的文件流
     * @param fileName 客户端上传的文件名称
     */
    public LibraryHelmServer createScratchFile(InputStream inputStream, String fileName) {
        String s = StringUtils.substringBeforeLast(fileName, ".");

        //helm制品临时库文件夹
        String folderPath = yamlDataMaService.repositoryAddress() + "/helmTem/"+s;
        try {
            File folder = new File(folderPath);
            if (!folder.exists()){
                folder.mkdirs();
            }

            String filePath = folderPath + "/" + fileName;
            File file = new File(filePath);
            if (!file.exists()){
                file.createNewFile();
            }

            //用字节流写入文件
            FileOutputStream outputStream = new FileOutputStream(filePath);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();

            //解压
            RepositoryUtil.decompression(filePath,folderPath);

            //获取临时文件夹下面的解压后的文件夹
            File[] subDirectories = folder.listFiles(File::isDirectory);
            if (subDirectories==null){
                logger.info("没有找到解压helm的文件");
                throw new SystemException(500,"没有找到解压helm的文件");
            }
            String name = subDirectories[0].getName();

            //读取Chart.yaml信息
            String chartPath = folderPath + "/" + name + "/Chart.yaml";
            Map<String, Object> chartData = UploadTool.readYamlFile(chartPath);

            //解析chart.yaml数据
            LibraryHelmServer helmChart = analysisChartData(chartData,filePath);

            helmChart.setScratchFilePath(filePath);
            return helmChart;
        } catch (IOException e) {
            logger.info("创建临时文件 "+fileName+" 失败："+e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

    /**
     * 写入正式数据
     * @param helmChart helmChart文件信息
     * @param repositoryId 仓库id
     * @param fileName 客户端上传的文件名称
     */
    public void writeOffData(LibraryHelmServer helmChart, String repositoryId, String fileName){
        //上传文件的后缀
        String fileSuffix = StringUtils.substringAfterLast(fileName, ".");

        //chart.yaml文件路径
        String chartFileName = helmChart.getName() + "-" + helmChart.getVersion()+".yaml";
        try {
            //创建文件夹
            String helmFolderPath = yamlDataMaService.repositoryAddress() + "/"+repositoryId+"/"+helmChart.getName();
            File helmFolder = new File(helmFolderPath);
            if (!helmFolder.exists()){
                helmFolder.mkdirs();
            }

            //创建文件
            String chartFilePath = helmFolder+"/"+chartFileName;
            File chartFile = new File(chartFilePath);
            if (!chartFile.exists()){
                chartFile.createNewFile();
            }

            UploadTool.writeYamlFile(helmChart.getData(),chartFilePath);
           /* FileWriter writer = new FileWriter(chartFilePath);
            writer.write(helmChart.getData());
            writer.close();*/

            //移动helmTag包文件
            String packFileName = helmChart.getName() + "-" + helmChart.getVersion()+"."+fileSuffix;
            String packFilePath = helmFolderPath+"/"+packFileName;
            File file = new File(helmChart.getScratchFilePath());
            file.renameTo(new File(packFilePath));

        } catch (IOException e) {
            logger.info("写入正式文件 "+fileName+" 失败："+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新索引文件
     * @param helmChart helmChart文件信息
     */
    public void updateIndexFile(LibraryHelmServer helmChart, String repositoryId, String fileName){
        //上传文件的后缀
        String fileSuffix = StringUtils.substringAfterLast(fileName, ".");

        String indexFilePath = yamlDataMaService.repositoryAddress() + "/" +repositoryId+"/index.yaml" ;
        //索引文件不存在初始化索引文件
        RepositoryUtil.initHelmIndexFile(indexFilePath);

        Map<String, Object> yamlFile = UploadTool.readYamlFile(indexFilePath);
        Map<String, Object> newDatabaseMap = new LinkedHashMap<>(yamlFile);
        LinkedHashMap<String, List<Map>> entries = (LinkedHashMap<String, List<Map>>) newDatabaseMap.get("entries");

        //添加文件存储路径
        Map<String, Object> chartData = helmChart.getData();
        Object urls = chartData.get("urls");
        List<String> list = new ArrayList<>();
        if (!ObjectUtils.isEmpty(urls)){
            list = (ArrayList<String>) urls;
        }
        String path =helmChart.getName()+"/"+ helmChart.getName() + "-" + helmChart.getVersion()+"."+fileSuffix;
        list.add(path);
        chartData.put("urls",list);

        List<Map> arrayList = new ArrayList();
        //首次上传文件到仓库,entries为空
        if (ObjectUtils.isEmpty(entries)){
            arrayList.add(chartData);
            entries.put(helmChart.getName(),arrayList);
        }else {
            List<String> collect = entries.keySet().stream().filter(a -> a.equals(helmChart.getName())).collect(Collectors.toList());

            //为空，首次上传该helm制品
            if (CollectionUtils.isEmpty(collect)){
                arrayList.add(chartData);
                entries.put(helmChart.getName(),arrayList);
            }else {
               //更新同一版本或者上传不同版本
                List<Map> packList = entries.get(collect.get(0));

                for (int i = 0; i < packList.size(); i++) {
                    Map map = packList.get(i);
                    //上传helm制品的索引文件版本和仓库中的索引文件版本相同   修改该版本信息
                    if (map.get("version").equals(helmChart.getVersion())){
                        packList.set(i, chartData);
                    }else {
                        //不同版本，直接添加
                        packList.add(chartData);
                    }
                }
            }
        }
        newDatabaseMap.put("entries", entries);
        UploadTool.writeYamlFile(newDatabaseMap,indexFilePath);
    }


    /**
     *  解析Chart数据
     *  @param chartData     chartData
     */
    public LibraryHelmServer analysisChartData(Map<String, Object> chartData, String filePath){
        //获取文件的sha256
        String sha256 = RepositoryUtil.getSHA256ByPath(filePath);

        Map<String, Object> newDatabaseMap = new LinkedHashMap<>(chartData);

        //在description 键值对上下添加数据
        Map<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : newDatabaseMap.entrySet()) {
            if (entry.getKey().equals("description")) {
                String now = Instant.now().toString();
                map.put("created",now);
            }
            map.put(entry.getKey(),newDatabaseMap.get(entry.getKey()));
            if (entry.getKey().equals("description")) {
                map.put("digest",sha256);
            }
        }
        LibraryHelmServer helmChart = new LibraryHelmServer();
        helmChart.setData(map);
        helmChart.setDigest(sha256);
        helmChart.setName(chartData.get("name").toString());
        helmChart.setVersion(chartData.get("version").toString());
        return  helmChart;
    }


    /**
     *  读取文件信息
     *  @param filePath     文件路径
     */
    public String readFileData(String filePath){
        try {
           File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            StringBuilder result = new StringBuilder();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = null;

            while ((lineTxt = bfr.readLine()) != null) {
                result.append(lineTxt).append('\n');
            }
            String toString = result.toString();
            String trim = toString.trim();
            return trim;
        }catch (IOException e){
            logger.info("读取文件："+filePath+" 失败");
            throw new SystemException(e.getMessage());
        }
    }

    /**
     *  创建制品信息
     *  @param helmClient     helmClient
     */
    public  void createLibraryInfo(LibraryHelmClient helmClient, Repository repository, LibraryHelmServer helmChart) {
        String libraryPath= yamlDataMaService.repositoryAddress() + "/" + repository.getId()+"/"+helmChart.getName();

        //创建制品
        Library library = libraryService.createLibraryData(helmChart.getName(),"helm",repository);

        //Chart 信息文件
        String infoName = helmChart.getName() + "-" + helmChart.getVersion()+".yaml";
        String InfoPath = libraryPath  +"/" + infoName;
        File infoFile = new File(InfoPath);
        long infoLength = infoFile.length();
        //chart
        String chartName = helmChart.getName() + "-" + helmChart.getVersion()+".tgz";
        String chartPath = libraryPath  +"/" +chartName;
        File chart = new File(chartPath);
        long chartLength = chart.length();
        long allLength = infoLength + chartLength;


        //创建版本
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setPusher(helmClient.getUserName());
        libraryVersion.setLibrary(library);
        libraryVersion.setVersion(helmChart.getVersion());
        libraryVersion.setRepository(repository);
        libraryVersion.setHash(helmChart.getDigest());
        libraryVersion.setSize(allLength);
        libraryVersion.setLibraryType("helm");
        String libraryVersionId = libraryVersionService.redactLibraryVersion(libraryVersion);



       //创建制品
        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setRepository(repository);
        libraryFile.setLibrary(library);

        //创建 chart 信息文件
        libraryFile.setFileName(infoName);
        libraryFile.setFileUrl(repository.getId()+"/"+helmChart.getName()+"/"+infoName);
        libraryFile.setRelativePath(helmChart.getName()+"/"+infoName);
        //Chart信息文件
        libraryFile.setFileSize(RepositoryUtil.formatSize(infoLength));
        libraryFile.setSize(infoLength);
        libraryFileService.redactLibraryFile(libraryFile,libraryVersionId);


        //创建chart
        libraryFile.setFileName(chartName);
        libraryFile.setFileUrl(repository.getId()+"/"+helmChart.getName()+"/"+chartName);
        libraryFile.setRelativePath(helmChart.getName()+"/"+chartName);
        //Chart信息文件
        libraryFile.setFileSize(RepositoryUtil.formatSize(chartLength));
        libraryFile.setSize(chartLength);
        libraryFileService.redactLibraryFile(libraryFile,libraryVersionId);
    }
}
