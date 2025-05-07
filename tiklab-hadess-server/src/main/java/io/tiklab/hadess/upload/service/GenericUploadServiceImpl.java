package io.tiklab.hadess.upload.service;

import io.tiklab.hadess.common.FileUtil;
import io.tiklab.hadess.library.model.Library;
import io.tiklab.hadess.library.model.LibraryFile;
import io.tiklab.hadess.library.model.LibraryFileQuery;
import io.tiklab.hadess.library.model.LibraryVersion;
import io.tiklab.core.Result;
import io.tiklab.eam.passport.user.service.UserPassportService;
import io.tiklab.hadess.upload.common.response.GenericResponse;
import io.tiklab.hadess.upload.common.response.PypiResponse;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.hadess.common.RepositoryUtil;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.library.service.LibraryFileService;
import io.tiklab.hadess.library.service.LibraryService;
import io.tiklab.hadess.library.service.LibraryVersionService;
import io.tiklab.hadess.repository.model.Repository;
import io.tiklab.hadess.repository.service.RepositoryService;
import io.tiklab.hadess.common.UserCheckService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
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
    public void GenericUpload(HttpServletRequest request, HttpServletResponse response) {
        String contextPath = request.getRequestURI();
        String repositoryPath = yamlDataMaService.getUploadRepositoryUrl(contextPath,"generic");
        response.setCharacterEncoding("UTF-8");

        //校验用户信息
        String authorization = request.getHeader("Authorization");
        Result userCheckResult = userCheck(authorization);
        if (userCheckResult.getCode()==401){
            GenericResponse.correctToClient(response,userCheckResult.getMsg());
            return;
        }

        //写入文件
        Result result = fileWriteData(request, repositoryPath, userCheckResult.getData().toString());
        GenericResponse.correctToClient(response,result.getMsg());
    }

    @Override
    public void GenericDownload(HttpServletRequest request, HttpServletResponse response) {
        String contextPath = request.getRequestURI();
        String repositoryPath = yamlDataMaService.getUploadRepositoryUrl(contextPath,"generic");
        response.setCharacterEncoding("UTF-8");

        //校验用户信息
        String authorization = request.getHeader("Authorization");
        Result userCheckResult = userCheck(authorization);
        if (userCheckResult.getCode()==401){
            GenericResponse.errorToClient(response,userCheckResult.getMsg());
            return;
        }

        //版本
        String substring = request.getQueryString();
        String version = substring.substring(substring.indexOf("=")+  1);

        //拉取
        String[] split = repositoryPath.split("/");
        //制品库
        String repositoryName = split[0];
        Repository repository = repositoryService.findRepositoryByName(repositoryName);
        if (ObjectUtils.isEmpty(repository)){
            GenericResponse.errorToClient(response,"仓库不存在");
            return;
        }

        //制品
        String fileName = split[1];
        Library library= libraryService.findLibraryByCondition(fileName,"generic",repository.getId());
        if (ObjectUtils.isEmpty(library)){
            GenericResponse.errorToClient(response,"制品不存在");
            return;
        }


        //制品文件
        LibraryFileQuery fileQuery = new LibraryFileQuery();
        fileQuery.setRepositoryId(repository.getId());
        fileQuery.setLibraryId(library.getId());
        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(fileQuery);
        if (CollectionUtils.isEmpty(libraryFileList)){
            GenericResponse.errorToClient(response,"制品文件不存在");
            return;
        }

        List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> a.getFileUrl().contains(version)).toList();
        if (CollectionUtils.isEmpty(libraryFiles)){
            GenericResponse.errorToClient(response,"当前版本制品不存在");
            return;
        }

        LibraryFile libraryFile = libraryFiles.get(0);

        //文件地址
        String filePah = yamlDataMaService.repositoryAddress() + "/" + libraryFile.getFileUrl();

        try {
            File file = new File(filePah);
            if (!file.exists()){
                GenericResponse.errorToClient(response,"文件不存在");
            }
            response.setContentType("application/json; charset=UTF-8");
            FileUtil.readFileData(file,response);
        } catch (IOException e) {
            GenericResponse.errorToClient(response,"读取文件信息失败");
        }

    }


    /**
     * fileWriteData  写入文件
     * @param userName 用户名称
     * @param repositoryPath 制品库
     */
    public Result fileWriteData(HttpServletRequest request,String repositoryPath, String userName )  {

        try {
            InputStream inputStream = request.getInputStream();
            //版本
            String substring = request.getQueryString();
            String version = substring.substring(substring.indexOf("=")+  1);

            //仓库和文件名称
            String[] split = repositoryPath.split("/");
            String repositoryName = split[0];
            String fileName = split[1];


            //文件存储的路径名
            String name=fileName;
            if (fileName.contains(".")){
              if (fileName.endsWith(".gz")){
                  String beforeLast = StringUtils.substringBeforeLast(fileName, ".");
                   name = StringUtils.substringBeforeLast(beforeLast, ".");

              }else {
                  name = fileName.substring(0, fileName.indexOf("."));
              }
            }
            Repository repository = repositoryService.findRepositoryByName(repositoryName);
            if (ObjectUtils.isEmpty(repository)&&("generic").equals(repository.getType())){
                return Result.error(400,"{code:400,msg:制品库不存在}");
            }

            //仓库地址
            String versionPath= repository.getId()+"/"+name+"/"+version;
            String folderPath = yamlDataMaService.repositoryAddress() + "/" +versionPath;
            //写入文件
            FileUtil.copyFileData(inputStream,folderPath,fileName);

            //文件大小
            String filePath = folderPath + "/" + fileName;
            File fileData = new File(filePath);
            long FileLength = fileData.length();
            String size = RepositoryUtil.formatSize(FileLength);

            //创建制品
            Library library = libraryService.createLibraryData(fileName,"generic",repository);

            //制品版本创建、修改
            LibraryVersion libraryVersion = new LibraryVersion();
            libraryVersion.setPusher(userName);
            libraryVersion.setLibrary(library);
            libraryVersion.setVersion(version);
            libraryVersion.setRepository(repository);
            libraryVersion.setSize(Long.valueOf(FileLength));
            libraryVersion.setLibraryType("generic");
            String libraryVersionId = libraryVersionService.redactLibraryVersion(libraryVersion);
            libraryVersion.setId(libraryVersionId);

            //创建制品文件
            LibraryFile libraryFile = new LibraryFile();
            libraryFile.setRepository(repository);
            libraryFile.setLibrary(library);
            libraryFile.setLibraryVersion(libraryVersion);

            libraryFile.setFileName(fileName);
            libraryFile.setFileUrl(versionPath+"/"+fileName);
            libraryFile.setRelativePath(name+"/"+version+"/"+fileName);
            libraryFile.setFileSize(size);
            libraryFile.setSize(FileLength);
            libraryFileService.redactLibraryFile(libraryFile);
            return Result.ok(200,"{code:200,msg:upload上传成功}");
        }catch (Exception e){
            return Result.error(500,"{code:500,msg:upload上传失败:"+e.getMessage()+"}");
        }
    }







    /**
     * 校验用户信息
     * @param authorization  客户端上传的用户信息
     */
    public Result userCheck(String authorization) {

        //docker第一次访问没有用户信息 为了获取支持的验证机制
        if (ObjectUtils.isEmpty(authorization)){
            logger.info("pypi拉取推送没有用户信息");
            return  Result.error(401,"{code:401,msg:用户信息不存在}");
        }

        try {
            String basic = authorization.replace("Basic", "").trim();
            byte[] decode = Base64.getDecoder().decode(basic);
            //用户信息
            String userData = new String(decode, "UTF-8");
            String[] split = userData.split(":");
            String userName = split[0];
            String password = split[1];

            //generic制品库里面上传 制品
            if (("xpackhand").equals(password)){
                return Result.ok(userName);
            }

            userCheckService.basicsUserCheck(userData);
            return Result.ok(userName);
        }catch (Exception e){
            return  Result.error(401,"{code:401,msg:"+e.getMessage()+"}");
        }
    }


}
