package com.doublekit.console;

import com.doublekit.apibox.client.annotation.EnableApiboxClient;
import com.doublekit.toolkit.annotation.EnableToolkitServer;
import org.springframework.context.annotation.Configuration;
import com.doublekit.user.annotation.EnableUserServer;
import com.doublekit.privilege.annotation.EnablePrivilegeServer;
import com.doublekit.message.annotation.EnableMessageServer;
import com.doublekit.plugin.annotation.EnablePluginServer;

/**
 * TccCommonApplication
 */
@Configuration
@EnableUserServer
@EnablePrivilegeServer
@EnableMessageServer
@EnablePluginServer
@EnableToolkitServer
//@EnableGateway
@EnableApiboxClient
public class ConsoleServerAutoConfiguration {
}

