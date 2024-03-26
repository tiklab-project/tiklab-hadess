package io.thoughtware.hadess.upload.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.thoughtware.core.Result;
import io.thoughtware.hadess.library.model.*;
import io.thoughtware.eam.passport.user.service.UserPassportService;
import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.library.service.LibraryFileService;
import io.thoughtware.hadess.library.service.LibraryService;
import io.thoughtware.hadess.library.service.LibraryVersionService;
import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.hadess.repository.service.RepositoryService;
import io.thoughtware.hadess.common.UserCheckService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DockerUploadServiceImpl implements DockerUploadService {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    UserPassportService userPassportService;

    @Autowired
    XpackYamlDataMaService yamlDataMaService;
    
    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    LibraryVersionService versionService;

    @Autowired
    UserCheckService userCheckService;

    public static Map<String , String> blobsDataSize = new HashMap<>();

    public static Map<String , InputStream> blobsData = new HashMap<>();
    @Override
    public int userCheck(String userData) {
        try {
            userCheckService.dockerUserCheck(userData);
            return 200;
        }catch (Exception e){
            return 401;
        }
    }

    @Override
    public Result v2Sha256Check(String repositoryPath) throws Exception {
        String sha256 = repositoryPath.substring(repositoryPath.indexOf("sha256:") );

        String repositoryAddress = yamlDataMaService.repositoryAddress();

        //仓库名称
        String repositoryName = repositoryPath.substring(0,repositoryPath.indexOf("/"));
        Repository repository = repositoryService.findRepository(repositoryName,"docker");

        if (ObjectUtils.isEmpty(repository)){
            return Result.error(404,"can not find repository,制品库"+repositoryName+"没有找到，请输入正确的制品库");
        }
        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setFileName(sha256).setRepositoryId(repository.getId()));
        if (CollectionUtils.isEmpty(libraryFileList)){
            return Result.error(404,"制品不存在:"+sha256);
        }
        String fileUrl = libraryFileList.get(0).getFileUrl();
        //sha256的文件位置
        String AllFilePath = repositoryAddress + "/" + fileUrl;
        File blobsFile = new File(AllFilePath);
        if (blobsFile.exists()){
            //创建文件
            long fileLength = blobsFile.length();
            return Result.ok(String.valueOf(fileLength));
        }
        return Result.error(400,"文件不存在："+sha256);
    }

    @Override
    public Result uploadData(InputStream inputStream, String repositoryPath) throws IOException {

        String fileName = repositoryPath.substring(repositoryPath.lastIndexOf("/") + 1);

        blobsData.put(fileName,inputStream);

        //制品库名称
        String repositoryName = repositoryPath.substring(0, repositoryPath.indexOf("/"));
        Repository repository = repositoryService.findRepository(repositoryName,"docker");
        if (ObjectUtils.isEmpty(repository)){
            return Result.error(404,"can not find repository,制品库"+repositoryName+"没有找到，请输入正确的制品库");
        }
        //制品名称
        String libraryName =getLibraryName(repositoryPath,"/blobs");

        //创建制品
        Library library = libraryService.createLibraryData(libraryName, "docker", repository);
        String repositoryAddress = yamlDataMaService.repositoryAddress();

        //blobs的文件夹(数据)
        String blobsFolder = repositoryAddress + "/" + repository.getId() + "/" + libraryName+"/"+"blobs";
        File blobsFolderFile = new File(blobsFolder);
        if (!blobsFolderFile.exists()){
            blobsFolderFile.mkdirs();
        }

        String blobsDoc = blobsFolder + "/" + fileName;
        File blobsFile = new File(blobsDoc);
        if (!blobsFile.exists()){
            blobsFile.createNewFile();
        }

        //用字节流写入文件
        FileOutputStream outputStream = new FileOutputStream(blobsDoc);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();

        //创建文件
        long fileLength = blobsFile.length();
        String size = RepositoryUtil.formatSize(fileLength);
        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setLibrary(library);
        libraryFile.setFileName(fileName);
        libraryFile.setFileSize(size);
        libraryFile.setSize(fileLength);
        libraryFile.setRepository(repository);

        String fileUrl = repository.getId() + "/" + libraryName + "/" + "blobs/" + fileName;
        libraryFile.setFileUrl(fileUrl);
        libraryFile.setRelativePath("blobs/" + fileName);
        libraryFileService.libraryFileSplice(libraryFile,null);


        blobsDataSize.put(fileName,String.valueOf(fileLength));
        return Result.ok();
    }

    @Override
    public Result createSession(String repositoryPath) {
        String repositoryName = StringUtils.substringBefore(repositoryPath, "/");
        Repository repository = repositoryService.findRepository(repositoryName,"docker");
        if (ObjectUtils.isEmpty(repository)){
            return Result.error(404,"can not find repository，制品库"+repositoryName+"没有找到，请输入正确的制品库");
        }
        return Result.ok();
    }

    @Override
    public String createFile(String digest,String repositoryPath) throws IOException {
        String fileName = repositoryPath.substring(repositoryPath.lastIndexOf("/") + 1);


        //制品名称
        String libraryName =getLibraryName(repositoryPath,"/blobs/");

        //制品库名称
        String repositoryName = repositoryPath.substring(0, repositoryPath.indexOf("/"));
        Repository repository = repositoryService.findRepository(repositoryName,"docker");


        String repositoryAddress = yamlDataMaService.repositoryAddress();
        String blobsFolder = repositoryAddress + "/" + repository.getId() + "/" + libraryName+"/"+"blobs/";

        String oldFileName = blobsFolder + fileName;
        String newFileName = blobsFolder + digest;
        File oldFile = new File(oldFileName);
        File newFile = new File(newFileName);

        // 使用 renameTo() 方法将文件重命名
        boolean success = oldFile.renameTo(newFile);

        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setRepositoryId(repository.getId()).setFileName(fileName));
        if (!CollectionUtils.isEmpty(libraryFileList)){
            LibraryFile libraryFile = libraryFileList.get(0);
            libraryFile.setFileName(digest);

            String replace = libraryFile.getFileUrl().replace(fileName, digest);
            libraryFile.setFileUrl(replace);

            String relative = libraryFile.getRelativePath().replace(fileName, digest);
            libraryFile.setRelativePath(relative);

            libraryFileService.updateLibraryFile(libraryFile);
        }
       return blobsDataSize.get(fileName);
    }

    @Override
    public String createTag(InputStream inputStream, String repositoryPath,String authorization) throws IOException, NoSuchAlgorithmException {


        String version = repositoryPath.substring(repositoryPath.lastIndexOf("/") + 1);

        //制品库名称
        String repositoryName = repositoryPath.substring(0, repositoryPath.indexOf("/"));


        //制品名称
        String libraryName =getLibraryName(repositoryPath,"/manifests");

        Repository repository = repositoryService.findRepository(repositoryName,"docker");

        String repositoryAddress = yamlDataMaService.repositoryAddress();

        //tags 文件夹路径
        String tagFolder = repositoryAddress + "/" + repository.getId() + "/" + libraryName + "/tags";
        //写入数据
        Long aLong = writeFileData(inputStream, tagFolder, version);

        File TagFile = new File(tagFolder + "/" + version);
        String readFile = readFile(TagFile);

        String sha256 = RepositoryUtil.generateSHA256(readFile);
        String fileName = "sha256:" + sha256;
        String manFolder = repositoryAddress + "/" + repository.getId() + "/" + libraryName + "/manifests";


        InputStream  data  =  new   ByteArrayInputStream(readFile.getBytes());
        writeFileData(data,manFolder,fileName);

        //创建版本
        List<Library> libraryList = libraryService.likeLibraryListNo(new LibraryQuery().setName(libraryName)
                .setRepositoryId(repository.getId()));

        if (!CollectionUtils.isEmpty(libraryList)){
            Library library = libraryList.get(0);

            LibraryVersion libraryVersion = new LibraryVersion();
            libraryVersion.setLibrary(library);
            libraryVersion.setRepository(repository);
            libraryVersion.setVersion(version);
            libraryVersion.setLibraryType("docker");
            libraryVersion.setSize(aLong);

            //用户信息
            String basic = authorization.replace("Basic", "").trim();
            byte[] decode = Base64.getDecoder().decode(basic);
            //用户信息
            String userData = new String(decode, "UTF-8");
            String[] split = userData.split(":");
            libraryVersion.setPusher(split[0]);
            String libraryVersionId = versionService.createLibraryVersionSplice(libraryVersion,fileName);

            //创建描述文件
            String fileUrl = repository.getId() + "/" + libraryName + "/manifests/" + fileName;
            long fileLength = fileUrl.length();
            String size = RepositoryUtil.formatSize(fileLength);

            LibraryFile libraryFile = new LibraryFile();
            libraryFile.setLibrary(library);
            libraryFile.setFileName(fileName);
            libraryFile.setFileSize(size);
            libraryFile.setSize(fileLength);
            libraryFile.setRepository(repository);

            libraryFile.setFileUrl(fileUrl);
            libraryFile.setRelativePath("tag/" + fileName);
            libraryFileService.libraryFileSplice(libraryFile,libraryVersionId);
        }

        return readFile ;
    }

    @Override
    public Map<String, String> pullManifests(String repositoryPath) {

        //版本
        String version = repositoryPath.substring(repositoryPath.lastIndexOf("/") + 1);

        //仓库名称
        String repositoryName = repositoryPath.substring(0,repositoryPath.indexOf("/"));
        //制品名称
        String libraryName = getLibraryName(repositoryPath,"/manifests");

        Repository repository = repositoryService.findRepository(repositoryName,"docker");
        if (ObjectUtils.isEmpty(repository)){
            return putMapData("400","制品库不存在");
        }

        List<LibraryFile> libraryFile = libraryFileService.findFileByReAndLibraryAndVer(repository.getId(), libraryName, version);
        if (CollectionUtils.isEmpty(libraryFile)){
             return putMapData("400","文件不存在");
        }
        //证明数据manifests
        List<LibraryFile> libraryFiles = libraryFile.stream().filter(a -> a.getFileUrl().contains("/manifests/")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(libraryFiles)){
            return putMapData("400","文件不存在");
        }

        String fileName = libraryFiles.get(0).getFileName();
        return putMapData("200",fileName);
    }

    @Override
    public Map<String, String> readMirroringData(String repositoryPath) throws IOException {


        //仓库名称
        String repositoryName = repositoryPath.substring(0,repositoryPath.indexOf("/"));
        Repository repository = repositoryService.findRepository(repositoryName,"docker");

        String substring =repositoryPath.substring(repositoryPath.indexOf("/"));

        String repositoryAddress = yamlDataMaService.repositoryAddress();
        String manifestsPath = repositoryAddress + "/" + repository.getId() + substring;
        String readFile = readFile(new File(manifestsPath));

        Map<String, String> map = putMapData("200", readFile);
        long length = new File(manifestsPath).length();
        map.put("size",String.valueOf(length));
        map.put("url",manifestsPath);
        return map;
    }


    // 创建错误信息对象
    private static Map<String, Object> createErrorObject(String message, String digest) {
        Map<String, Object> errorObject = new HashMap<>();
        errorObject.put("code", "BLOB_UNKNOWN");
        errorObject.put("message", message);

        Map<String, String> detail = new HashMap<>();
        detail.put("digest", digest);

        errorObject.put("detail", detail);
        return errorObject;
    }

    public String readFile( File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(inputStream.available());
        BufferedInputStream in = new BufferedInputStream(inputStream);
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
     * 写入文件数据
     * @param inputStream 文件流
     */
    public Long writeFileData(InputStream inputStream,String folderPath,String fileName) throws IOException {
        File TagFolderFile = new File(folderPath);
        if (!TagFolderFile.exists()){
            TagFolderFile.mkdirs();
        }

        //tags 文件
        String tagFile = folderPath+"/"+fileName;
        File TagFile = new File(tagFile);
        if (!TagFile.exists()){
            TagFile.createNewFile();
        }

        //用字节流写入文件
        FileOutputStream outputStream = new FileOutputStream(tagFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();

        return TagFile.length();
    }


    /**
     * 输入结果数据
     * @param code code
     * @param  data 数据
     */
    public  Map<String, String> putMapData(String code,String data){
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("code",code);
        resultMap.put("data",data);
        return resultMap;
    }

    /**
     * 制品名字
     * @param repositoryPath 请求路径
     */
    public  String getLibraryName(String repositoryPath,String type){
     //制品名称
        String libraryName = repositoryPath.substring(repositoryPath.indexOf("/") + 1, repositoryPath.indexOf(type));
        return libraryName;
    }

}
