package io.tiklab.hadess.upload.controller;

import io.tiklab.core.Result;
import io.tiklab.core.exception.SystemException;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.upload.model.LibraryUploadData;
import io.tiklab.hadess.upload.model.LibraryUploadResult;
import io.tiklab.hadess.upload.service.MavenUploadService;
import io.tiklab.hadess.upload.service.NpmUploadService;
import io.tiklab.hadess.repository.model.Repository;
import io.tiklab.hadess.repository.service.RepositoryService;
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

/*
* maven、npm 制品上传拉取
* */
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

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getRequestURI();
        String repositoryPath = yamlDataMaService.getUploadRepositoryUrl(contextPath,"repository");
        String repositoryName=repositoryPath.substring(0,repositoryPath.indexOf("/", 1));


        if (StringUtils.isNotEmpty(repositoryName)){
            Repository repository = repositoryService.findRepositoryByName(repositoryName);
            if (ObjectUtils.isEmpty(repository)){
                response.setStatus(404);
                return;
            }
            String type = repository.getType();
            if (("maven").equals(type)){
                maven(request,response,repositoryPath);
            }
            if (("npm").equals(type)){
                npm(request,response,repository,repositoryPath);
            }
        }
    }

/*    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }*/

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    /**
     * maven
     */
    public void maven(HttpServletRequest request,HttpServletResponse response,String repositoryPath){
        logger.info("开始执行maven："+repositoryPath);

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
                    response.getWriter().print("Unauthorized");
                }else {
                    //用户信息
                    String basic = authorization.replace("Basic", "").trim();
                    byte[] decode = Base64.getDecoder().decode(basic);
                    //用户信息
                    String userData = new String(decode, "UTF-8");
                    Result<byte[]> result = downloadMavenService.mavenSubmit(repositoryPath, inputStream, userData);
                    response.setStatus(result.getCode(),result.getMsg());
                    response.getWriter().print(result.getMsg());
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
                    response.getWriter().print(result.getMsg());
                }
            }
        }catch (Exception e){
            throw new SystemException(e);
        }
    }

    /**
     * npm
     */
    public void npm(HttpServletRequest request,HttpServletResponse response,Repository repository,String repositoryPath){
        logger.info("开始执行npm："+repositoryPath);
        try {
            String method = request.getMethod();

            //请求全路径
            String absolutePath = request.getRequestURL().toString();
            //请求方式yarn、npm
            String agent = request.getHeader("user-agent");
            //提交的用户认证信息
            String authorization = request.getHeader("authorization");

            //npm 请求的方式
            String referer = request.getHeader("referer");

            LibraryUploadData uploadData = new LibraryUploadData();
            if (StringUtils.isNotEmpty(referer)&&referer.contains("adduser")) {
                //npm 登陆信息
                uploadData.setUserReader(request.getReader());
            }else {
                //npm 推送文件信息
                uploadData.setInputStream(request.getInputStream());
            }
            uploadData.setMethod(method);
            uploadData.setAgentType(agent);
            uploadData.setAuthorization(authorization);
            uploadData.setRelativePath(repositoryPath);
            uploadData.setAbsolutePath(absolutePath);
            uploadData.setReferer(referer);
            uploadData.setRepository(repository);

            //发送请求
            LibraryUploadResult uploadResult = downloadNpmService.uploadEntrance(uploadData);
            response.setStatus(uploadResult.getStatus());
            if (StringUtils.isNotEmpty(uploadResult.getContentType())&&("string").equals(uploadResult.getResultType())){
                response.setContentType(uploadResult.getContentType());
                response.getWriter().print(uploadResult.getData());
            }

            //拉取返回byte 类型格式
            if (("byte").equals(uploadResult.getResultType())){
                byte[] data = uploadResult.getDetails();
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(data);
            }
        }catch (Exception e){
            throw new SystemException(e);
        }
    }
}
