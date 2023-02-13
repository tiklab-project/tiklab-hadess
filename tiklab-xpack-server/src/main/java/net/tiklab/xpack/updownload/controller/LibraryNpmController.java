package net.tiklab.xpack.updownload.controller;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.xdevapi.JsonString;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.xpack.updownload.service.LibraryNpmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;

@RestController
@RequestMapping("/repository")
@Api(name = "LibraryNpmController",desc = "npm提交拉取")
public class LibraryNpmController {

    private static Logger logger = LoggerFactory.getLogger(LibraryNpmController.class);

    @Autowired
    LibraryNpmService libraryNpmService;

    @RequestMapping(path = "/npm/**",method = {RequestMethod.PUT,RequestMethod.GET})
    @ApiMethod(name = "mavenSubmit",desc = "npm制品提交")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public Object npmSubmit(HttpServletRequest request, HttpServletResponse response){
        String contextPath = request.getRequestURI();
        String referer = request.getHeader("referer");
        try {
            //npm publish （提交）
            if (referer.contains("publish")){
                InputStream inputStream = request.getInputStream();
                Integer resultCode = libraryNpmService.npmSubmit(contextPath, inputStream);
                response.setStatus(resultCode);
            }
            //npm install （拉取）
            if (referer.contains("install")){
                if (contextPath.endsWith(".tgz")){
                    //第二次交互以.tgz结尾
                    byte[] a=libraryNpmService.npmPullTgzData(contextPath);
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(a);
                }else {
                    //第一次交互
                    Object data = libraryNpmService.npmPull(contextPath);
                    response.setContentType("application/json;charset=utf-8");
                    Object o = JSON.toJSON(data);
                    response.getWriter().print(o);
                }
            }
            //登陆
            if (referer.contains("adduser")){
                BufferedReader reader = request.getReader();
                Map map = libraryNpmService.npmLogin(reader);
                String jsonString = JSON.toJSONString(map);
                Object success = map.get("success");
                if (success.equals("0K")){
                    response.setStatus(201);
                }else {
                    response.setStatus(401);
                }
                response.getWriter().print(jsonString);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
