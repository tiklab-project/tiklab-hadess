package io.thoughtware.hadess.common;

public interface XpackYamlDataMaService {

    /**
     * 仓库备份上传地址
     */
    String uploadAddress();

    /**
     *jdbc host
     */
    String host();

    /**
     *jdbc dbName
     */
    String dbName();

    /**
     *jdbc schemaName
     */
    String schemaName();


    /**
     * 仓库地址
     */
    String repositoryAddress();

    /**
     * 项目端口
     */
    String serverPort();

    /**
     * 文件地址
     */
     String fileAddress();

    /**
     * 扫描文件地址
     */
    String scanFileAddress();

    /**
     * pgsql的地址
     */
    String pgSqlAddress();


    /**
     * 获取上传的制品的路径
     */
     String getUploadRepositoryUrl(String requestURL);

    /**
     * 获取openscan执行路径
     */
    String getOpenScanUrl();

    /**
     * 获取openscan的本地漏洞路径
     */
    String getLocalHoleUrl();
}
