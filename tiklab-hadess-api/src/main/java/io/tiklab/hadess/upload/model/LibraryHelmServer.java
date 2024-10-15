package io.tiklab.hadess.upload.model;

import io.tiklab.core.BaseModel;

import java.util.Map;

public class LibraryHelmServer extends BaseModel {



    //sha256
    private String digest;

    //helm 的Chart名字
    private String name;

    private String urls;

    //helm包版本
    private String version;


    //临时文件存储的路径
    private String scratchFilePath;

    //chart.yaml内容
    private Map<String, Object> data;

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getScratchFilePath() {
        return scratchFilePath;
    }

    public void setScratchFilePath(String scratchFilePath) {
        this.scratchFilePath = scratchFilePath;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
