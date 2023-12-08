package io.thoughtware.hadess.upload.controller;

import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.UuidGenerator;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.upload.DockerUploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/v2")
@Api(name = "DockerUploadController",desc = "Maven提交这个用于手动提交不校验用户信息")
public class DockerUploadController {

    @Autowired
    DockerUploadService dockerUploadService;

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @RequestMapping(path="/**",method = {RequestMethod.GET,RequestMethod.HEAD,RequestMethod.POST,
            RequestMethod.PATCH,RequestMethod.PUT})
    @ApiMethod(name = "dockerPush",desc = "docker上传")
    public void dockerPush(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getRequestURI();

        String repositoryPath =contextPath.substring(contextPath.indexOf("/", 1) + 1);

        String method = request.getMethod();
        StringBuffer requestURL = request.getRequestURL();
        //登陆用户校验 或者pull 镜像数据
        if (("GET").equals(method)){
            //请求路径中存在sha256 代表pull 请求
            if (contextPath.contains("/sha256:")){
                String sha256 = contextPath.substring(contextPath.lastIndexOf("sha256"));
                Map<String, String> resultMap = dockerUploadService.readMirroringData(repositoryPath);
                String data = resultMap.get("data");
                response.setStatus(HttpServletResponse.SC_OK);
                response.setHeader("Content-Length",resultMap.get("size"));
                response.setHeader("Docker-Content-Digest", sha256);
                // 设置响应的内容类型和状态码
                if (contextPath.contains("/manifest")){
                    response.setContentType("application/vnd.docker.distribution.manifest.v2+json");
                    response.getWriter().write(resultMap.get("data"));
                }else {
                    if (data.startsWith("{")){
                        response.setContentType("application/vnd.docker.container.image.v1+json");
                        response.getWriter().write(data);
                    }else {
                        //zip 拉取 边读边推
                        response.setContentType("application/vnd.docker.image.rootfs.diff.tar.gzip");
                        String url = resultMap.get("url");
                        InputStream inputStream = new FileInputStream(new File(url));
                        ServletOutputStream outputStream = response.getOutputStream();
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        // 关闭输入流和输出流
                        inputStream.close();
                        outputStream.close();
                    }
                }
            }else {
                //用户信息
                String authorization = request.getHeader("Authorization");
                //第一次访问没有用户信息 为了获取支持的验证机制
                if (StringUtils.isEmpty(authorization)){
                    //基本的验证机制
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setHeader("WWW-Authenticate", "Basic realm=\"Docker Registry\", Bearer realm=\"Docker Registry\", service=\"registry.example.com\"");
                    response.getWriter().close();
                }else {
                    String basic = authorization.replace("Basic", "").trim();
                    byte[] decode = Base64.getDecoder().decode(basic);
                    //用户信息
                    String userData = new String(decode, "UTF-8");
                    int check = dockerUploadService.userCheck(userData);
                    response.setStatus(check);
                    response.getWriter().close();
                }
            }
        }

        //docker 校验sha256
        if (("HEAD").equals(method)){
            //HEAD类型的请求带有/manifests 为拉取；否则推送
            if (contextPath.contains("/manifests")){
                //获取manifests 镜像校验数据
                Map<String, String> manifests = dockerUploadService.pullManifests(repositoryPath);
                if (("200").equals(manifests.get("code"))){
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/vnd.docker.distribution.manifest.v2+json");
                    response.setHeader("Docker-Content-Digest",manifests.get("data"));
                    response.getWriter().close();
                }
            }else {
                Map<String, String> resultMap = dockerUploadService.v2Sha256Check(repositoryPath);
                if (("200").equals(resultMap.get("code"))){
                    String substring = contextPath.substring(contextPath.indexOf("/") + 1);
                    String fileLength = resultMap.get("data");
                    // 设置响应的状态码和内容类型
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setHeader("Docker-Content-Digest",substring);
                    response.setHeader("Content-Length",fileLength);
                    response.setContentType("application/json");
                    response.getWriter().close();
                }else {
                    // 设置响应的状态码和内容类型
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.setContentType("application/json");
                    // 将 JSON 响应写入响应输出流
                    response.getWriter().write(resultMap.get("data"));
                }
            }
        }

        //docker 创建上传会话
        if (("POST").equals(method)){

            String num8 = UuidGenerator.gen(8);
            String lowerCaseNum = num8.toLowerCase();

            String num4 = UuidGenerator.gen(4);
            String lowerCase = num4.toLowerCase();

            String uploadID = UuidGenerator.gen(8) + "-" + UuidGenerator.gen(4) + "-" + lowerCase + "-" + lowerCaseNum;
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.setHeader("Location", contextPath + uploadID);
            response.setHeader("Docker-Upload-UUID", uploadID);
            response.setHeader("Range", "0-0");
            response.getWriter().close();
        }

        //docker 上传镜像层数据
        if (("PATCH").equals(method)){
            String uploadID = contextPath.substring(contextPath.lastIndexOf("/") + 1);

           dockerUploadService.uploadData(request.getInputStream(), repositoryPath);

            String length = request.getHeader("content-length");

            //镜像文件大小区间
            int i = Integer.valueOf(length) - 1;
            String range="0-"+i;
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.setHeader("Location", contextPath);
            response.setHeader("Docker-Upload-UUID", uploadID);
            response.setHeader("Range", range);
            response.getWriter().close();
        }
        //docker 上传镜像层数据校验
        if (("PUT").equals(method)){

            //路径中存在manifests 为校验 否则为上传数据
            if (contextPath.contains("/manifests")){
                String authorization = request.getHeader("Authorization");
                String tag = dockerUploadService.createTag(request.getInputStream(), repositoryPath, authorization);
                String sha256 = RepositoryUtil.generateSHA256(tag);

                // 设置响应的内容类型和状态码
                response.setContentType("application/vnd.docker.distribution.manifest.v2+json");
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setHeader("Docker-Content-Digest", "sha256:"+sha256);
                response.getWriter().write(tag);

            }else {
                Map<String, String[]> parameterMap = request.getParameterMap();
                String digest = parameterMap.get("digest")[0];
                String file = dockerUploadService.createFile(digest, repositoryPath);

                String range="0-"+file;
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setHeader("Docker-Content-Digest", digest);

                String substring = requestURL.substring(0, requestURL.indexOf("uploads"));
                String location = substring + digest;
                response.setHeader("Location", location);
                response.setHeader("Content-Range", range);
                response.getWriter().close();
            }
        }

    }
}
