package io.thoughtware.hadess.upload;

import io.thoughtware.hadess.library.model.LibraryFileHand;

import java.io.IOException;
import java.io.InputStream;

/**
 * LibraryFileReadService-制品文件读取
 */
public interface HandUploadService {

    /**
     * 文件读取   versionId
     * @param requestURI  访问路径
     */
    byte[] fileRead(String requestURI);

    /**
     * 获取服务器ip
     */
    String findServerIp();

    /**
     * 文件上传
     */
    String fileUpload(InputStream inputStream, String fileName) throws IOException;

    /**
     * 手动上传制品
     */
    String libraryHandPush(LibraryFileHand libraryFileHand) ;

    /**
     * 获取手动上传结果
     */
    String findHandPushResult(String repositoryId);

}
