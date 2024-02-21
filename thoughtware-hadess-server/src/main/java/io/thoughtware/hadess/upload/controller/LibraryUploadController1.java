package io.thoughtware.hadess.upload.controller;

import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/repository")
@Api(name = "DockerUploadController",desc = "Maven提交这个用于手动提交不校验用户信息")
public class LibraryUploadController1 {

    @RequestMapping(path="/**",method = {RequestMethod.GET,RequestMethod.HEAD,RequestMethod.POST,
            RequestMethod.PATCH,RequestMethod.PUT})
    @ApiMethod(name = "dockerPush",desc = "docker上传")
    public void dockerPush(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getRequestURI();
        System.out.println("");
    }
}
