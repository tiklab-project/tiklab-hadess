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
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class test {

    public static void main(String[] args) throws Exception {
      // String token = getToken();
       // getURL1();
        //getURL();
        //getHttp(token);
        //postHttp();

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

           /* // 打印 Layers 数据
            System.out.println("Image Layers:");
            for (String layer : layers) {

                System.out.println("结果："+layer);
            }*/

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


    private static void getURL() throws Exception {
        String input="{\n" +
                "   \"schemaVersion\": 2,\n" +
                "   \"mediaType\": \"application/vnd.docker.distribution.manifest.v2+json\",\n" +
                "   \"config\": {\n" +
                "      \"mediaType\": \"application/vnd.docker.container.image.v1+json\",\n" +
                "      \"size\": 1588,\n" +
                "      \"digest\": \"sha256:d83c7f637787efd251cadd2e9c5c3c301a308d8e392c7806cc29374af5aa44f5\"\n" +
                "   },\n" +
                "   \"layers\": [\n" +
                "      {\n" +
                "         \"mediaType\": \"application/vnd.docker.image.rootfs.diff.tar.gzip\",\n" +
                "         \"size\": 83518086,\n" +
                "         \"digest\": \"sha256:a1d0c75327776413fa0db9ed3adcdbadedc95a662eb1d360dad82bb913f8a1d1\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"mediaType\": \"application/vnd.docker.image.rootfs.diff.tar.gzip\",\n" +
                "         \"size\": 1587436,\n" +
                "         \"digest\": \"sha256:29b7e009ba213cee14be1fd2587716d0b7ba7c49da2a36c7fe32a5f7c18fb66f\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"mediaType\": \"application/vnd.docker.image.rootfs.diff.tar.gzip\",\n" +
                "         \"size\": 202,\n" +
                "         \"digest\": \"sha256:d38d4b627938c85f458b2778d7f23a2e1ba955b71699341c6473b0c9575a6047\"\n" +
                "      }\n" +
                "   ]\n" +
                "}";
        // 创建 SHA-256 MessageDigest 实例
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // 计算哈希值
        byte[] hashBytes = digest.digest(input.getBytes());

        // 将字节数组转换为十六进制字符串
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        // 输出结果
        System.out.println("SHA-256:" + hexString.toString());
    }

    private static void getHttp(String token) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        newHeaders.add(HttpHeaders.ACCEPT, "application/vnd.docker.distribution.manifest.v2+json");

        // 目标 URL
        String proxyUrl = "https://registry-1.docker.io/v2/library/hello-world/manifests/latest";

        ResponseEntity<JSONObject> retryResponse = restTemplate.exchange(proxyUrl, HttpMethod.GET,
                new HttpEntity<>(newHeaders), JSONObject.class);

        // 获取响应头
        HttpHeaders responseHeaders = retryResponse.getHeaders();

        ResponseEntity<JSONObject> body1 = ResponseEntity.status(retryResponse.getStatusCode())
                .headers(retryResponse.getHeaders())
                .body(retryResponse.getBody());

        System.out.println("body1"+body1);


 /*       // 创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.docker.distribution.manifest.v2+json");
        // 创建 HttpEntity 对象，包含请求头和请求体
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, JSONObject.class);
        JSONObject body = response.getBody();*/
        JSONObject body = retryResponse.getBody();
        System.out.println("响应体："+body);
    }

    private static void postHttp() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // 目标 URL
        String url = "https://hub.docker.com/v2/access-tokens";

        // 创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // 设置请求类型

        // 创建请求体
        String requestBody = "\"token_label\": \"My read only token\",\n" +
                "\"scopes\": [\n" +
                "\"repo:read\"\n" +
                "]"; // 这里使用 JSON 字符串作为请求体

        // 创建 HttpEntity 对象，包含请求头和请求体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送 POST 请求并获取响应
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // 输出响应内容
        System.out.println("响应状态码: " + response.getStatusCode());
        System.out.println("响应体: " + response.getBody());
    }


    private static String getToken() throws Exception {
        String tokenUrl = "https://auth.docker.io/token?service=registry.docker.io&scope=repository:library/hello-world:pull";
        URL url = new URL(tokenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 读取响应
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        connection.disconnect();

        // 解析返回的 JSON，提取 token
        return response.toString().split("\"")[3];// 注意：这不是最安全的解析方法，建议使用 JSON 库
    }


}
