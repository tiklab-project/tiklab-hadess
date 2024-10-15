package io.tiklab.hadess.upload.controller;

import io.tiklab.core.Result;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.repository.service.RepositoryService;
import io.tiklab.hadess.upload.common.response.GenericResponse;
import io.tiklab.hadess.upload.service.GenericUploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@WebServlet(name = "genericServlet",urlPatterns = {"/generic/*"},
        initParams = {
                @WebInitParam(name = "base-path", value = "//"),
                @WebInitParam(name = "export-all", value = "true")
        }
)
public class GenericUploadController  extends HttpServlet {


    @Autowired
    RepositoryService repositoryService;

    @Autowired
    GenericUploadService genericUploadService;

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getRequestURI();
        String repositoryPath = yamlDataMaService.getUploadRepositoryUrl(contextPath,"generic");

        response.setCharacterEncoding("UTF-8");
        //版本
        String substring = request.getQueryString();
        String version = substring.substring(substring.indexOf("=")+  1);
        
        //用户信息
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)){
            response.getWriter().print("{code:401,msg:用户信息不存在}");
            return;
        }

        String basic = authorization.replace("Basic", "").trim();
        byte[] decode = Base64.getDecoder().decode(basic);
        String userData = new String(decode, "UTF-8");

        String methodType = request.getHeader("type");

        if (StringUtils.isNotEmpty(methodType)&&("download").equals(methodType)){
            //下载
            Result<byte[]> result = genericUploadService.GenericDownload(repositoryPath, userData, version);
            GenericResponse.basicsRep(result,response);
        }else {
            //上传
            InputStream inputStream = request.getInputStream();
            String result=genericUploadService.GenericUpload(inputStream,repositoryPath, userData,version);
            response.getWriter().print(result);
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
