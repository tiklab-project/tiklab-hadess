package io.tiklab.xpack.library.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class LibraryFileReadServiceImpl implements LibraryFileReadService{

    @Value("${repository.address:null}")
    private String repositoryAddress;

    @Value("${server.port:8080}")
    private String port;

    @Override
    public byte[] fileRead(String requestURI) {

        try {
            String[] split = requestURI.split("/");
            Integer indexes = split[1].length() + split[2].length() + 2;
            String url = requestURI.substring(indexes);
            File file = new File(url);

            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            byte[] bytes = bos.toByteArray();

            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findServerIp() {
        if (StringUtils.isEmpty(repositoryAddress)){
            try {
               String ip = InetAddress.getLocalHost().getHostAddress();
                String  absoluteAddress="http://" + ip + ":" + port;
                return  absoluteAddress;
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }else {
            return  repositoryAddress;
        }
    }
}
