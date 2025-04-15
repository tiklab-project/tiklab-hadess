package io.tiklab.hadess.upload.controller;

import io.tiklab.hadess.upload.service.ComposerUploadService;
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
import java.util.Enumeration;

@WebServlet(name = "ComposerServlet",urlPatterns = {"/composer/*"},
        initParams = {
                @WebInitParam(name = "base-path", value = "//"),
                @WebInitParam(name = "export-all", value = "true")
        }
)
public class ComposerUploadController extends HttpServlet {

        private static Logger logger = LoggerFactory.getLogger(ComposerUploadController.class);

        @Autowired
        ComposerUploadService composerUploadService;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                logger.info("composer拉取请求路径："+request.getRequestURI());
                composerUploadService.downloadPackage(request,response);
        }


        @Override
        protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                logger.info("composer上传请求路径："+req.getRequestURI());
                composerUploadService.uploadPackage(req,resp);
        }


        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                doGet(req, resp);
        }

}
