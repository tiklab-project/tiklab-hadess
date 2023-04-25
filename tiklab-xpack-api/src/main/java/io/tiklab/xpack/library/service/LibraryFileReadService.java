package io.tiklab.xpack.library.service;

/**
 * LibraryFileReadService-制品文件读取
 */
public interface LibraryFileReadService {

    /**
     * 文件读取   versionId
     * @param requestURI  访问路径
     */
    byte[] fileRead(String requestURI);
}
