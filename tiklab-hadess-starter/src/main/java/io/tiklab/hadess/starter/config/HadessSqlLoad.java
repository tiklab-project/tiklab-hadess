package io.tiklab.hadess.starter.config;

import io.tiklab.dsm.model.DsmConfig;
import io.tiklab.dsm.support.DsmConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class HadessSqlLoad {

    @Bean
    DsmConfig initDsmConfig() {
        DsmConfig dsmConfig = DsmConfigBuilder.instance();
        dsmConfig.newVersion("1.0.0", new String[]{
                "backups_1.0.0",
                //PrivilegeDsm
                "privilege_1.0.0",
                //UserDsm
                "user_1.0.0",
                "userCe_1.0.0",
                //IntegrationDsm
                "tool_1.0.0",
                //LicenceDsm
                "app-authorization_1.0.0",
                //MessageDsm
                "message_1.0.0",
                //SecurityDsm
                "oplog_1.0.0",
                //TodoTaskDsm
                "todotask_1.0.0",
                "openapi_1.0.0",
                //xpack
                "xpack_1.0.0",
                "xprivilege_1.0.0",
                "hadess_1.0.0",

        });
        dsmConfig.newVersion("1.0.1", new String[]{
                "xpack_1.0.1",
                "oplog_1.0.1",
                "message_1.0.1",
                "todotask_1.0.1",
                "xprivilege_1.0.1",
                "apply-auth_1.0.1",
                "privilege_1.0.1",
                "hadess_1.0.1",
                "scan_1.0.1",

        });
        dsmConfig.newVersion("1.0.2", new String[]{
                "xpack_1.0.2",
                "message_1.0.2",
                "oplog_1.0.2",
                "todotask_1.0.2",
                "apply-auth_1.0.2",
                "privilege_1.0.2",
                "xprivilege_1.0.2",
                "hadess_1.0.2",
        });
        dsmConfig.newVersion("1.0.3", new String[]{
                "xpack_1.0.3",
                "message_1.0.3",
                "oplog_1.0.3",
                "apply-auth_1.0.3",
                "privilege_1.0.3",
                "hadess_1.0.3",
        });
        dsmConfig.newVersion("1.0.4", new String[]{
                "message_1.0.4",
                "oplog_1.0.4",
                "apply-auth_1.0.4",
                "privilege_1.0.4",
                "hadess_1.0.4",
        });
        dsmConfig.newVersion("1.0.5", new String[]{
                "message_1.0.5",
        });
        dsmConfig.newVersion("1.0.6", new String[]{
                "message_1.0.6",
        });
        dsmConfig.newVersion("1.0.7", new String[]{
                "message_1.0.7"
        });
        dsmConfig.newVersion("1.0.8", new String[]{
                "message_1.0.8"
        });
        dsmConfig.newVersion("1.1.1", new String[]{
             "user_1.1.1"
        });

        return dsmConfig;
    }
}
