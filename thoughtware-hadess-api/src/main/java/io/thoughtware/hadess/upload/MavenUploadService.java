package io.thoughtware.hadess.upload;

import io.thoughtware.core.Result;

import java.io.IOException;
import java.io.InputStream;

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


}
