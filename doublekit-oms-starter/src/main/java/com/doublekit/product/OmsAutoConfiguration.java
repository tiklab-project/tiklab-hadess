package com.doublekit.product;

import com.doublekit.apibox.client.EnableApiboxClient;
import com.doublekit.beans.starter.annotation.EnableBeans;
import com.doublekit.dal.starter.annotation.EnableDal;
import com.doublekit.datafly.starter.annotation.EnableDataFly;
import com.doublekit.dcs.starter.annotation.EnableDcs;
import com.doublekit.dfs.starter.annotation.EnableDfs;
import com.doublekit.doc.annotation.EnableDocServer;
import com.doublekit.dss.starter.annotation.EnableDss;
import com.doublekit.eam.server.EnableEamServer;
import com.doublekit.gateway.starter.annotation.EnableGateway;
import com.doublekit.join.starter.annotation.EnableJoin;
import com.doublekit.member.annotation.EnableMemberServer;
import com.doublekit.message.starter.annotation.EnableMessage;
import com.doublekit.privilege.starter.annotation.EnablePrivilegeServer;
import com.doublekit.product.annotation.EnableProductServer;
import com.doublekit.sns.annotation.EnableSnsServer;
import com.doublekit.ssm.annotation.EnableSsmServer;
import com.doublekit.subscribe.annotation.EnableSubscribeServer;
import com.doublekit.tenant.annotation.EnableTenantServer;
import com.doublekit.toolkit.starter.annotation.EnableToolkitServer;
import com.doublekit.user.EnableUserServer;
import com.doublekit.web.starter.annotation.EnableWeb;
import org.springframework.context.annotation.Configuration;

/**
 * OmsServerAutoConfiguration
 */
@Configuration
//common
@EnableBeans
@EnableWeb
@EnableDal
@EnableDfs
@EnableDcs
@EnableJoin
@EnableDss
@EnableDataFly
@EnableGateway
//pcs
@EnableUserServer
@EnableEamServer
@EnablePrivilegeServer
@EnableToolkitServer
//ocs
@EnableMemberServer
@EnableTenantServer
@EnableProductServer
@EnableSubscribeServer
@EnableDocServer
@EnableSnsServer
@EnableSsmServer
//other
@EnableApiboxClient
@EnableMessage
public class OmsAutoConfiguration {
}

