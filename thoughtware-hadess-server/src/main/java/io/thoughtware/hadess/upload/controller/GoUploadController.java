package io.thoughtware.hadess.upload.controller;


import io.thoughtware.core.Result;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.upload.common.response.GoResponse;
import io.thoughtware.hadess.upload.common.response.HelmResponse;
import io.thoughtware.hadess.upload.model.LibraryHelmClient;
import io.thoughtware.hadess.upload.service.GoUploadService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "goServlet",urlPatterns = {"/go/*"},
        initParams = {
                @WebInitParam(name = "base-path", value = "//"),
                @WebInitParam(name = "export-all", value = "true")
        }
)
public class GoUploadController extends HttpServlet {
    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Autowired
    GoUploadService goUploadService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contextPath = request.getRequestURI();
        String pathData = yamlDataMaService.getUploadRepositoryUrl(contextPath,"go");
        Enumeration<String> headerNames = request.getHeaderNames();
        //用户信息
        String authorization = request.getHeader("Authorization");
        String agent = request.getHeader("user-agent");

        Result<byte[]> pullData = goUploadService.pullData(pathData);

        GoResponse.goPull(pullData,contextPath,response);
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
