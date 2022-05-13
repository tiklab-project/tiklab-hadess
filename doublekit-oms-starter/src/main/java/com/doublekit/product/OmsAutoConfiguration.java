package com.doublekit.product;

import com.doublekit.apibox.client.EnableApiboxClient;
import com.doublekit.beans.starter.annotation.EnableBeans;
import com.doublekit.dal.starter.annotation.EnableDal;
import com.doublekit.doc.EnableDocServer;
import com.doublekit.dsm.starter.annotation.EnableDsm;
import com.doublekit.dss.starter.EnableDss;
import com.doublekit.eam.starter.EnableEam;
import com.doublekit.gateway.starter.EnableGateway;
import com.doublekit.join.starter.annotation.EnableJoin;
import com.doublekit.member.EnableMemberServer;
import com.doublekit.message.starter.EnableMessage;
import com.doublekit.privilege.EnablePrivilegeServer;
import com.doublekit.sns.EnableSnsServer;
import com.doublekit.statistics.EnableStatisticsServer;
import com.doublekit.subscribe.EnableSubscribeServer;
import com.doublekit.tenant.EnableTenantServer;
import com.doublekit.user.starter.EnableUser;
import com.doublekit.web.starter.annotation.EnableWeb;
import org.springframework.context.annotation.Configuration;

/**
 * OmsServerAutoConfiguration
 */
@Configuration
//common
@EnableBeans
@EnableJoin
@EnableWeb
@EnableDal
@EnableDss
@EnableDsm
@EnableGateway
//pcs
@EnableUser
@EnableEam
@EnablePrivilegeServer
//@EnableToolkitServer
//ocs
@EnableMemberServer
@EnableTenantServer
@EnableProductServer
@EnableSubscribeServer
@EnableDocServer
@EnableSnsServer
@EnableStatisticsServer
//other
@EnableApiboxClient
@EnableMessage
public class OmsAutoConfiguration {
}

