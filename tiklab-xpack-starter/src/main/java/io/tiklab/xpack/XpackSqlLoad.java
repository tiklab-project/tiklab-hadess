package io.tiklab.xpack;

import io.tiklab.dsm.model.DsmConfig;
import io.tiklab.dsm.support.DsmConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class XpackSqlLoad {

    @Bean
    DsmConfig initDsmConfig() {
        DsmConfig dsmConfig = DsmConfigBuilder.instance();
        dsmConfig.newVersion("1.0.0", new String[]{
                //UserDsm
                "dmprivilege_1.0.0_ddl",
                "dmuser_1.0.0_ddl",
                "privilege_1.0.0_ddl",
                "user_1.0.0_ddl",
                "userCe_1.0.0_ddl",
                "privilege_1.0.0_dml",
                "user_1.0.0_dml",
                "userCe_1.0.0_dml",
                //IntegrationDsm
                "tool_1.0.0_ddl",
                //LicenceDsm
                "app-authorization_1.0.0_ddl",
                "app-authorization_1.0.0_dml",
                //MessageDsm
                "message_1.0.0_ddl",
                "message_1.0.0_dml",
                //SecurityDsm
                "oplog_1.0.0_ddl",
                "oplog_1.0.0_dml",
                //TodoTaskDsm
                "todotask_1.0.0_ddl",
                "todotask_1.0.0_dml",
                //xpack
                "xpack_1.0.0_ddl",

        });
        return dsmConfig;
    }
}
