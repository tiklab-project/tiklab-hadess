package io.tiklab.xpack.library.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;

import java.io.*;

public class test {

    public static void main(String[] args) throws IOException {
        String url="/Users/limingliang/source";


        String sourceFilePath =url+"/tiklab-privilege-spring-boot-starter-1.0.0.1-sources.jar";
        String targetFilePath =url+ "/"+"tiklab-privilege-spring-boot-starter-1.0.0.2"+"-sources.jar";

        try (
                InputStream inputStream = new FileInputStream(sourceFilePath);
                OutputStream outputStream = new FileOutputStream(targetFilePath);
        ) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            System.out.println("文件拷贝完成！");
        } catch (IOException e) {
            System.out.println("拷贝文件时出现错误：" + e.getMessage());
        }
    }
}
