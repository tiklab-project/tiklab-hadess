package net.tiklab.xpack.updownload.controller;

import net.tiklab.core.Result;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.xpack.repository.model.Repository;
import net.tiklab.xpack.updownload.service.LibraryMavenMutualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

@RestController
@RequestMapping("/repository")
@Api(name = "LibraryMavenController",desc = "Maven提交拉取")
public class LibraryMavenMutualController {

    @Autowired
    LibraryMavenMutualService libraryMavenMutualService;


    @RequestMapping(path = "/maven/**",method = {RequestMethod.PUT,RequestMethod.GET})
    @ApiMethod(name = "mavenSubmit",desc = "mavne制品提交")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public Result<Repository> mavenSubmit(HttpServletRequest request, HttpServletResponse response){
        String contextPath = request.getRequestURI();
        String referer = request.getHeader("referer");
        Enumeration<String> headerNames = request.getHeaderNames();
        InputStream inputStream=null;
        try {
            HttpSession session = request.getSession();

            inputStream = request.getInputStream();
            libraryMavenMutualService.mavenSubmit(contextPath,inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @RequestMapping(path = "/maven-install/**",method = {RequestMethod.PUT,RequestMethod.GET})
    @ApiMethod(name = "mavenSubmit",desc = "mavne制品拉取")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public void mavenInstall(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        String contextPath = request.getRequestURI();
        byte[] fileData = libraryMavenMutualService.mavenInstall(contextPath);
        Enumeration<String> headerNames = request.getHeaderNames();
        response.setCharacterEncoding("UTF-8");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(fileData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
