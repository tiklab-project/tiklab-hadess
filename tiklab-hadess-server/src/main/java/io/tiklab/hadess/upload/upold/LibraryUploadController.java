/*
package io.thoughtware.hadess.upload.upold;

import com.alibaba.fastjson.JSON;
import io.thoughtware.core.Result;
import io.thoughtware.core.exception.SystemException;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.hadess.repository.service.RepositoryService;
import io.thoughtware.hadess.upload.service.MavenUploadService;
import io.thoughtware.hadess.upload.service.NpmUploadService;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;

*/
/*
* maven、npm 制品上传拉取
* *//*

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

 */
/*   @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
*//*

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    */
/**
     * maven
     *//*

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

    */
/**
     * npm
     *//*

    public void npm(HttpServletRequest request,HttpServletResponse response,Repository repository,String repositoryPath){
        logger.info("开始执行npm："+repositoryPath);
        String referer = request.getHeader("referer");


        //请求全路径
        String requestFullURL = request.getRequestURL().toString();

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
                        Result<String> result = downloadNpmService.npmPullJson(repository, requestFullURL);
                        if (result.getCode()==200){
                            response.setContentType("application/json;charset=utf-8");
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().print(result.getData());
                        }else {
                            response.setStatus(result.getCode());
                            response.getWriter().print(result.getMsg());
                        }
                    }else {
                        //第二次交互以.tgz结尾
                        Result<byte[]> result = downloadNpmService.npmPullTgzData(repository, requestFullURL);
                        if (result.getCode()==200) {
                            response.setStatus(200, result.getMsg());
                            byte[] data = result.getData();

                            ServletOutputStream outputStream = response.getOutputStream();

                            outputStream.write(data);
                        }else {
                            response.setStatus(result.getCode());
                            response.getWriter().print(result.getMsg());
                        }
                    }
                }

                //登陆
                if (referer.contains("adduser")){
                    BufferedReader reader = request.getReader();
                    Map map = downloadNpmService.npmLogin(reader);
                    String jsonString = JSON.toJSONString(map);
                    boolean success = (boolean) map.get("result");
                    if (success){
                        response.setStatus(HttpServletResponse.SC_CREATED);
                    }else {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                    response.getWriter().print(jsonString);
                }

            } catch (IOException e) {
                throw new SystemException(e);
            }
        }
    }


}
*/
