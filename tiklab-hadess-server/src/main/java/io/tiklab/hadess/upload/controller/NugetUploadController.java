package io.tiklab.hadess.upload.controller;

import io.tiklab.hadess.common.FileUtil;
import io.tiklab.hadess.upload.service.NugetUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

@WebServlet(name = "nugetServlet",urlPatterns = {"/nuget/*"},
        initParams = {
                @WebInitParam(name = "base-path", value = "//"),
                @WebInitParam(name = "export-all", value = "true")
        }
)
@MultipartConfig
public class NugetUploadController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(NugetUploadController.class);

    @Autowired
    NugetUploadService nugetUploadService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        String contextPath = request.getRequestURI();
        logger.info("hadess nuget download request address："+contextPath);
        nugetUploadService.downloadData(request,response);
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getRequestURI();
        logger.info("hadess nuget upload request address："+contextPath);
        nugetUploadService.uploadData(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //上传
        String contextPath = req.getRequestURI();
        logger.info("hadess nuget request address："+contextPath);
    }
}
