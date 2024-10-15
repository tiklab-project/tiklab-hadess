package io.tiklab.hadess.common;


import io.tiklab.user.user.model.User;

import java.util.List;

public interface UserCheckService {

    /**
     * maven用户校验
     * @param userData    用户数据
     */
    String mavenUserCheck(String userData);

    /**
     * npm用户校验
     * @param userName    账号
     * @param  password  密码
     */
    String npmUserCheck(String userName,String password);

    /**
     * npm通过用户校验
     * @param userName    账号
     */
    List<User> npmUserCheckByName(String userName);

    /**
     * 基础用户校验 docker、helm
     * @param userData    用户数据
     */
    void basicsUserCheck(String userData);

    /**
     * generic 通用类型用户校验
     * @param userData    用户数据
     */
    String genericUserCheck(String userData);

}
