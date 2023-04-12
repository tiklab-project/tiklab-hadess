package io.tiklab.xpack.library.service;

import io.tiklab.core.Result;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * MavenUploadService-maven上传下载接口
 */
public interface MavenUploadService {


    /**
     * maven提交
     * @param contextPath 路径
     * @param inputStream 文件liu
     * @param userData 用户信息
     * @param method 方式
     * @return
     */
    Result mavenSubmit(String contextPath,  InputStream inputStream,String userData, String method) ;


    /**
     * maven拉取
     * @param contextPath 客户端拉取文件的地址
     * @return
     */
    Result mavenInstall(String contextPath);


}
