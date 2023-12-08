package io.thoughtware.hadess.starter.config;


import io.thoughtware.dal.boot.starter.annotation.EnableDal;
import io.thoughtware.dcs.boot.starter.annotation.EnableDcsClient;
import io.thoughtware.dcs.boot.starter.annotation.EnableDcsServer;
import io.thoughtware.eam.boot.starter.annotation.EnableEamClient;
import io.thoughtware.eam.boot.starter.annotation.EnableEamServer;
import io.thoughtware.gateway.boot.starter.annotation.EnableGateway;
import io.thoughtware.hadess.annotation.EnableHadessServer;
import io.thoughtware.licence.boot.starter.annotation.EnableLicenceServer;
import io.thoughtware.messsage.boot.starter.annotation.EnableMessageServer;
import io.thoughtware.openapi.boot.starter.annotation.EnableOpenApi;
import io.thoughtware.plugin.starter.EnablePluginServer;
import io.thoughtware.postgresql.EnablePostgresql;
import io.thoughtware.privilege.boot.starter.annotation.EnablePrivilegeServer;
import io.thoughtware.rpc.boot.starter.annotation.EnableRpc;
import io.thoughtware.security.boot.stater.annotation.EnableSecurityServer;
import io.thoughtware.todotask.boot.stater.annotation.EnableTodoTaskServer;
import io.thoughtware.toolkit.boot.starter.annotation.EnableToolkit;
import io.thoughtware.user.boot.starter.annotation.EnableUserClient;
import io.thoughtware.user.boot.starter.annotation.EnableUserServer;
import org.springframework.context.annotation.Configuration;
/**
 * XpackServerAutoConfiguration
 */
@Configuration
//common
@EnableToolkit
@EnablePostgresql
@EnableDal
@EnableDcsServer
@EnableDcsClient
@EnableOpenApi
@EnableRpc

@EnablePluginServer
//eam
@EnableEamServer
@EnableEamClient
@EnableSecurityServer
@EnableLicenceServer
@EnableUserServer
@EnableUserClient
@EnableMessageServer
@EnableGateway
@EnableTodoTaskServer
@EnablePrivilegeServer


//ocs
@EnableHadessServer
public class HadessAutoConfiguration {
}

