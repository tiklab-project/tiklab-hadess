package io.thoughtware.hadess.upload.model;

import io.thoughtware.postin.annotation.ApiProperty;

public class LibraryUploadResult {

    @ApiProperty(name="status",desc="结果code")
    private int status;


    @ApiProperty(name="data",desc="返回的结果")
    private String data;

    @ApiProperty(name="details",desc="返回内容")
    private byte[] details;

    @ApiProperty(name="msg",desc="返回的消息")
    private String msg;

    @ApiProperty(name="contentType",desc="结果类型")
    private String contentType;


    @ApiProperty(name="resultType",desc="结果类型")
    private String resultType;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public byte[] getDetails() {
        return details;
    }

    public void setDetails(byte[] details) {
        this.details = details;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
