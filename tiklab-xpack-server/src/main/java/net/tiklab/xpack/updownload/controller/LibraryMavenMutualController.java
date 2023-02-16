package net.tiklab.xpack.updownload.controller;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.Result;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.xpack.repository.model.Repository;
import net.tiklab.xpack.updownload.service.LibraryMavenMutualService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/repository")
@Api(name = "LibraryMavenController",desc = "Maven提交拉取")
public class LibraryMavenMutualController {

    @Autowired
    LibraryMavenMutualService libraryMavenMutualService;


    @RequestMapping(path = "/maven/**",method = {RequestMethod.PUT,RequestMethod.GET})
    @ApiMethod(name = "mavenSubmit",desc = "mavne制品提交")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public void mavenSubmit(HttpServletRequest request, HttpServletResponse response){
        String contextPath = request.getRequestURI();
        String method = request.getMethod();
        String authorization = request.getHeader("Authorization");
        String basic = authorization.replace("Basic", "").trim();
        byte[] decode = Base64.getDecoder().decode(basic);

        try {
            //用户信息
            String userData = new String(decode, "UTF-8");
            InputStream inputStream = request.getInputStream();
            Map map = libraryMavenMutualService.mavenSubmit(contextPath, inputStream, userData,method);
            int code = (int)map.get("code");
            if (code==220) {
                response.setHeader("Content-type", "text/plain");
                String data = map.get("data").toString();
                response.setStatus(200);
                //response.addHeader("ETag","{SHA1{178158a672bd13432bf3347a04ab3850ee6524f3}}");
                response.getWriter().write(data);

            }
            if(code==200){
                response.setHeader("Content-type", "application/xml");
                String data = map.get("data").toString();
                response.setStatus(200,map.get("msg").toString());
                response.getWriter().write(data);
            }
            if (code!=220&&code!=200){
                response.setStatus((int)map.get("code"),map.get("msg").toString());

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @RequestMapping(path = "/maven-install/**",method = {RequestMethod.PUT,RequestMethod.GET})
    @ApiMethod(name = "mavenSubmit",desc = "mavne制品拉取")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public void mavenInstall(HttpServletRequest request, HttpServletResponse response) {
        String contextPath = request.getRequestURI();
        Map map = libraryMavenMutualService.mavenInstall(contextPath);
        response.setCharacterEncoding("UTF-8");
        try {
            if ((int)map.get("code")==200){
                response.setStatus(200,map.get("msg").toString());
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write((byte[])map.get("data"));
            }else {
                response.setStatus((int)map.get("code"),map.get("msg").toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
