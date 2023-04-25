package io.tiklab.xpack.library.controller;


import io.tiklab.core.Result;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xpack.library.model.Library;
import io.tiklab.xpack.library.service.LibraryFileReadService;
import io.tiklab.xpack.library.service.LibraryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * fileReadController  读取文件
 */
@RestController
@RequestMapping("/libraryFile")
public class FileReadController  {

    @Autowired
    private LibraryFileReadService libraryFileReadService;



    @RequestMapping(path="/snapshots/**",method = RequestMethod.GET)
    @ApiMethod(name = "fileRead",desc = "文件读取")
    @ApiParam(name = "library",desc = "library",required = true)
    public void fileRead(HttpServletRequest request, HttpServletResponse response){
        String requestURI = request.getRequestURI();

        try {
            byte[] bytes = libraryFileReadService.fileRead(requestURI);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/downloadSingleFile/**",method = RequestMethod.GET)
    @ApiMethod(name = "download",desc = "单个制品文件下载")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public void downloadSingleFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            String requestURI = request.getRequestURI();
            String fileName = requestURI.substring(requestURI.lastIndexOf("/")+1);
            byte[] bytes = libraryFileReadService.fileRead(requestURI);
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/findServerIp",method = RequestMethod.GET)
    @ApiMethod(name = "findServerIp",desc = "获取当前服务器ip")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public Result<String> findServerIp() {

        String address=libraryFileReadService.findServerIp();
        return Result.ok(address);
    }
}
