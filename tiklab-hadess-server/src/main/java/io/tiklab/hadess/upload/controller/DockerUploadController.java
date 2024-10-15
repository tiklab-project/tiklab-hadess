package io.tiklab.hadess.upload.controller;

import io.tiklab.core.Result;
import io.tiklab.hadess.upload.common.response.DockerResponse;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.upload.service.DockerUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        //客户端的get请求
        if (("GET").equals(method)){
            //请求路径中存在sha256 代表pull拉取 请求
            if (contextPath.contains("/sha256:")){
                Map<String, String> resultMap = dockerUploadService.readMirroringData(repositoryPath);
                DockerResponse.dockerReadMirroring(contextPath,resultMap,response);
            }else {
                //EAD请求拉取-manifests返回404，执行该get请求校验Manifests数据
                if (contextPath.contains("/manifests")){
                    Map<String, String> manifests = dockerUploadService.pullManifests(repositoryPath);
                    DockerResponse.dockerPullManifests(manifests,"GET",response);

                }else {
                    //登陆、推送。校验用户信息
                    String authorization = request.getHeader("Authorization");
                    Result<String> userCheck = dockerUploadService.userCheck(authorization);
                    DockerResponse.dockerAccountVerify(userCheck,response);
                }
            }
        }

        //docker 校验sha256
        if (("HEAD").equals(method)){
            //HEAD类型的请求带有/manifests 为拉取；否则推送
            if (contextPath.contains("/manifests")){
                //获取manifests 镜像校验数据
                Map<String, String> manifests = dockerUploadService.pullManifests(repositoryPath);
                response.setContentType("application/vnd.docker.distribution.manifest.v2+json");
                DockerResponse.dockerPullManifests(manifests,"HEAD",response);
            }else {
                //校验Sha256
                Result result = dockerUploadService.v2Sha256Check(repositoryPath);
                DockerResponse.DockerSha25Verify(result,contextPath,response);
            }
        }

        //docker 创建上传会话
        if (("POST").equals(method)){
            Result result = dockerUploadService.createSession(repositoryPath);
            DockerResponse.DockerCreateSession(result,contextPath,response);
        }

        //docker 上传镜像层数据
        if (("PATCH").equals(method)){
            Result result = dockerUploadService.uploadData(request.getInputStream(), repositoryPath);
            DockerResponse.DockerUploadBlobs(result,contextPath,request,response);
        }
        //docker 上传镜像层数据校验
        if (("PUT").equals(method)){
            //路径中存在manifests为校验 ;否则为上传数据
            if (contextPath.contains("/manifests")){
                String authorization = request.getHeader("Authorization");
                String tag = dockerUploadService.createTag(request.getInputStream(), repositoryPath, authorization);
                DockerResponse.dockerCreateTag(tag,response);

            }else {
                Map<String, String[]> parameterMap = request.getParameterMap();
                String digest = parameterMap.get("digest")[0];
                String file = dockerUploadService.createFile(digest, repositoryPath);
                DockerResponse.dockerCreateFile(file,requestURL,digest,response);
            }
        }

    }
}
