package com.doublekit.oms.config;

import com.doublekit.apibox.client.annotation.EnableApiboxClient;
import com.doublekit.beans.starter.annotation.EnableBeans;
import com.doublekit.dal.starter.annotation.EnableDal;
import com.doublekit.datafly.annotation.DataFly;
import com.doublekit.datafly.starter.annotation.EnableDataFly;
import com.doublekit.dcs.starter.annotation.EnableDcs;
import com.doublekit.dfs.starter.annotation.EnableDfs;
import com.doublekit.doc.annotation.EnableDocServer;
import com.doublekit.dsl.starter.annotation.EnableDsl;
import com.doublekit.dss.starter.annotation.EnableDss;
import com.doublekit.eam.client.annotation.EnableEamClient;
import com.doublekit.eam.server.annotation.EnableEamServer;
import com.doublekit.member.annotation.EnableMemberServer;
import com.doublekit.privilege.annotation.EnablePrivilegeServer;
import com.doublekit.product.annotation.EnableProductServer;
import com.doublekit.sns.annotation.EnableSnsServer;
import com.doublekit.subscribe.annotation.EnableSubscribeServer;
import com.doublekit.tenant.server.annotation.EnableTenantServer;
import com.doublekit.toolkit.annotation.EnableToolkitServer;
import com.doublekit.user.annotation.EnableUserServer;
import com.doublekit.web.starter.annotation.EnableWeb;
import org.springframework.context.annotation.Configuration;

/**
 * OmsServerAutoConfiguration
 */
@Configuration
//common
//@EnableFramework
@EnableBeans
@EnableWeb
@EnableDal
@EnableDfs
@EnableDcs
@EnableDsl
@EnableDss
@DataFly({
        "subscribe",
        "sns",
        "product",
        "tenant",
        "doc",
        "member",
})
@EnableDataFly
//pcs
@EnableUserServer
@EnableEamServer
@EnableEamClient
@EnablePrivilegeServer
@EnableToolkitServer
//ocs
@EnableMemberServer
@EnableTenantServer
@EnableProductServer
@EnableSubscribeServer
@EnableDocServer
@EnableSnsServer
//other
@EnableApiboxClient

public class OmsAutoConfiguration {
}

