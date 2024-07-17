package io.thoughtware.hadess.upload.service;

import io.thoughtware.core.Result;
import io.thoughtware.hadess.upload.model.LibraryHelmClient;

public interface HelmUploadService {

    /**
     * 用户校验
     * @param authorization 认证信息
     */
    Result<String> userCheck(String authorization);

    /**
     * 连接仓库
     * @param authorization 认证信息
     */
    Result<byte[]> connectWarehouse(String authorization,String pathData);

    /**
     * 数据上传
     * @param helmClient helmClient
     */
    Result<String> uploadData(LibraryHelmClient helmClient);

    /**
     * 制品拉取
     * @param pathData 请求路径（仓库+包路径）
     */
    Result<byte[]> pullData(String pathData);
}
