package io.thoughtware.hadess.upload.model;

import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.io.BufferedReader;
import java.io.InputStream;

/*
* 制品上传的数据
* */
@ApiModel
public class LibraryUploadData {

    @ApiProperty(name="userName",desc="用户名字")
    private String userName;

    @ApiProperty(name="authorization",desc="认证信息")
    private String authorization;

    @ApiProperty(name="inputStream",desc="推送的数据")
    private InputStream inputStream ;

    @ApiProperty(name="relativePath",desc="相对路径 例：仓库名/请求文件")
    private String relativePath ;

    @ApiProperty(name="absolutePath",desc="绝对路径 例：http：//")
    private String absolutePath ;

    @ApiProperty(name="method",desc="请求方法 PUT POST")
    private String method;

    @ApiProperty(name="agentType",desc="请求类型 yarn、npm")
    private String agentType;

    @ApiProperty(name="repository",desc="仓库")
    private Repository repository;

    @ApiProperty(name="referer",desc="npm请求类型专用 请求的方式 推送：publish、登陆：adduser、拉取：install ")
    private String referer;

    @ApiProperty(name="userReader",desc="npm请求类型专用 登陆用户信息")
    private BufferedReader userReader;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public BufferedReader getUserReader() {
        return userReader;
    }

    public void setUserReader(BufferedReader userReader) {
        this.userReader = userReader;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
