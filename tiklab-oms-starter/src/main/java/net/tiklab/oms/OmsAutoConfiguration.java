package  net.tiklab.oms;


import  net.tiklab.dal.starter.annotation.EnableDal;
import  net.tiklab.dcs.starter.EnableDcs;
import  net.tiklab.dfs.starter.EnableDfs;
import  net.tiklab.dsm.starter.annotation.EnableDsm;
import  net.tiklab.dss.starter.EnableDss;
import  net.tiklab.eam.client.EnableEamClient;
import net.tiklab.eam.starter.EnableEam;
import  net.tiklab.licence.starter.EnableLicenceServer;
import  net.tiklab.oms.annotation.EnableOmsServer;
import net.tiklab.oplog.stater.EnableLog;
import  net.tiklab.rpc.starter.annotation.EnableRpc;
import net.tiklab.tks.annotation.EnableTks;
import  net.tiklab.user.client.EnableUserClient;
import net.tiklab.user.starter.EnableUser;
import  net.tiklab.web.starter.annotation.EnableWeb;
import org.springframework.context.annotation.Configuration;

/**
 * OmsServerAutoConfiguration
 */
@Configuration
//common
@EnableTks
@EnableWeb
@EnableDal
@EnableDss
@EnableDfs
@EnableDcs
@EnableDsm
@EnableRpc

//eam
@EnableEam
@EnableLog
@EnableLicenceServer
@EnableUser
//@EnablePrivilegeServer
//@EnableToolkitServer
//ocs
@EnableOmsServer
//@EnableMessage
public class OmsAutoConfiguration {
}

