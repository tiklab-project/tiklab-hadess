package io.tiklab.hadess.starter.config;


import io.tiklab.dal.boot.starter.annotation.EnableDal;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsClient;
import io.tiklab.dcs.boot.starter.annotation.EnableDcsServer;
import io.tiklab.dsm.boot.starter.annotation.EnableDsm;
import io.tiklab.eam.boot.starter.annotation.EnableEamClient;
import io.tiklab.eam.boot.starter.annotation.EnableEamServer;
import io.tiklab.gateway.boot.starter.annotation.EnableGateway;
import io.tiklab.hadess.annotation.EnableHadessServer;
import io.tiklab.licence.boot.starter.annotation.EnableLicenceServer;
import io.tiklab.messsage.boot.starter.annotation.EnableMessageServer;
import io.tiklab.openapi.boot.starter.annotation.EnableOpenApi;
import io.tiklab.postgresql.EnablePostgresql;
import io.tiklab.privilege.boot.starter.annotation.EnablePrivilegeServer;
import io.tiklab.rpc.boot.starter.annotation.EnableRpc;
import io.tiklab.security.boot.stater.annotation.EnableSecurityServer;
import io.tiklab.toolkit.boot.starter.annotation.EnableToolkit;
import io.tiklab.user.boot.starter.annotation.EnableUserClient;
import io.tiklab.user.boot.starter.annotation.EnableUserServer;
import org.springframework.context.annotation.Configuration;
/**
 * XpackServerAutoConfiguration
 */
@Configuration
//common
@EnableToolkit
@EnablePostgresql
@EnableDal
@EnableDsm
@EnableDcsServer
@EnableDcsClient
@EnableOpenApi
@EnableRpc

//eam
@EnableEamServer
@EnableEamClient
@EnableLicenceServer

@EnableSecurityServer
@EnableUserServer
@EnableUserClient
@EnableMessageServer
@EnableGateway
@EnablePrivilegeServer

//ocs
@EnableHadessServer
public class HadessAutoConfiguration {
}

