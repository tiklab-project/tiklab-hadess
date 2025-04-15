package io.tiklab.hadess.upload.service;

import io.tiklab.core.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface DockerUploadService {
    /**
     * 用户校验
     * @param authorization 用户信息
     */
    Result<String> userCheck(String authorization);

    /**
     * 推送-校验Sha256 。存在则直接返回文件大小
     * @param repositoryPath 路径
     */
    Result v2Sha256Check(String repositoryPath) throws Exception;

    /**
     * 推送-文件上传
     * @param inputStream 文件流
     */
    Result uploadData(InputStream inputStream, String repositoryPath) throws Exception;

    /**
     * 推送-创建会话 判断制品库是否存在
     * @param repositoryPath repositoryPath
     */
    Result createSession(String repositoryPath);

    /**
     * 推送-创建文件
     * @param digest 文件名
     * @param repositoryPath 客户端请求路径
     */
    String createFile(String digest,String repositoryPath) throws IOException;

    /**
     * 推送-创建Manifests、tag（版本）信息
     * @param inputStream 文件流
     * @param  authorization 用户信息
     */
    String createManifests(InputStream inputStream, String repositoryPath,String authorization) throws IOException, NoSuchAlgorithmException;


    /**
     * 拉取docker-获取manifests镜像校验数据
     * @param repositoryPath
     */
    Map<String, String> downloadManifests(String repositoryPath,String userAgent);


    /**
     * 拉取docker-读取manifests、blobs镜像数据。这一步是已经校验了manifests数据后执行
     * @param repositoryPath  客户端请求路径
     */
    void downloadMirroringData(HttpServletResponse response,String repositoryPath) throws IOException;


}
