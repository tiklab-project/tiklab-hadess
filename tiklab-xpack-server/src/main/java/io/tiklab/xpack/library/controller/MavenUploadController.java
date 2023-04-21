package io.tiklab.xpack.library.controller;


import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xpack.library.service.MavenUploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Base64;

@RestController
@RequestMapping("/xpack")
@Api(name = "LibraryMavenController",desc = "Maven提交拉取")
public class MavenUploadController {

    @Autowired
    MavenUploadService downloadMavenService;



    @RequestMapping(path = "/maven/**",method = {RequestMethod.PUT,RequestMethod.GET})
    @ApiMethod(name = "mavenSubmit",desc = "mavne")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public void mavenSubmit(HttpServletRequest request, HttpServletResponse response){

        String contextPath = request.getRequestURI();
        String method = request.getMethod();

        //用户信息
        String authorization = request.getHeader("Authorization");

        try {
            InputStream inputStream = request.getInputStream();
            if ("POST".equals(method)||"PUT".equals(method)){
                //用户信息
               // String authorization = request.getHeader("Authorization");
                if (StringUtils.isEmpty(authorization)){
                    response.setStatus(401,"Unauthorized");
                }else {
                    //用户信息
                    String basic = authorization.replace("Basic", "").trim();
                    byte[] decode = Base64.getDecoder().decode(basic);
                    //用户信息
                    String userData = new String(decode, "UTF-8");
                    Result<byte[]> result = downloadMavenService.mavenSubmit(contextPath, inputStream, userData);
                    response.setStatus(result.getCode(),result.getMsg());
                }
            }else {
                String repositoryUrl = contextPath.substring(contextPath.indexOf("xpack/maven") + 12);
                Result<byte[]> result = downloadMavenService.mavenPull(contextPath,repositoryUrl);
                if (result.getCode()==200){
                    response.setStatus(200,result.getMsg());
                    byte[] data = result.getData();
                    String s = new String(data, "UTF-8");

                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(data);
                }else {
                    response.setStatus(result.getCode(),result.getMsg());
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
