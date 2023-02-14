package net.tiklab.xpack.updownload.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface LibraryMavenMutualService {


    /**
     * maven提交
     * @param contextPath 路径
     * @param inputStream 文件liu
     * @param userData 用户信息
     * @param method 方式
     * @return
     */
    Map mavenSubmit(String contextPath,  InputStream inputStream,String userData, String method) throws IOException;


    /**
     * maven拉取
     * @param contextPath
     * @return
     */
    Map mavenInstall(String contextPath);
}
