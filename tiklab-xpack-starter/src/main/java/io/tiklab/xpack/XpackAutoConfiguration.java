package io.tiklab.xpack;


import  io.tiklab.dal.starter.annotation.EnableDal;
import  io.tiklab.dcs.starter.EnableDcs;
import  io.tiklab.dfs.starter.EnableDfs;
import  io.tiklab.dsm.starter.annotation.EnableDsm;
import  io.tiklab.dss.starter.EnableDss;
import io.tiklab.integration.starter.EnableIntegration;
import  io.tiklab.licence.starter.EnableLicenceServer;
import io.tiklab.messsage.starter.EnableMessage;
import io.tiklab.mysql.starter.EnableMysql;
import io.tiklab.privilege.starter.EnablePrivilegeServer;
import io.tiklab.security.stater.EnableSecurity;
import io.tiklab.xpack.annotation.EnableXpackServer;
import  io.tiklab.rpc.starter.annotation.EnableRpc;
import io.tiklab.tks.annotation.EnableTks;
import io.tiklab.user.starter.EnableUser;
import  io.tiklab.web.starter.annotation.EnableWeb;
import org.springframework.context.annotation.Configuration;
import io.tiklab.eam.starter.EnableEam;
/**
 * XpackServerAutoConfiguration
 */
@Configuration
//common
@EnableTks
@EnableWeb
@EnableMysql
@EnableDal
@EnableDss
@EnableDfs
@EnableDcs
@EnableDsm
@EnableRpc

//eam
@EnableEam
@EnableSecurity
@EnableIntegration
@EnableLicenceServer
@EnableUser
@EnablePrivilegeServer
@EnableMessage
//ocs
@EnableXpackServer
public class XpackAutoConfiguration {
}

