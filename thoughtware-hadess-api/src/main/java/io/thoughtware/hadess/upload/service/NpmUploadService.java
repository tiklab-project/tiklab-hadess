package io.thoughtware.hadess.upload.service;

import io.thoughtware.hadess.upload.model.LibraryUploadData;
import io.thoughtware.hadess.upload.model.LibraryUploadResult;
import io.thoughtware.hadess.upload.model.NpmPubData;

import java.io.IOException;

/**
 * NpmUploadService-npm上传下载接口
 */
public interface NpmUploadService {



    void createAndWritePullLibrary(NpmPubData npmPubData,String fileTgzName) throws IOException;

    /**
     * 创建拉取的制品
     * @param npmPubData  创建相关数据
     * @param  fileName 文件名字
     * @return
     */
     void createPullLibrary(NpmPubData npmPubData, String fileName);


    /**
     * npm 上传的入口
     * @param libraryUploadData  上传数据
     */
    LibraryUploadResult uploadEntrance(LibraryUploadData libraryUploadData);
}
