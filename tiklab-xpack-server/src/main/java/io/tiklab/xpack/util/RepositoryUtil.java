package io.tiklab.xpack.util;

import io.tiklab.core.context.AppHomeContext;

public class RepositoryUtil {

    /**
     * 获取默认仓库地址
     * @param
     * @return 仓库地址
     */
    public static String findRepositoryUrl(){
        String appHome = AppHomeContext.getAppHome()+"/xpack";
        return appHome;
    }
}
