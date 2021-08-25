package com.doublekit.oms.config;

import com.doublekit.apibox.client.annotation.EnableApiboxClient;
import com.doublekit.dal.starter.annotation.EnableDal;
import com.doublekit.datafly.starter.annotation.EnableDataFly;
import com.doublekit.dcs.starter.annotation.EnableDcs;
import com.doublekit.dfs.starter.annotation.EnableDfs;
import com.doublekit.dss.starter.annotation.EnableDss;
import com.doublekit.eam.server.annotation.EnableEamServer;
import com.doublekit.framework.starter.annotation.EnableFramework;
import com.doublekit.iam.server.annotation.EnableIamServer;
import com.doublekit.member.annotation.EnableMemberServer;
import com.doublekit.privilege.annotation.EnablePrivilegeServer;
import com.doublekit.product.annotation.EnableProductServer;
import com.doublekit.rpc.starter.annotation.EnableRpcClient;
import com.doublekit.tenant.annotation.EnableTenantServer;
import com.doublekit.toolkit.annotation.EnableToolkitServer;
import com.doublekit.trade.annotation.EnableTradeServer;
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
@EnableDataFly
//modules
@EnableUserServer
@EnablePrivilegeServer
@EnableToolkitServer
@EnableEamServer
//---------
@EnableMemberServer
@EnableIamServer
@EnableTenantServer
@EnableProductServer
@EnableTradeServer
@EnableApiboxClient
public class OmsServerAutoConfiguration {
}

