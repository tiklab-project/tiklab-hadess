/*
package io.thoughtware.hadess.upload.upold;

import com.alibaba.fastjson.JSONObject;
import io.thoughtware.core.Result;
import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.hadess.upload.model.NpmPubData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

*/
/**
 * NpmUploadService-npm上传下载接口
 *//*



public interface NpmUploadService {

    */
/**
     * npm publish 提交
     * @param contextPath
     * @return
     *//*

    Integer npmSubmit(String contextPath, InputStream inputStream);

    */
/**
     * npm 拉取 （第一次交互） 如果客户端的.npm 文件夹下面npm文件已经存在 就只执行这次交互（拉取信息）
     * 第一次请求是不带版本的、
     * @param repository 请求的制品库
     * @param requestFullURL 请求全路径
     *//*

    Result<String> npmPullJson(Repository repository, String requestFullURL);

    */
/**
     * npm 拉取 （第二次交互） 客户端的.npm 文件夹下面npm文件不存在（拉取版本文件不存在）
     * 就根据第一次返回的json里面的tarball的value发起第二次请求（拉取文件信息）
     * 拉取tgz数据的仓库地址是根据第一次请求json里面 的地址发起的
     * @param repository
     * @param requestFullURL
     *//*

    Result<byte[]>  npmPullTgzData(Repository repository,String requestFullURL);

    */
/**
     * npm 登陆
     * @param reader
     * @return
     *//*

    Map npmLogin(BufferedReader reader);


    */
/**
     * npm提交-读取文件
     * @param path  路径
     * @param allData 数据
     * @param  userName 提交人
     * @return
     *//*

    */
/*Integer npmSubmitData(String path, JSONObject allData,String userName) throws IOException;*//*



    */
/**
     * npm提交-读取文件
     * @param inputStream  输入流
     * @return
     *//*

    JSONObject readData(InputStream inputStream) throws IOException;

    void createAndWritePullLibrary(NpmPubData npmPubData,String fileTgzName) throws IOException;

    */
/**
     * 创建拉取的制品
     * @param npmPubData  创建相关数据
     * @param  fileName 文件名字
     * @return
     *//*

     void createPullLibrary(NpmPubData npmPubData, String fileName);
}
*/
