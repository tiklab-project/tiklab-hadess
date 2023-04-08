package io.tiklab.xpack.library.controller;

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
import java.util.Base64;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/repository")
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
                Map map = downloadMavenService.mavenSubmit(contextPath, inputStream, userData,method);
                int code = (int)map.get("code");
                if (code==220) {
                    response.setHeader("Content-type", "text/plain");
                    String data = map.get("data").toString();
                    response.setStatus(200);

                    response.getWriter().write(data);
                }
                if(code==200){
                    response.setHeader("Content-type", "application/xml");
                    String data = map.get("data").toString();
                    response.setStatus(200,map.get("msg").toString());
                    //response.addHeader("ETag",map.get("ETag").toString());
                    response.getWriter().write(data);
                }
                if (code!=220&&code!=200){
                    response.setStatus((int)map.get("code"),map.get("msg").toString());
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
        Map map = downloadMavenService.mavenInstall(contextPath);
        response.setCharacterEncoding("UTF-8");
        try {
            if ((int)map.get("code")==200){
                response.setStatus(200,map.get("msg").toString());
                if (!"HEAD".equals(method)){
                    ServletOutputStream outputStream = response.getOutputStream();
                   // outputStream.write((byte[])map.get("data"));

                    outputStream.write((byte[])map.get("data"));
                }
            }
            if ((int)map.get("code")==404){
                response.setStatus((int)map.get("code"),map.get("msg").toString());
            }
           if ((int)map.get("code")==300){
               response.setStatus((int)map.get("code"),map.get("msg").toString());
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
