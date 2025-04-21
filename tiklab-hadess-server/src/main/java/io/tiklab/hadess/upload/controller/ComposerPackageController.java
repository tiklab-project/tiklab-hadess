package io.tiklab.hadess.upload.controller;

import io.tiklab.core.exception.SystemException;
import io.tiklab.hadess.upload.service.ComposerUploadService;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
* 拉取composer 客户端根据服务端获取的json文件中的dist的地址拉取package
* */
@RestController
@RequestMapping("/composerPack")
public class ComposerPackageController {
    private static Logger logger = LoggerFactory.getLogger(ComposerPackageController.class);
    @Autowired
    ComposerUploadService composerUploadService;

    @RequestMapping(path = "/**",method = RequestMethod.GET)
    @ApiMethod(name = "downloadPackage",desc = "composer下载package")
    @ApiParam(name = "request",desc = "request")
    public void downloadPackage(HttpServletRequest request, HttpServletResponse response) {
        logger.info("composer download Package path："+request.getRequestURI());
        composerUploadService.downloadPackage(request,response);
    }

}
