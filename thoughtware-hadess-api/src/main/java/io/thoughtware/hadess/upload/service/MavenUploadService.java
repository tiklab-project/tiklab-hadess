package io.thoughtware.hadess.upload.service;

import io.thoughtware.core.Result;
import io.thoughtware.hadess.repository.model.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface MavenUploadService {

    /**
     * maven提交
     * @param contextPath 路径
     * @param inputStream 文件liu
     * @param userData 用户信息
     * @return
     */
    Result<byte[]> mavenSubmit(String contextPath, InputStream inputStream, String userData);


    /**
     * maven提交 写入文件
     * @param inputStream    流文件
     * @param userName       推送人
     * @param contextPath    客户端请求路径
     * @return
     */
     Result fileWriteData(InputStream inputStream, String userName,String contextPath ) throws IOException;

    /**
     * maven拉取
     * @param contextPath 客户端拉取文件的地址
     * @return
     */
    Result<byte[]> mavenPull(String contextPath);


    /**
     * 写入文件
     * @param inputStream 客户端拉取文件的地址
     * @param  dataMap dataMap
     */
     Result writeFile(InputStream inputStream, Map<String, String> dataMap) throws IOException;


    /**
     *  拉取远程后创建制品信息
     * @param  dataMap 数据整合的map
     * @param  repository 远程库
     * @param  entityBody   entityBody
     */
     void pullCreateLibrary(Map<String, String> dataMap, Repository repository, byte[] entityBody);


}
