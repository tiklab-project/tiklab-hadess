package io.thoughtware.hadess.upload.controller;


import io.thoughtware.core.Result;
import io.thoughtware.core.exception.SystemException;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
import io.thoughtware.hadess.library.model.LibraryFileHand;
import io.thoughtware.hadess.upload.service.HandUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;

/**
 * fileReadController  读取文件
 */
@RestController
@RequestMapping("/fileHand")
public class FileHandleController  {

    @Autowired
    private HandUploadService handUploadService;



    @RequestMapping(path="/read/**",method = RequestMethod.GET)
    @ApiMethod(name = "fileRead",desc = "文件读取")
    @ApiParam(name = "library",desc = "library",required = true)
    public void fileRead(HttpServletRequest request, HttpServletResponse response){
        String requestURI = request.getRequestURI();

        try {
            byte[] bytes = handUploadService.fileRead(requestURI);
            String s = new String(bytes, "UTF-8");

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }

    @RequestMapping(path = "/download/**",method = RequestMethod.GET)
    @ApiMethod(name = "download",desc = "单个制品文件下载")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public void downloadSingleFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            String requestURI = request.getRequestURI();

            String fileName = requestURI.substring(requestURI.lastIndexOf("/")+1);
            byte[] bytes = handUploadService.fileRead(requestURI);
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }


    @RequestMapping(path="/fileUpload/**",method = RequestMethod.POST)
    @ApiMethod(name = "fileUpload",desc = "文件上传")
    public Result<String> fileUpload(@RequestParam("uploadFile") MultipartFile uploadFile){
        try {
            String fileName = uploadFile.getOriginalFilename();   //获取文件名字
            InputStream inputStream = uploadFile.getInputStream();
            String finePath = handUploadService.fileUpload(inputStream, fileName);
            return Result.ok(finePath);
        } catch (IOException e) {
            throw new SystemException(e.getMessage());
        }
    }

    @RequestMapping(path = "/findServerIp",method = RequestMethod.POST)
    @ApiMethod(name = "findServerIp",desc = "获取当前服务器ip")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public Result<String> findServerIp() {

        String address=handUploadService.findServerIp();
        return Result.ok(address);
    }


    @RequestMapping(path="/libraryHandPush",method = RequestMethod.POST)
    @ApiMethod(name = "libraryHandPush",desc = "手动上传制品")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public Result<String> libraryHandPush(@RequestBody @Valid @NotNull LibraryFileHand libraryFileHand){

        String handPush = handUploadService.libraryHandPush(libraryFileHand);

        return Result.ok(handPush);
    }
    @RequestMapping(path="/findHandPushResult",method = RequestMethod.POST)
    @ApiMethod(name = "findHandPushResult",desc = "获取手动上传结果")
    @ApiParam(name = "repositoryId",desc = "制品库id")
    public Result<String> findHandPushResult(@NotNull String repositoryId){
        String handPushResult = handUploadService.findHandPushResult(repositoryId);
        return Result.ok(handPushResult);
    }


}
