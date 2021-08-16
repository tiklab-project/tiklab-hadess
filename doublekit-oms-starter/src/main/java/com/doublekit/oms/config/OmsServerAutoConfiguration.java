package com.doublekit.oms.config;

import com.doublekit.apibox.client.annotation.EnableApiboxClient;
import com.doublekit.auth.annotation.EnableAuthClient;
import com.doublekit.auth.annotation.EnableAuthServer;
import com.doublekit.dal.starter.annotation.EnableDal;
import com.doublekit.datafly.starter.annotation.EnableDatafly;
import com.doublekit.dcs.starter.annotation.EnableDcs;
import com.doublekit.dfs.starter.annotation.EnableDfs;
import com.doublekit.dss.starter.annotation.EnableDss;
import com.doublekit.framework.starter.annotation.EnableFramework;
import com.doublekit.message.annotation.EnableMessageServer;
import com.doublekit.privilege.annotation.EnablePrivilegeServer;
import com.doublekit.rpc.starter.annotation.EnableRpcClient;
import com.doublekit.toolkit.annotation.EnableToolkitServer;
import com.doublekit.user.annotation.EnableUserServer;
import com.doublekit.web.starter.annotation.EnableWeb;
import org.springframework.context.annotation.Configuration;

/**
 * OmsServerAutoConfiguration
 */
@Configuration
//common
@EnableFramework
@EnableWeb
@EnableDal
@EnableDfs
@EnableDcs
@EnableDss
@EnableRpcClient
@EnableDatafly
//modules
@EnableUserServer
@EnableAuthServer
@EnableAuthClient
@EnablePrivilegeServer
@EnableToolkitServer
@EnableApiboxClient
public class OmsServerAutoConfiguration {
}

