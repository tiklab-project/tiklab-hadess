package  net.tiklab.oms;


import  net.tiklab.beans.starter.annotation.EnableBeans;
import  net.tiklab.dal.starter.annotation.EnableDal;
import  net.tiklab.dcs.starter.EnableDcs;
import  net.tiklab.dfs.starter.EnableDfs;
import  net.tiklab.dsm.starter.annotation.EnableDsm;
import  net.tiklab.dss.starter.EnableDss;
import  net.tiklab.eam.client.EnableEamClient;
import  net.tiklab.join.starter.annotation.EnableJoin;
import  net.tiklab.licence.starter.EnableLicenceServer;
import  net.tiklab.message.starter.EnableMessage;
import  net.tiklab.oms.annotation.EnableOmsServer;
import  net.tiklab.rpc.starter.annotation.EnableRpc;
import  net.tiklab.user.client.EnableUserClient;
import  net.tiklab.web.starter.annotation.EnableWeb;
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
@EnableRpc

//eam
@EnableEamClient
@EnableUserClient
@EnableLicenceServer
//@EnablePrivilegeServer
//@EnableToolkitServer
//ocs
@EnableOmsServer
@EnableMessage
public class OmsAutoConfiguration {
}

