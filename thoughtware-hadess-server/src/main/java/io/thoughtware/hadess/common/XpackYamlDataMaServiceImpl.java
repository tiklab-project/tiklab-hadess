package io.thoughtware.hadess.common;

import io.thoughtware.core.context.AppHomeContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class XpackYamlDataMaServiceImpl implements XpackYamlDataMaService{


    @Value("${DATA_HOME}")
    private String dataHome;


    @Value("${server.port}")
    private String port;

    @Value("${jdbc.url}")
    String jdbcUrl;

    @Value("${repository.address}")
    String memoryAddress;

    @Value("${DATA_HOME}")
    String DATA_HOME;


    @Value("${spring.profiles.active:null}")
    String environment;


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
    public String scanFileAddress() {
        return DATA_HOME+"/scan";
    }

    @Override
    public String pgSqlAddress() {
        String appHome = AppHomeContext.getAppHome();
        String path = new File(appHome).getParentFile().getParentFile().getAbsolutePath();
        return path+"/embbed/pgsql-10.23/bin";
       // return "/Users/limingliang/postgreSQL/bin";
    }

    public String getUploadRepositoryUrl(String requestURL,String type){
        return StringUtils.substringAfter(requestURL,"/"+type+"/");

    }

    @Override
    public String getOpenScanUrl() {
        if (("dev").equals(environment)){
           return AppHomeContext.getAppHome() + "/embbed/opensca-1.0.13";
        }else {
            return  new File(AppHomeContext.getAppHome()).getParentFile().getParent()+"/embbed/opensca-1.0.13";
        }
    }

    @Override
    public String getLocalHoleUrl() {
        if (("dev").equals(environment)){
            return AppHomeContext.getAppHome() + "/embbed/cve.json";
        }else {
            return  new File(AppHomeContext.getAppHome()).getParentFile().getParent()+"/embbed/cve.json";
        }
    }


}
