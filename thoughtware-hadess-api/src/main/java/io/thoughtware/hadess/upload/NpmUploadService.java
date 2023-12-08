package io.thoughtware.hadess.upload;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * NpmUploadService-npm上传下载接口
 */
public interface NpmUploadService {

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
    Map<String,String> npmPull(String contextPath);

    /**
     * npm 拉取 （第二次交互）
     * @param contextPath
     * @return
     */
   Map<String,Object>  npmPullTgzData(String contextPath);

    /**
     * npm 登陆
     * @param reader
     * @return
     */
    Map npmLogin(BufferedReader reader);


    /**
     * npm提交-读取文件
     * @param path
     * @param allData
     * @return
     */
     Integer npmSubmitData(String path, JSONObject allData) throws IOException;


    /**
     * npm提交-读取文件
     * @param inputStream  输入流
     * @return
     */
     JSONObject readData(InputStream inputStream) throws IOException;
}
