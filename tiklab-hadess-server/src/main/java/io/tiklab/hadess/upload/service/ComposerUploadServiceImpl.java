package io.tiklab.hadess.upload.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.tiklab.core.Result;
import io.tiklab.hadess.common.RepositoryUtil;
import io.tiklab.hadess.common.UserCheckService;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.library.model.Library;
import io.tiklab.hadess.library.model.LibraryPypi;
import io.tiklab.hadess.library.model.LibraryPypiQuery;
import io.tiklab.hadess.library.service.LibraryFileService;
import io.tiklab.hadess.library.service.LibraryPypiService;
import io.tiklab.hadess.library.service.LibraryService;
import io.tiklab.hadess.library.service.LibraryVersionService;
import io.tiklab.hadess.repository.model.Repository;
import io.tiklab.hadess.repository.model.RepositoryGroup;
import io.tiklab.hadess.repository.model.RepositoryGroupQuery;
import io.tiklab.hadess.repository.model.RepositoryRemoteProxy;
import io.tiklab.hadess.repository.service.RepositoryGroupService;
import io.tiklab.hadess.repository.service.RepositoryRemoteProxyService;
import io.tiklab.hadess.repository.service.RepositoryService;
import io.tiklab.hadess.upload.common.response.ComposerResponse;
import io.tiklab.hadess.upload.common.response.PypiResponse;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

@Service
@Exporter
public class ComposerUploadServiceImpl implements ComposerUploadService{
    private static Logger logger = LoggerFactory.getLogger(ComposerUploadServiceImpl.class);

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Autowired
    RepositoryService repositoryService;


    @Autowired
    RepositoryRemoteProxyService remoteProxyService;

    @Autowired
    RepositoryGroupService groupService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryPypiService libraryPypiService;

    @Autowired
    LibraryVersionService versionService;

    @Autowired
    LibraryFileService libraryFileService;


    @Autowired
    UserCheckService userCheckService;

    @Override
    public void downloadPackage(HttpServletRequest request, HttpServletResponse response) {

        String pathData = yamlDataMaService.getUploadRepositoryUrl(request.getRequestURI(),"composer");

        try {
            downloadMetadataIndex(response,pathData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void uploadPackage(HttpServletRequest request, HttpServletResponse response) {
        //用户信息
        String authorization = request.getHeader("Authorization");
        try {
            if (StringUtils.isEmpty(authorization)){
                response.getWriter().print("{code:401,msg:用户信息不存在}");
                return;
            }
            //校验用户信息
            Result<String> userCheckResult = userCheck(authorization);
            if (userCheckResult.getCode()==401){
                ComposerResponse.errorToClient(response,401,userCheckResult.getMsg());
                return;
            }




        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    /**
     * 获取所有包的元数据索引
     * @param pathData 客户端请求路径
     */
    public void downloadMetadataIndex(HttpServletResponse response,String pathData) throws Exception {
        //仓库名称
        String repName = StringUtils.substringBefore(pathData, "/");

        String fileName = StringUtils.substringAfterLast(pathData, repName+"/");

        Repository repository = repositoryService.findRepository(repName, "composer");
        if (ObjectUtils.isEmpty(repository)){
            logger.info("composer下载，配置的仓库不存在");
        }
        //客户端请求拉取元数据仓库地址为远程库
        if (("remote").equals(repository.getRepositoryType())){
            String data = downloadMetadataByRemoteRep(repository, fileName);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/json");
            PrintWriter  writer = response.getWriter();
            writer.write(data);
        }
    }

    public String downloadMetadataByRemoteRep(Repository repository,String fileName) throws JsonProcessingException {
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRepId(repository.getId());
        if (CollectionUtils.isEmpty(remoteProxyList)){
            logger.info("composer下载，仓库没有配置代理代理");
        }
        String jsonString = metadataForwardingRemote(remoteProxyList, fileName);

        // 创建 JSONObject。将metadata-url名字添加请求的仓库名字
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String metadataUrl = jsonObject.getString("metadata-url");
        String substringAfter = StringUtils.substringAfter(metadataUrl, "/composer/");
        String newMetadataUrl = "/composer/" + repository.getName() + "/"+substringAfter;
        jsonObject.put("metadata-url",newMetadataUrl);

        String string = jsonObject.toJSONString();
         return string;
    }



    /**
     * 下载元数据数据->转发远程
     * @param remoteProxyList  代理地址
     */
    public String metadataForwardingRemote(List<RepositoryRemoteProxy> remoteProxyList,String fileName){

        for (RepositoryRemoteProxy remoteProxy:remoteProxyList){
            String agencyUrl = remoteProxy.getRemoteProxy().getAgencyUrl();
            try {
                if (!agencyUrl.endsWith("/")){
                    agencyUrl=agencyUrl+"/";
                }
                String path= agencyUrl + fileName;
                String data = restTemplateGet(path);
                return data;
            }catch (Exception e){
                logger.info("composer下载，地址："+agencyUrl);
                e.printStackTrace();
                continue;
            }
        }
        return "500-调用代理地拉取失败";
    }


    /**
     * 校验用户信息
     * @param authorization  客户端上传的用户信息
     */
    public Result<String> userCheck(String authorization) {

        //docker第一次访问没有用户信息 为了获取支持的验证机制
        if (ObjectUtils.isEmpty(authorization)){
            logger.info("pypi拉取推送没有用户信息");
            return Result.error(401,"用户信息不存在");
        }
        try {
            String basic = authorization.replace("Basic", "").trim();
            byte[] decode = Base64.getDecoder().decode(basic);
            //用户信息
            String userData = new String(decode, "UTF-8");
            userCheckService.basicsUserCheck(userData);
            String[] split = userData.split(":");
            String userName = split[0];
            return Result.ok(userName);
        }catch (Exception e){
            logger.info("docker登陆错误："+e.getMessage());
            return Result.error(401,e.getMessage());
        }
    }

    /**
     * 执行get请求
     * @param relativeAbsoluteUrl 地址
     */
    public String restTemplateGet(String relativeAbsoluteUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders newHeaders = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(relativeAbsoluteUrl, HttpMethod.GET,
                new HttpEntity<>(newHeaders), String.class);

        String jsonString = exchange.getBody();
        return jsonString;
    }
}
