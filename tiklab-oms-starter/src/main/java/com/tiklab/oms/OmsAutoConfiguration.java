package com.tiklab.oms;


import com.tiklab.beans.starter.annotation.EnableBeans;
import com.tiklab.dal.starter.annotation.EnableDal;
import com.tiklab.dcs.starter.EnableDcs;
import com.tiklab.dfs.starter.EnableDfs;
import com.tiklab.dsm.starter.annotation.EnableDsm;
import com.tiklab.dss.starter.EnableDss;
import com.tiklab.eam.starter.EnableEam;
import com.tiklab.gateway.starter.EnableGateway;
import com.tiklab.join.starter.annotation.EnableJoin;
import com.tiklab.message.starter.EnableMessage;
import com.tiklab.oms.annotation.EnableOmsServer;
//import com.tiklab.privilege.EnablePrivilegeServer;
import com.tiklab.rpc.starter.annotation.EnableRpc;
import com.tiklab.user.starter.EnableUser;
import com.tiklab.web.starter.annotation.EnableWeb;
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
//pcs
@EnableRpc
@EnableUser
@EnableEam
//@EnablePrivilegeServer
//@EnableToolkitServer
//ocs
@EnableOmsServer
@EnableMessage
public class OmsAutoConfiguration {
}
