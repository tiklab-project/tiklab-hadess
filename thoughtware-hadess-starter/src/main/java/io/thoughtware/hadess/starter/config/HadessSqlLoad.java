package io.thoughtware.hadess.starter.config;

import io.thoughtware.dal.dsm.config.model.DsmConfig;
import io.thoughtware.dal.dsm.support.DsmConfigBuilder;
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
                //xpack
                "xpack_1.0.0",
                "xprivilege_1.0.0",


        });
        dsmConfig.newVersion("1.0.1", new String[]{
                "xpack_1.0.1",
                //"xprivilege_1.0.1",

        });
        dsmConfig.newVersion("1.0.2", new String[]{
                "xpack_1.0.2",
        });
        dsmConfig.newVersion("1.0.3", new String[]{
                "xpack_1.0.3",
        });
        dsmConfig.newVersion("1.0.4", new String[]{
                "xpack_1.0.4",
        });
        dsmConfig.newVersion("1.0.5", new String[]{
                "xpack_1.0.5",
        });
        dsmConfig.newVersion("1.0.6", new String[]{
                "xpack_1.0.6",
        });
        dsmConfig.newVersion("1.0.7", new String[]{
                "xpack_1.0.7",
        });
        return dsmConfig;
    }
}
