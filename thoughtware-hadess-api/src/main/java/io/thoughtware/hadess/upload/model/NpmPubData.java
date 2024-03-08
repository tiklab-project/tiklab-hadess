package io.thoughtware.hadess.upload.model;

import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
public class NpmPubData {

    @ApiProperty(name="rpyName",desc="仓库名字")
    private String rpyName;

    @ApiProperty(name="fileName",desc="文件名字")
    private String fileName;

    @ApiProperty(name="libraryName",desc="制品名字")
    private String libraryName;

    @ApiProperty(name="repository",desc="仓库")
    private Repository repository;

    @ApiProperty(name="version",desc="版本")
    private String version;


    @ApiProperty(name="storagePath",desc="文件存储位置")
    private String storagePath;

    @ApiProperty(name="relativePath",desc="文件存储相对路径")
    private String relativePath;

    @ApiProperty(name="fileSize",desc="文件大小")
    private Long fileSize;

    @ApiProperty(name="hash",desc="hash")
    private String hash;

    @ApiProperty(name="userName",desc="用户名")
    private String userName;

    @ApiProperty(name="tgzData",desc="tgz内容")
    private byte[] tgzData;

    @ApiProperty(name="jsonData",desc="json内容")
    private String jsonData;

    @ApiProperty(name="requestFullURL",desc="请求全路径")
    private String requestFullURL;

    @ApiProperty(name="repositoryList",desc="仓库list")
    private List<Repository> repositoryList;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public byte[] getTgzData() {
        return tgzData;
    }

    public void setTgzData(byte[] tgzData) {
        this.tgzData = tgzData;
    }

    public String getRpyName() {
        return rpyName;
    }

    public void setRpyName(String rpyName) {
        this.rpyName = rpyName;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestFullURL() {
        return requestFullURL;
    }

    public void setRequestFullURL(String requestFullURL) {
        this.requestFullURL = requestFullURL;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public List<Repository> getRepositoryList() {
        return repositoryList;
    }

    public void setRepositoryList(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
