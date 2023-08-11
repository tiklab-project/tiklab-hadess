package io.tiklab.xpack.library.service;

import io.tiklab.core.exception.SystemException;
import io.tiklab.xpack.common.XpakYamlDataMaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

@Service
public class LibraryFileReadServiceImpl implements LibraryFileReadService{

    @Value("${visit.address:null}")
    private String repositoryAddress;

    @Value("${server.port:8080}")
    private String port;

    @Autowired
    XpakYamlDataMaService xpakYamlDataMaService;

    @Override
    public byte[] fileRead(String requestURI) {

        try {
            String rpyAddress=xpakYamlDataMaService.repositoryAddress();

            String[] split = requestURI.split("/");
            Integer indexes = split[1].length() + split[2].length() + 2;
            String url = requestURI.substring(indexes);

            File file = new File(rpyAddress+url);

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
            throw new SystemException(e);
        }
    }

    @Override
    public String findServerIp() {
        String ip=null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual()) {
                    continue;  // 跳过回环和虚拟网络接口
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address.isLoopbackAddress()) {
                        continue;  // 跳过回环地址
                    }
                    if (address.getHostAddress().contains(":")) {
                        continue;  // 跳过IPv6地址
                    }
                    ip = address.getHostAddress();
                }
            }
        } catch (Exception e) {
            ip = "172.0.0.1";
        }
        String absoluteAddressl="http://" + ip + ":" + port;
        return absoluteAddressl;
    }
}
