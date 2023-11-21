package io.tiklab.xpack.starter.config;

import io.tiklab.dal.dsm.config.model.DsmConfig;
import io.tiklab.dal.dsm.support.DsmConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class XpackSqlLoad {

    @Bean
    DsmConfig initDsmConfig() {
        DsmConfig dsmConfig = DsmConfigBuilder.instance();
        dsmConfig.newVersion("1.0.0", new String[]{
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
        dsmConfig.newVersion("1.0.5", new String[]{
                "xpack_1.0.6",
        });
        return dsmConfig;
    }
}
