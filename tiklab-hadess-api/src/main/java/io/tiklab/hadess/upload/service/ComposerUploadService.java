package io.tiklab.hadess.upload.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ComposerUploadService {

    /**
     * composer拉取->拉取json数据。根包信息packages.json、特定包的元数据
     * @param request request
     * @param  response response
     */
    void downloadJsonData(HttpServletRequest request, HttpServletResponse response);

    /**
     * composer拉取->拉取json数据，客户端接收的路径发起请求下载包文件，请求的仓库只会是local、remote库
     * 如果客户端存在了包，执行拉取的时候只会执行 <>拉取json数据</>,不会执行该接口
     * @param request request
     * @param  response response
     */
    void downloadPackage(HttpServletRequest request, HttpServletResponse response);

    /**
     * composer上传
     * @param request request
     * @param  response response
     */
    void uploadPackage(HttpServletRequest request, HttpServletResponse response);
}
