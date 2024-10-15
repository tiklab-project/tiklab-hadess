package io.tiklab.hadess.upload.service;

import io.tiklab.core.Result;

public interface GoUploadService {

    /**
     * 制品拉取
     * @param pathData 请求路径（仓库+包路径）
     */
    Result<byte[]> pullData(String pathData);
}
