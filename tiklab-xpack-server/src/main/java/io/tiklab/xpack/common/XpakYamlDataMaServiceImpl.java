package io.tiklab.xpack.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class XpakYamlDataMaServiceImpl implements XpakYamlDataMaService{

    @Value("${server.port:8080}")
    private String port;

    @Value("${repository.address}")
    String memoryAddress;


    @Override
    public String repositoryAddress() {
        return memoryAddress;

    }

    @Override
    public String serverPort() {
        return port;
    }
}
