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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

@RestController
@RequestMapping("/xpack")
@Api(name = "LibraryMavenController",desc = "Maven提交拉取")
public class MavenUploadController {

    @Autowired
    MavenUploadService downloadMavenService;


    @RequestMapping(path = "/maven/**",method = {RequestMethod.PUT,RequestMethod.GET})
    @ApiMethod(name = "mavenSubmit",desc = "mavne制品提交")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public void mavenSubmit(HttpServletRequest request, HttpServletResponse response){
        String contextPath = request.getRequestURI();
        String method = request.getMethod();
        String authorization = request.getHeader("Authorization");

        if (StringUtils.isEmpty(authorization)){
            response.setStatus(401,"Unauthorized");
        }else {
            String basic = authorization.replace("Basic", "").trim();
            byte[] decode = Base64.getDecoder().decode(basic);
            try {
                //用户信息
                String userData = new String(decode, "UTF-8");
                InputStream inputStream = request.getInputStream();

                Result<byte[]> result = downloadMavenService.mavenSubmit(contextPath, inputStream, userData, method);
                if(result.getCode()==200){
                    response.setStatus(200,result.getMsg());
                    ServletOutputStream outputStream = response.getOutputStream();
                    if (contextPath.contains("maven-metadata.xml.sha1")){
                        outputStream.write(result.getData());
                    }else {
                        outputStream.write(result.getData());
                    }
                }else {
                    response.setStatus(result.getCode(),result.getMsg());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @RequestMapping(path = "/maven-install/**",method = {RequestMethod.PUT,RequestMethod.GET})
    @ApiMethod(name = "mavenSubmit",desc = "mavne制品拉取")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public void mavenInstall(HttpServletRequest request, HttpServletResponse response) {
        String contextPath = request.getRequestURI();
        String method = request.getMethod();
        Result<byte[]> result = downloadMavenService.mavenInstall(contextPath);
        response.setCharacterEncoding("UTF-8");
        try {
            if (result.getCode()==200){
                response.setStatus(200,result.getMsg());
                if (!"HEAD".equals(method)){
                    //byte[] data = (byte[]) map.get("data");
                    byte[] data = result.getData();
                    String s = new String(data, "UTF-8");
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(data);
                    /*if (contextPath.endsWith(".jar")){
                        ServletOutputStream outputStream = response.getOutputStream();
                        outputStream.write(data);
                    }else {
                        String str = new String(data, "UTF-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(str);
                    }*/
                }
            }else {
                response.setStatus(result.getCode(),result.getMsg());
            }
            }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
