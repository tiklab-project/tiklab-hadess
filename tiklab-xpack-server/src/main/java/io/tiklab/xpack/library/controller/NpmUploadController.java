package io.tiklab.xpack.library.controller;

import com.alibaba.fastjson.JSON;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xpack.library.service.NpmUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import static org.apache.coyote.http11.Constants.a;

@RestController
@RequestMapping("/xpack")
@Api(name = "LibraryNpmController",desc = "npm提交拉取")
public class NpmUploadController {

    private static Logger logger = LoggerFactory.getLogger(NpmUploadController.class);

    @Autowired
    NpmUploadService downloadNpmService;

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
                Integer resultCode = downloadNpmService.npmSubmit(contextPath, inputStream);
                response.setStatus(resultCode);
            }

            //npm install （拉取）
            if (referer.contains("install")){
                if (contextPath.endsWith(".tgz")){
                    //第二次交互以.tgz结尾
                    Map<String,Object> resultMap = downloadNpmService.npmPullTgzData(contextPath);
                    if ("200".equals(resultMap.get("code"))){
                        ServletOutputStream outputStream = response.getOutputStream();
                        outputStream.write((byte[])resultMap.get("data"));
                    }
                    if ("404".equals(resultMap.get("code"))){
                        response.setStatus(404);
                    }

                }else {
                    //第一次交互
                    Map<String,String> data = downloadNpmService.npmPull(contextPath);
                    switch (data.get("code")){
                        case "200":
                            response.setContentType("application/json;charset=utf-8");
                            Object o = JSON.toJSON(data);
                            response.getWriter().print(o);
                            break;
                        case "404":
                            response.setStatus(404);
                            break;
                    }
                }
            }

            //登陆
            if (referer.contains("adduser")){
                BufferedReader reader = request.getReader();
                Map map = downloadNpmService.npmLogin(reader);
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
