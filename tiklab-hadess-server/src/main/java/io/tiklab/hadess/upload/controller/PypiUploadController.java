package io.tiklab.hadess.upload.controller;

import io.tiklab.hadess.upload.service.PypiUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "pypiServlet",urlPatterns = {"/pypi/*"},
        initParams = {
                @WebInitParam(name = "base-path", value = "//"),
                @WebInitParam(name = "export-all", value = "true")
        }
)
public class PypiUploadController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(PypiUploadController.class);

    @Autowired
    PypiUploadService pypiUploadService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getRequestURI();
        logger.info("pypi拉取请求路径："+contextPath);
        if (contextPath.contains("packages")){
            //下载包文件
            pypiUploadService.downloadPackages(contextPath,response);
        }else {
            //下载元数据
            pypiUploadService.downloadMetadata(request,response);
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //上传
        pypiUploadService.UploadData(req,resp);
    }

}
