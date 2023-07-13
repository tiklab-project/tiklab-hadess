package io.tiklab.xpack;


import io.tiklab.dal.boot.starter.annotation.EnableDal;
import io.tiklab.dcs.boot.starter.EnableDcs;
import io.tiklab.dfs.boot.starter.EnableDfs;
import io.tiklab.dsm.boot.starter.EnableDsm;
import io.tiklab.dss.boot.starter.EnableDss;
import io.tiklab.gateway.starter.EnableGateway;
import io.tiklab.integration.starter.EnableIntegration;
import  io.tiklab.licence.starter.EnableLicenceServer;
import io.tiklab.messsage.starter.EnableMessage;
import io.tiklab.plugin.starter.EnablePluginServer;
import io.tiklab.postgresql.EnablePostgresql;
import io.tiklab.privilege.EnablePrivilegeServer;
import io.tiklab.rpc.boot.starter.EnableRpc;
import io.tiklab.security.stater.EnableSecurity;
import io.tiklab.todotask.stater.EnableTodoTask;
import io.tiklab.toolkit.boot.starter.EnableToolkit;
import io.tiklab.xpack.annotation.EnableXpackServer;
import io.tiklab.user.starter.EnableUser;
import org.springframework.context.annotation.Configuration;
import io.tiklab.eam.starter.EnableEam;
/**
 * XpackServerAutoConfiguration
 */
@Configuration
//common
@EnableToolkit
@EnablePostgresql
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

