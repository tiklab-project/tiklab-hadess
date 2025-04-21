package io.tiklab.hadess.common;

public class HadessFinal {


    // 日志消息类型
    public static final String LOG_TYPE_CREATE = "HDS_CREATE";
    public static final String LOG_TYPE_UPDATE = "HDS_UPDATE";
    public static final String LOG_TYPE_DELETE = "HDS_DELETE";



    //仓库创建
    public static final String LOG_RPY_CREATE = "/repository/${repositoryId}/setting/info";
    //仓库删除
    public static final String LOG_RPY_DELETE = "/repository";
    //仓库修改
    public static final String LOG_RPY_UPDATE = "/repository/${repositoryId}/setting/info";


    public static final String SCAN_MESSAGE_TYPE = "SCAN_RESULT";
    //制品扫码路径
    public static final String SCAN_RESULT_PATH = "/repository/${repositoryId}/scanDetails/${scanRecordId}";


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



    //hadess中央地址
    public static final String HADESS_PATH = "https://mirror.tiklab.net/";

    //docker-获取token
    public static final String DOCKER_TOKEN = "https://auth.docker.io/token";



    //没有查询到
    public static final Integer NOT_FOUNT_EXCEPTION = 57404;

    //默认异常
    public static final Integer SYSTEM_EXCEPTION = 57100;

    //文件异常
    public static final Integer FILE_EXCEPTION = 57101;

    //时间异常
    public static final Integer TIME_EXCEPTION = 57102;


    //写入数据失败
    public static final Integer WRITE_EXCEPTION = 57103;

    //读取远程文件失败
    public static final Integer READ_REMOTE_EXCEPTION = 57104;

    //读取本地文件失败
    public static final Integer READ_LOCAL_EXCEPTION = 571045;



     /** docker  * */

    //manifest 的类型
    public static final String MEDIA_TYPE_V2 ="application/vnd.docker.distribution.manifest.v2+json";

    // 镜像配置的格式的类型
    public static final String MEDIA_TYPE_CONFIG_V1 ="application/vnd.docker.container.image.v1+json";

    // 镜像清单类型
    public static final String MEDIA_TYPE_LAYER_GZIP ="application/vnd.docker.image.rootfs.diff.tar.gzip";
    public static final String MEDIA_TYPE_LAYER_TAR ="application/vnd.docker.image.rootfs.diff.tar";



    /** composer  * */

    //manifest 的类型

    public static final String COMPOSER_PACKAGES_LIST ="https://packagist.org/packages/list.json";

    public static final String COMPOSER_METADATA_CHANGES ="https://packagist.org/metadata/changes.json";
}
