package io.thoughtware.hadess.upload.controller;

import io.thoughtware.core.Result;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.upload.service.HelmUploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.Enumeration;

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
        String repositoryPath = yamlDataMaService.getUploadRepositoryUrl(contextPath,"helm");


        Enumeration<String> headerNames = request.getHeaderNames();
        //用户信息
        String authorization = request.getHeader("Authorization");

        if (StringUtils.isEmpty(authorization)){
            //response.setStatus(401);
            response.setHeader("X-Content-Type-Options", "nosniff");
            response.setHeader("WWW-Authenticate", "BASIC realm=\"Hadess Repository Manager\"");
            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

            return;
        }


        //解析用户信息
        String basic = authorization.replace("Basic", "").trim();
        byte[] decode = Base64.getDecoder().decode(basic);
        String userData = new String(decode, "UTF-8");
        int check = helmUploadService.userCheck(userData);
        if (check==200){

        }
        response.setStatus(check);
        response.setHeader("Content-Type", "text/x-yaml");
        System.out.println("");
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
