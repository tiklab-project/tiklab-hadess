package io.thoughtware.hadess.common;

public class HadessFinal {


    // 日志类型
    public static final String LOG_TYPE_CREATE = "HDS_CREATE";
    public static final String LOG_TYPE_UPDATE = "HDS_UPDATE";
    public static final String LOG_TYPE_DELETE = "HDS_DELETE";



    //仓库创建
    public static final String LOG_RPY_CREATE = "/repository/${repositoryId}/setting/repositoryInfo";
    //仓库删除
    public static final String LOG_RPY_DELETE = "/repository";
    //仓库修改
    public static final String LOG_RPY_UPDATE = "/repository/${repositoryId}/setting/repositoryInfo";


    /**
     * DEFAULT
     */
    public static final String DEFAULT = "default";


    //npm中央仓库账号
    public static final String NPM_CENTER_ACCOUNT = "tiklab";

    //npm中央仓库密码
    public static final String NPM_CENTER_PASSWORD = "Darth2020...";

    //maven中央快照版本地址
    public static final String MAVEN_CENTER_SAN_PATH = "https://s01.oss.sonatype.org/content/repositories/snapshots";
    //maven中央正式版本地址
    public static final String MAVEN_CENTER_REL_PATH = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/";
}
