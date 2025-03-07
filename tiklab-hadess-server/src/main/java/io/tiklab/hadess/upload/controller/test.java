package io.tiklab.hadess.upload.controller;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.ContainerConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class test {

    public static void main(String[] args) throws Exception {

        String expression="{{systems.io.used(/)}}<[40]";
        String sa = expression.replaceAll("^\\{\\{(.*?)\\}\\}([<>]=?|==)\\[.*?\\]$", "$1");;

        System.out.println("12");
        // 测试生成 0 到 3 之间的随机数
        for (int i = 0; i < 10; i++) {
            // 获取当前时间的纳秒数
            long nanoTime = System.nanoTime();
            // 取模 4，得到 0 到 3 之间的随机数
            int randomNumber = (int) (nanoTime % 4);
            System.out.println("随机数: " + randomNumber);
        }



        String beforePath = org.apache.commons.lang.StringUtils.substringBefore("docker-test/hadess/blobs/sha256:f2a868e4472b8516e8f1a8e4430c62c5a42ac16b8fb21aa80434840703585dfc", "/blobs/");
        String s = StringUtils.substringAfterLast(beforePath, "/");

        // 创建 Docker 客户端
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        try {
            String imageName="192.168.10.10:8080/docker-test/test-bulid:1.0.0";

           // String imageName="sha256:d83c7f637787efd251cadd2e9c5c3c301a308d8e392c7806cc29374af5aa44f5";
            // 获取镜像的详细信息
            InspectImageResponse imageInfo = dockerClient.inspectImageCmd(imageName).exec();

            // 获取镜像的 Layers 数据
            List<String> layers = imageInfo.getRootFS().getLayers();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void getURL1() throws Exception {
        String tokenUrl = "https://auth.docker.io/token?service=registry.docker.io&scope=repository:library/hello-world:pull";


        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JSONObject> forEntity = restTemplate.getForEntity(tokenUrl, JSONObject.class);
            JSONObject resultBody = forEntity.getBody();

            int value = forEntity.getStatusCode().value();
            String accessToken = resultBody.get("access_token").toString();
            // 输出镜像信息
            System.out.println("成功获取镜像信息: " + resultBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 格式化大小（字节转换为 MB/KB）
    private static String formatSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return (size / 1024) + " KB";
        } else {
            return (size / (1024 * 1024)) + " MB";
        }
    }





}
