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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        String contextPath = request.getRequestURI();
        String methodType = request.getHeader("type");

        genericUploadService.GenericDownload(request,response);

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        genericUploadService.GenericUpload(req,resp);
    }
}
