package io.tiklab.xpack.upload.controller;

import com.alibaba.fastjson.JSON;
import io.tiklab.core.Result;
import io.tiklab.core.exception.SystemException;
import io.tiklab.xpack.uoload.MavenUploadService;
import io.tiklab.xpack.uoload.NpmUploadService;
import io.tiklab.xpack.repository.model.Repository;
import io.tiklab.xpack.repository.service.RepositoryService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.Map;

@WebServlet(name = "xpackServlet",urlPatterns = {"/repository/*"},
        initParams = {
        @WebInitParam(name = "base-path", value = "//"),
        @WebInitParam(name = "export-all", value = "true")
}
)
public  class LibraryUploadController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(LibraryUploadController.class);
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    MavenUploadService downloadMavenService;

    @Autowired
    NpmUploadService downloadNpmService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getRequestURI();

        String repositoryPath = contextPath.substring(contextPath.indexOf("/", 1) + 1);
        String repositoryName=repositoryPath.substring(0,repositoryPath.indexOf("/", 1));

        if (StringUtils.isNotEmpty(repositoryName)){
            Repository repository = repositoryService.findRepositoryListByName(repositoryName);
            if (ObjectUtils.isEmpty(repository)){
                response.setStatus(404);
                return;
            }
            String type = repository.getType();
            if (("maven").equals(type)){
                maven(request,response,repositoryPath);
            }
            if (("npm").equals(type)){
                npm(request,response,repositoryPath);
            }
        }
    }

 /*   @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
*/
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    /**
     * maven
     */
    public void maven(HttpServletRequest request,HttpServletResponse response,String repositoryPath){
        logger.info("开始执行maven");
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
                    Result<byte[]> result = downloadMavenService.mavenSubmit(repositoryPath, inputStream, userData);
                    response.setStatus(result.getCode(),result.getMsg());
                }
            }else {
                Result<byte[]> result = downloadMavenService.mavenPull(repositoryPath);
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
            throw new SystemException(e);
        }

    }

    /**
     * npm
     */
    public void npm(HttpServletRequest request,HttpServletResponse response,String repositoryPath){

       /* String a="""

        """;*/
        String referer = request.getHeader("referer");
        if (StringUtils.isNotEmpty(referer)){
            try {

                //npm publish （提交）
                if (referer.contains("publish")){
                    InputStream inputStream = request.getInputStream();
                    Integer resultCode = downloadNpmService.npmSubmit(repositoryPath, inputStream);
                    response.setStatus(resultCode);
                }
                //npm install （拉取）
                if (referer.contains("install")){
                    if (!repositoryPath.endsWith(".tgz")){
                        //第一次交互
                        Map<String,String> data = downloadNpmService.npmPull(repositoryPath);
                        if ("200".equals(data.get("code"))){
                            response.setContentType("application/json;charset=utf-8");
                            Object o = JSON.toJSON(data.get("data"));
                            response.getWriter().print(o);
                        }
                        if ("404".equals(data.get("code"))){
                            response.setStatus(404);
                        }
                    }else {
                        //第二次交互以.tgz结尾
                        Map<String,Object> resultMap = downloadNpmService.npmPullTgzData(repositoryPath);
                        if ("200".equals(resultMap.get("code"))){
                            ServletOutputStream outputStream = response.getOutputStream();
                            outputStream.write((byte[])resultMap.get("data"));
                        }
                        if ("404".equals(resultMap.get("code"))){
                            response.setStatus(404);
                        }
                        if ("500".equals(resultMap.get("code"))){
                            response.setStatus(500);
                        }
                    }
                }

                //登陆
                if (referer.contains("adduser")){
                    BufferedReader reader = request.getReader();
                    Map map = downloadNpmService.npmLogin(reader);
                    String jsonString = JSON.toJSONString(map);
                    boolean success = (boolean) map.get("success");
                    if (success){
                        response.setStatus(201);
                    }else {
                        response.setStatus(401);
                    }
                    response.getWriter().print(jsonString);
                }

            } catch (IOException e) {
                throw new SystemException(e);
            }
        }
    }


}
