package net.tiklab.oms.updownload.controller;

import net.tiklab.core.Result;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.rpc.annotation.Reference;
import net.tiklab.updownload.upload.FileResponse;
import net.tiklab.updownload.upload.FtpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/uploadFile")
@Api(name = "UploadFileController",desc = "产品安装包路径管理")
public class UploadFileController {

    @Autowired
    @Reference(address = "${ocs.address}")
    FtpClient ftpClient;

    @RequestMapping(path="/ftpUpload",method = RequestMethod.POST)
    @ApiMethod(name = "ftpUpload",desc = "ftp上传")
    public Result<FileResponse> ftpUpload(@RequestParam("uploadFile") MultipartFile uploadFile,String type){
        try {
            String fileName = uploadFile.getOriginalFilename();   //获取文件名字
            InputStream inputStream = uploadFile.getInputStream();
            FileResponse fileResponse = ftpClient.ftpUpload(inputStream, fileName, type);
            return Result.ok(fileResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @RequestMapping(path="/ftpDownload",method = RequestMethod.POST)
    @ApiMethod(name = "ftpDelete",desc = "ftp下载")
    public Result<Void> ftpDownload(String fileName,String  localPath,String dftPath){
       ftpClient.ftpDownload(fileName,localPath,dftPath);
        return Result.ok();
    }

    @RequestMapping(path="/ftpDelete",method = RequestMethod.POST)
    @ApiMethod(name = "ftpDelete",desc = "ftp删除")
    public Result<Boolean> ftpDelete(String fileName,String relativePath){
        boolean ftpDelete = ftpClient.ftpDelete(fileName, relativePath);
        return Result.ok(ftpDelete);
    }

}


