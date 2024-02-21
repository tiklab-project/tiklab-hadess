package io.thoughtware.hadess.upload.service;

import io.thoughtware.core.Result;

import java.io.InputStream;

public interface GenericUploadService {


    /**
     * generic提交
     * @param inputStream 文件liu
     * @param   repositoryPath 仓库名称/制品文件
     * @param userData 用户信息
     * @param version 产品版本
     * @return
     */
     String GenericUpload( InputStream inputStream,String repositoryPath,String userData,String version );

    /**
     * generic下载
     * @param   repositoryPath 仓库名称/制品文件
     * @param userData 用户信息
     * @param version 产品版本
     * @return
     */
    Result<byte[]> GenericDownload(String repositoryPath, String userData, String version);
}
