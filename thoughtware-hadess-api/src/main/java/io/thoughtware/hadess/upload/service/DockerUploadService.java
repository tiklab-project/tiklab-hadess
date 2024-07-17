package io.thoughtware.hadess.upload.service;

import io.thoughtware.core.Result;

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
    Result uploadData(InputStream inputStream, String repositoryPath) throws IOException;

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
     * 推送-创建tag（版本）信息
     * @param inputStream 文件流
     * @param  authorization 用户信息
     */
    String createTag(InputStream inputStream, String repositoryPath,String authorization) throws IOException, NoSuchAlgorithmException;

    /**
     * 拉取-manifests
     * @param repositoryPath
     */
    Map<String, String> pullManifests(String repositoryPath);

    /**
     * HEAD请求拉取-manifests返回404，执行该get请求校验Manifests数据
     * @param repositoryPath
     */
    String  verifyManifests(String repositoryPath);

    /**
     * 拉取-读取manifests、镜像数据
     * @param repositoryPath
     */
    Map<String, String> readMirroringData(String repositoryPath) throws IOException;


}
