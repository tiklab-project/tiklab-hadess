package io.tiklab.xpack;


import  io.tiklab.dal.starter.annotation.EnableDal;
import  io.tiklab.dcs.starter.EnableDcs;
import  io.tiklab.dfs.starter.EnableDfs;
import  io.tiklab.dsm.starter.annotation.EnableDsm;
import  io.tiklab.dss.starter.EnableDss;
import io.tiklab.gateway.starter.EnableGateway;
import io.tiklab.integration.starter.EnableIntegration;
import io.tiklab.join.starter.EnableToolkit;
import  io.tiklab.licence.starter.EnableLicenceServer;
import io.tiklab.messsage.starter.EnableMessage;
import io.tiklab.mysql.starter.EnableMysql;
import io.tiklab.pluginx.starter.EnablePluginServer;
import io.tiklab.privilege.EnablePrivilegeServer;
import io.tiklab.security.stater.EnableSecurity;
import io.tiklab.todotask.stater.EnableTodoTask;
import io.tiklab.xpack.annotation.EnableXpackServer;
import  io.tiklab.rpc.starter.annotation.EnableRpc;
import io.tiklab.user.starter.EnableUser;
import org.springframework.context.annotation.Configuration;
import io.tiklab.eam.starter.EnableEam;
/**
 * XpackServerAutoConfiguration
 */
@Configuration
//common
@EnableToolkit
@EnableMysql
@EnableDal
@EnableDss
@EnableDfs
@EnableDcs
@EnableDsm
@EnableRpc


@EnablePluginServer
//eam
@EnableEam
@EnableSecurity
@EnableIntegration
@EnableLicenceServer
@EnableUser
@EnableMessage
@EnableGateway
@EnableTodoTask
@EnablePrivilegeServer

//ocs
@EnableXpackServer
public class XpackAutoConfiguration {
}

