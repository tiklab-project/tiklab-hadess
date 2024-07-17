package io.thoughtware.hadess.upload.model;

import io.thoughtware.postin.annotation.ApiProperty;

import java.io.InputStream;

public class LibraryHelmClient {

    @ApiProperty(name="fileName",desc="上传文件名字")
    private String fileName;

    @ApiProperty(name="repositoryName",desc="仓库名字")
    private String repositoryName;

    @ApiProperty(name="fileSize",desc="文件大小")
    private Long fileSize;

    @ApiProperty(name="inputStream",desc="文件")
    private InputStream inputStream;


    @ApiProperty(name="userName",desc="推送用户名")
    private String userName;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
