package net.tiklab.xpack.library.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Map;

public interface DownloadNpmService {

    /**
     * npm publish 提交
     * @param contextPath
     * @return
     */
    Integer npmSubmit(String contextPath, InputStream inputStream);

    /**
     * npm 拉取 （第一次交互）
     * @param contextPath
     * @return
     */
    Object npmPull(String contextPath);

    /**
     * npm 拉取 （第二次交互）
     * @param contextPath
     * @return
     */
    byte[] npmPullTgzData(String contextPath);

    /**
     * npm 登陆
     * @param reader
     * @return
     */
    Map npmLogin(BufferedReader reader);
}
