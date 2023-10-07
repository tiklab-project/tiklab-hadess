package io.tiklab.xpack.common;

import io.tiklab.core.context.AppHomeContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class XpackYamlDataMaServiceImpl implements XpackYamlDataMaService{


    @Value("${DATA_HOME}")
    private String dataHome;


    @Value("${backup.address}")
    private String backupAddress;

    @Value("${server.port}")
    private String port;

    @Value("${jdbc.url}")
    String jdbcUrl;

    @Value("${repository.address}")
    String memoryAddress;

    @Value("${DATA_HOME}")
    String DATA_HOME;


    @Override
    public String uploadAddress() {
        return dataHome+"/upload";
    }

    @Override
    public String host() {
        String substring = jdbcUrl.substring(jdbcUrl.indexOf("//", 1)+2, jdbcUrl.indexOf("/", jdbcUrl.indexOf("/") + 2));
        String[] split = substring.split(":");
        return split[0];
    }

    @Override
    public String dbName() {
        String dbName = jdbcUrl.substring(jdbcUrl.indexOf("/", jdbcUrl.indexOf("/") + 2)+1, jdbcUrl.indexOf("?", 1));
        return dbName;
    }

    @Override
    public String schemaName() {
        return "public";
    }

    @Override
    public String backupAddress() {
        return backupAddress;
    }

    @Override
    public String repositoryAddress() {
        return memoryAddress;
    }

    @Override
    public String serverPort() {
        return port;
    }


    @Override
    public String fileAddress() {
        return DATA_HOME+"/file";

    }

    @Override
    public String pgSqlAddress() {
        String appHome = AppHomeContext.getAppHome();
        String path = new File(appHome).getParentFile().getParentFile().getAbsolutePath();
        //return path+"/embbed/pgsql-10.23/bin";
        return "/Users/limingliang/postgreSQL/bin";
    }


}
