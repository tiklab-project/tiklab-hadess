package net.tiklab.xpack.updownload.service;

import java.io.IOException;
import java.io.InputStream;

public interface LibraryMavenMutualService {


    /**
     * maven提交
     * @param contextPath
     * @return
     */
    void mavenSubmit(String contextPath,  InputStream inputStream) throws IOException;


    /**
     * maven拉取
     * @param contextPath
     * @return
     */
    byte[] mavenInstall(String contextPath);
}
