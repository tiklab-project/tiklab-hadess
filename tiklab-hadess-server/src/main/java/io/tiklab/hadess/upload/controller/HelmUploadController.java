package io.tiklab.hadess.upload.controller;

import io.tiklab.core.Result;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.upload.common.response.HelmResponse;
import io.tiklab.hadess.upload.model.LibraryHelmClient;
import io.tiklab.hadess.upload.service.HelmUploadService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@WebServlet(name = "helmServlet",urlPatterns = {"/helm/*"},
        initParams = {
                @WebInitParam(name = "base-path", value = "//"),
                @WebInitParam(name = "export-all", value = "true")
        }
)
public class HelmUploadController  extends HttpServlet {

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Autowired
    HelmUploadService helmUploadService;



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contextPath = request.getRequestURI();
        String pathData = yamlDataMaService.getUploadRepositoryUrl(contextPath,"helm");

        //用户信息
        String authorization = request.getHeader("Authorization");
        String agent = request.getHeader("user-agent");

        //推送
        if (agent.startsWith("curl")){
            //推送用户信息认证
            Result<String> result = helmUploadService.userCheck(authorization);
            //认证失败返回错误信息
            if (result.getCode()==401){
                HelmResponse.helmRep(result,response);
                return;
            }
            //获取请求信息
            LibraryHelmClient helmClient = HelmResponse.findMultipartFile(request);
            helmClient.setRepositoryName(pathData);
            helmClient.setUserName(result.getData());
            Result<String> uploadResult = helmUploadService.uploadData(helmClient);
            HelmResponse.helmRep(uploadResult,response);

        }else {
            //连接仓库( helm repo add)、更新仓库（helm repo update） 请求路径后缀为.yaml
            if (contextPath.endsWith(".yaml")){
                //连接仓库 用户信息认证
                Result<byte[]> result = helmUploadService.connectWarehouse(authorization, pathData);
                HelmResponse.helmRepVerify(result,response);
            }
            //拉取helm制品   请求路径 .tgz。
            if (contextPath.endsWith(".tgz")){
                //推送用户信息认证
                Result<String> result = helmUploadService.userCheck(authorization);
                //认证失败返回错误信息
                if (result.getCode()==401){
                    HelmResponse.helmRep(result,response);
                }
                Result<byte[]> pullData = helmUploadService.pullData(pathData);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(pullData.getData());
            }
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
