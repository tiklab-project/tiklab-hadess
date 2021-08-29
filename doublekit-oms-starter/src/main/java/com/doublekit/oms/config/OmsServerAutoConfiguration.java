package com.doublekit.oms.config;

import com.doublekit.apibox.client.annotation.EnableApiboxClient;
import com.doublekit.dal.starter.annotation.EnableDal;
import com.doublekit.dcs.starter.annotation.EnableDcs;
import com.doublekit.dfs.starter.annotation.EnableDfs;
import com.doublekit.dss.starter.annotation.EnableDss;
import com.doublekit.framework.starter.annotation.EnableFramework;
import com.doublekit.iam.annotation.EnableIamServer;
import com.doublekit.member.annotation.EnableMemberServer;
import com.doublekit.privilege.annotation.EnablePrivilegeServer;
import com.doublekit.product.annotation.EnableProductServer;
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
//modules
@EnableUserServer
@EnablePrivilegeServer
@EnableToolkitServer
//---------
@EnableMemberServer
@EnableIamServer
@EnableTenantServer
@EnableProductServer
@EnableTradeServer
@EnableApiboxClient
public class OmsServerAutoConfiguration {
}

