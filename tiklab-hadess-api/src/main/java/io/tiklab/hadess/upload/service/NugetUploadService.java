package io.tiklab.hadess.upload.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface NugetUploadService {
    /**
     * 上传制品数据（通过了索引json格式校验才会执行这第二步）
     * @param request  request
     * @param  response response
     */
    void uploadData(HttpServletRequest request, HttpServletResponse response);


    /**
     * 下载制品数据 。 (注意：上传的时候也会先调用get请求，获取索引json格式文件)
     * @param request  request
     * @param  response response
     */
    void downloadData(HttpServletRequest request, HttpServletResponse response);


}
