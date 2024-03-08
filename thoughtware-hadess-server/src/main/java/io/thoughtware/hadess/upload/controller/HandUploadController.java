package io.thoughtware.hadess.upload.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.exception.SystemException;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.upload.service.MavenUploadService;
import io.thoughtware.hadess.upload.service.NpmUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RestController
@RequestMapping("/xpack")
@Api(name = "MavenUploadController",desc = "Maven提交这个用于手动提交不校验用户信息")
public class HandUploadController {

    @Autowired
    MavenUploadService downloadMavenService;

    @Autowired
    NpmUploadService downloadNpmService;

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @RequestMapping(path = "/maven/**",method = {RequestMethod.PUT,RequestMethod.GET})
    @ApiMethod(name = "mavenSubmit",desc = "通过xpack界面手动上传maven")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public void mavenSubmit(HttpServletRequest request, HttpServletResponse response) {
        String contextPath = request.getRequestURI();

        String path = yamlDataMaService.getUploadRepositoryUrl(contextPath,"xpack");
        String repositoryPath=path.substring(path.indexOf("/")+1);
        String method = request.getMethod();

        try {
            InputStream inputStream = request.getInputStream();


            if ("POST".equals(method) || "PUT".equals(method)) {

                Result<byte[]> result = downloadMavenService.mavenSubmit(repositoryPath, inputStream, "hadess");
                response.setStatus(result.getCode(), result.getMsg());

            } else {
                Result<byte[]> result = downloadMavenService.mavenPull(repositoryPath);
                if (result.getCode() == 200) {
                    response.setStatus(200, result.getMsg());
                    byte[] data = result.getData();
                    String s = new String(data, "UTF-8");

                    ServletOutputStream outputStream = response.getOutputStream();

                    outputStream.write(data);
                } else {
                    response.setStatus(result.getCode(), result.getMsg());
                }
            }
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }


}
