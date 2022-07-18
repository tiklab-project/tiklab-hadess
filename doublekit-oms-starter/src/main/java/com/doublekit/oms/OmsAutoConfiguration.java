package com.doublekit.oms;


import com.doublekit.beans.starter.annotation.EnableBeans;
import com.doublekit.dal.starter.annotation.EnableDal;
import com.doublekit.dcs.starter.EnableDcs;
import com.doublekit.dfs.starter.EnableDfs;
import com.doublekit.dsm.starter.annotation.EnableDsm;
import com.doublekit.dss.starter.EnableDss;
import com.doublekit.eam.starter.EnableEam;
import com.doublekit.gateway.starter.EnableGateway;
import com.doublekit.join.starter.annotation.EnableJoin;
import com.doublekit.message.starter.EnableMessage;
import com.doublekit.oms.annotation.EnableOmsServer;
import com.doublekit.privilege.EnablePrivilegeServer;
import com.doublekit.rpc.starter.annotation.EnableRpc;
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
@EnableDfs
@EnableDcs
@EnableDsm
@EnableGateway
//@EnableTenantServer
//pcs
@EnableRpc
@EnableUser
@EnableEam
@EnablePrivilegeServer
//@EnableToolkitServer
//ocs
@EnableOmsServer
@EnableMessage
public class OmsAutoConfiguration {
}

