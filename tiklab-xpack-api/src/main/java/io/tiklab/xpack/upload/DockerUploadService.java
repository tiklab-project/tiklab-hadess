package io.tiklab.xpack.upload;

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
     * @param pathUrl 路径
     */
    Map<String, String> v2Sha256Check(String pathUrl) throws Exception;

    /**
     * 推送-文件上传
     * @param inputStream 文件流
     */
    void uploadData(InputStream inputStream,String contextPath) throws IOException;

    /**
     * 推送-创建文件
     * @param digest 文件名
     * @param contextPath 客户端请求路径
     */
    String createFile(String digest,String contextPath) throws IOException;

    /**
     * 推送-创建tag（版本）信息
     * @param inputStream 文件流
     * @param  authorization 用户信息
     */
    String createTag(InputStream inputStream, String contextPath,String authorization) throws IOException, NoSuchAlgorithmException;

    /**
     * 拉取-manifests
     * @param contextPath
     */
    Map<String, String> pullManifests(String contextPath);

    /**
     * 拉取-读取manifests、镜像数据
     * @param contextPath
     */
    Map<String, String> readMirroringData(String contextPath) throws IOException;
}
