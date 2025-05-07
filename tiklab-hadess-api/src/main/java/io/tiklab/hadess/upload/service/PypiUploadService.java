package io.tiklab.hadess.upload.service;

import io.tiklab.core.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PypiUploadService {

    /**
     * 下载元数据Json,代理的制品元数据存在版本的更新，所以每次转发了代理去拉取制品的元数据
     * @param request  request
     */
    void downloadMetadata(HttpServletRequest request,HttpServletResponse response);

    /**
     * 下载包数据，版本在hadess服务器中存在就直接下载，不存在转发代理拉取
     * @param contextPath  客户端请求路径
     */
    void downloadPackages(String contextPath,HttpServletResponse response);


    /**
     * 上传
     * @param req  客户端上传Request
     */
    void UploadData(HttpServletRequest req,HttpServletResponse resp);
}
