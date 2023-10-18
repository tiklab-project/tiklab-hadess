package io.tiklab.xpack.upload.service;

import io.tiklab.core.Result;
import io.tiklab.eam.passport.user.model.UserPassport;
import io.tiklab.eam.passport.user.service.UserPassportService;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.xpack.common.RepositoryUtil;
import io.tiklab.xpack.common.XpackYamlDataMaService;
import io.tiklab.xpack.library.model.*;
import io.tiklab.xpack.library.service.LibraryFileService;
import io.tiklab.xpack.library.service.LibraryService;
import io.tiklab.xpack.library.service.LibraryVersionService;
import io.tiklab.xpack.repository.model.Repository;
import io.tiklab.xpack.repository.service.RepositoryService;
import io.tiklab.xpack.upload.GenericUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Exporter
public class GenericUploadServiceImpl implements GenericUploadService {
    private static Logger logger = LoggerFactory.getLogger(GenericUploadServiceImpl.class);

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    UserPassportService userPassportService;

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    LibraryFileService libraryFileService;

    @Override
    public String GenericUpload(InputStream inputStream,String contextPath,String userData,String version) {
        String userName=null;
        try {
            //账号校验
            userName=userVerify(userData);
        }catch (Exception e){
            return "{code:401,msg:"+e.getMessage()+"}";
        }

        try {
          return   fileWriteData(contextPath,inputStream,userName,version);
        } catch (IOException e) {
            return "{code:400,msg:"+e.getMessage()+"}";
        }
    }

    @Override
    public Result<byte[]> GenericDownload(String contextPath, String userData, String version) {
        try {
            //账号校验
            userVerify(userData);
        }catch (Exception e){
            return Result.error(401,e.getMessage());
        }

        String[] split = contextPath.split("/");
        //制品库
        String repositoryName = split[2];
        Repository repository = repositoryService.findRepositoryByName(repositoryName);
        if (ObjectUtils.isEmpty(repository)){
            return Result.error(400,"仓库不存在");
        }

        //制品
        String fileName = split[3];
        String libraryName = fileName.substring(0, fileName.indexOf("."));
        Library library= libraryService.findLibraryByNameAndType(libraryName,"generic",null);
        if (ObjectUtils.isEmpty(library)){
            return Result.error(400,"制品不存在");
        }


        //制品文件
        LibraryFileQuery fileQuery = new LibraryFileQuery();
        fileQuery.setRepositoryId(repository.getId());
        fileQuery.setLibraryId(library.getId());
        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(fileQuery);
        if (CollectionUtils.isEmpty(libraryFileList)){
            return Result.error(400,"制品文件不存在");
        }

        List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> a.getFileUrl().contains(version)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(libraryFiles)){
            return Result.error(400,"当前版本制品不存在");
        }

        LibraryFile libraryFile = libraryFiles.get(0);

        //文件地址
        String filePah = yamlDataMaService.repositoryAddress() + "/" + libraryFile.getFileUrl();

        try {
           return readFileData(new File(filePah));
        } catch (IOException e) {
            return Result.error(400,"读取文件信息失败");
        }

    }

    /**
     * fileWriteData  写入文件
     * @param userName 用户名称
     * @param inputStream inputStream
     * @param contextPath 制品库
     * @param version  产品版本
     */
    public String fileWriteData(String contextPath, InputStream inputStream,
                                String userName ,String version) throws IOException {

        //仓库和文件名称
        String[] split = contextPath.split("/");
        String repositoryName = split[2];
        String fileName = split[3];
        String name = fileName.substring(0, fileName.indexOf("."));


        Repository repository = repositoryService.findRepositoryByName(repositoryName);
        if (ObjectUtils.isEmpty(repository)&&("generic").equals(repository.getType())){
            return "{code:400,msg:制品库不能存在}";
        }

        //仓库地址
        String versionPath= repository.getId()+"/"+name+"/"+version;
        String repositoryPath = yamlDataMaService.repositoryAddress() + "/" +versionPath;
        File folder = new File(repositoryPath);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        String filePath = repositoryPath + "/" + fileName;
        File fileData = new File(filePath);
        if (!fileData.exists()){
            fileData.createNewFile();
        }

        //用字节流写入文件
        FileOutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();

        //文件大小
        long FileLength = fileData.length();
        String size = RepositoryUtil.formatSize(FileLength);

        //创建制品
        Library library = libraryService.createLibraryData(name,"generic",repository);

        //制品版本创建、修改
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setPusher(userName);
        libraryVersion.setLibrary(library);
        libraryVersion.setVersion(version);
        libraryVersion.setRepository(repository);
        libraryVersion.setLibraryType("generic");
        String libraryVersionId = libraryVersionService.libraryVersionSplice(libraryVersion);

        //创建制品文件
        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setRepository(repository);
        libraryFile.setLibrary(library);
        libraryFile.setFileName(fileName);
        libraryFile.setFileUrl(versionPath+"/"+fileName);
        libraryFile.setRelativePath(name+"/"+version+"/"+fileName);
        libraryFile.setFileSize(size);

        libraryFileService.libraryFileSplice(libraryFile,libraryVersionId);
        return "{code:200,msg:upload上传成功}";
    }



    /**
     *  读取文件信息
     *  @param file     文件
     * @return
     */
    public Result readFileData(File file) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        byte[] bytes = bos.toByteArray();
        return Result.ok(bytes);
    }



    /**
     * userVerify  用户校验
     * @param userData 用户信息
     */
    public String userVerify(String userData){
        String[]  userObject=userData.split(":");
        String userName = userObject[0];
        String password = userObject[1];

        //通过xpack 界面手动上传不需要校验密码
        if (("xpackhand").equals(password)){
            return userName;
        }

        UserPassport userPassport = new UserPassport();
        userPassport.setAccount(userName);
        userPassport.setPassword(password);
        userPassport.setDirId("1");
        userPassportService.login(userPassport);
        return userName;
    }
}
