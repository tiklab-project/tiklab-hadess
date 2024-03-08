package io.thoughtware.hadess.upload.service;

import io.thoughtware.hadess.library.model.Library;
import io.thoughtware.hadess.library.model.LibraryFile;
import io.thoughtware.hadess.library.model.LibraryFileQuery;
import io.thoughtware.hadess.library.model.LibraryVersion;
import io.thoughtware.core.Result;
import io.thoughtware.eam.passport.user.service.UserPassportService;
import io.thoughtware.rpc.annotation.Exporter;
import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.library.service.LibraryFileService;
import io.thoughtware.hadess.library.service.LibraryService;
import io.thoughtware.hadess.library.service.LibraryVersionService;
import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.hadess.repository.service.RepositoryService;
import io.thoughtware.hadess.common.UserCheckService;
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

    @Autowired
    UserCheckService userCheckService;

    @Override
    public String GenericUpload(InputStream inputStream,String repositoryPath,String userData,String version) {
        String userName=null;
        try {
            //账号校验
             userName = userCheckService.genericUserCheck(userData);
        }catch (Exception e){
            return "{code:401,msg:"+e.getMessage()+"}";
        }

        try {
          return   fileWriteData(repositoryPath,inputStream,userName,version);
        } catch (IOException e) {
            return "{code:400,msg:"+e.getMessage()+"}";
        }
    }

    @Override
    public Result<byte[]> GenericDownload(String repositoryPath, String userData, String version) {
        try {
            //账号校验
          userCheckService.genericUserCheck(userData);
        }catch (Exception e){
            return Result.error(401,e.getMessage());
        }

        String[] split = repositoryPath.split("/");
        //制品库
        String repositoryName = split[0];
        Repository repository = repositoryService.findRepositoryByName(repositoryName);
        if (ObjectUtils.isEmpty(repository)){
            return Result.error(400,"仓库不存在");
        }

        //制品
        String fileName = split[3];
        String libraryName = fileName.substring(0, fileName.indexOf("."));
        Library library= libraryService.findLibraryByNameAndType(libraryName,"generic");
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
     * @param repositoryPath 制品库
     * @param version  产品版本
     */
    public String fileWriteData(String repositoryPath, InputStream inputStream,
                                String userName ,String version) throws IOException {

        //仓库和文件名称
        String[] split = repositoryPath.split("/");
        String repositoryName = split[0];
        String fileName = split[1];
        String name = fileName.substring(0, fileName.indexOf("."));


        Repository repository = repositoryService.findRepositoryByName(repositoryName);
        if (ObjectUtils.isEmpty(repository)&&("generic").equals(repository.getType())){
            return "{code:400,msg:制品库不能存在}";
        }

        //仓库地址
        String versionPath= repository.getId()+"/"+name+"/"+version;
        String folderPath = yamlDataMaService.repositoryAddress() + "/" +versionPath;
        File folder = new File(folderPath);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        String filePath = folderPath + "/" + fileName;
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
        libraryVersion.setSize(Long.valueOf(FileLength));
        libraryVersion.setLibraryType("generic");
        String libraryVersionId = libraryVersionService.createLibraryVersionSplice(libraryVersion,fileName);

        //创建制品文件
        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setRepository(repository);
        libraryFile.setLibrary(library);

        libraryFile.setFileName(fileName);
        libraryFile.setFileUrl(versionPath+"/"+fileName);
        libraryFile.setRelativePath(name+"/"+version+"/"+fileName);
        libraryFile.setFileSize(size);
        libraryFile.setSize(Long.valueOf(FileLength));
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
}
