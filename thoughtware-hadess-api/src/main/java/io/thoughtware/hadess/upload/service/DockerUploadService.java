package io.thoughtware.hadess.upload.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface DockerUploadService {
    /**
     * 用户校验
     * @param userData 用户信息
     */
    int userCheck(String userData);

    /**
     * 推送-校验Sha256
     * @param repositoryPath 路径
     */
    Map<String, String> v2Sha256Check(String repositoryPath) throws Exception;

    /**
     * 推送-文件上传
     * @param inputStream 文件流
     */
    void uploadData(InputStream inputStream,String repositoryPath) throws IOException;

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
     * 拉取-读取manifests、镜像数据
     * @param repositoryPath
     */
    Map<String, String> readMirroringData(String repositoryPath) throws IOException;
}
