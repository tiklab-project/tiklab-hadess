package io.tiklab.xpack.common;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.context.AppHomeContext;
import io.tiklab.core.exception.SystemException;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RepositoryUtil {

    /**
     * 获取默认仓库地址
     * @param url
     * @return 仓库地址
     */
    public static String findRepositoryUrl(String url){
        if (StringUtils.isEmpty(url)){
            url = AppHomeContext.getAppHome();
        }
        String repositoryAddress = url + "/xpack";
        return repositoryAddress;
    }

    /**
     * 修改package.js registry地址
     * @param packageFolderPath  需要修改文件的地址
     * @param registryUrl 修改的地址
     */
    public static void modifyPackageJson(String packageFolderPath, String registryUrl) throws IOException {
        File packageJsonFile = new File(packageFolderPath, "package.json");
        if (packageJsonFile.exists()) {
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(packageJsonFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            String fileData = content.toString();

            JSONObject packageJson = JSONObject.parseObject(fileData);
            Object publishConfig =packageJson.get("publishConfig");
            if (!ObjectUtils.isEmpty(publishConfig)){
                JSONObject registry = (JSONObject) publishConfig;
                String address = registry.get("registry").toString();
                fileData= fileData.replace(address,registryUrl);
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(packageJsonFile))) {
                writer.write(fileData);
            }
        }
    }

    /**
     * 解压zip文件夹
     * @param outputFolderPath 解压路径
     * @param inputFilePath 压缩包文件路径
     */

    public static void decompressionZip(String inputFilePath,String outputFolderPath) throws IOException {

        File targetFolder = new File(outputFolderPath);

        // 创建目标文件夹（如果不存在）
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        byte[] buffer = new byte[1024];

        // 创建zip文件输入流
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(inputFilePath));

        // 获取zip文件中的每个entry
        ZipEntry zipEntry = zipInputStream.getNextEntry();

        while (zipEntry != null) {
            String entryName = zipEntry.getName();

            // 构建目标文件路径
            File extractedFile = new File(targetFolder, entryName);

            // 如果entry是一个文件，则解压缩
            if (!zipEntry.isDirectory()) {
                // 创建目标文件的父目录（如果不存在）
                if (!extractedFile.getParentFile().exists()) {
                    extractedFile.getParentFile().mkdirs();
                }

                // 创建输出流，将entry解压到目标文件
                FileOutputStream outputStream = new FileOutputStream(extractedFile);
                int length;
                while ((length = zipInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
            }

            // 关闭当前entry，继续获取下一个entry
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }

        // 关闭zip文件输入流
        zipInputStream.close();
    }

    /**
     * 原生http  get调用
     * @param address 解压路径
     */
    public static String httpGet(String address) throws Exception {

        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    /**
     * 拷贝文件并重命名
     * @param oldFile
     */
    public static void copyFile(String oldFile, String newFile) throws Exception {
        InputStream inputStream = new FileInputStream(oldFile);
        OutputStream outputStream = new FileOutputStream(newFile);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }

    /**
     * 格式化文件大小，将字节转换为可读性更好的格式
     * @param size 单位B
     */
    public static String formatSize(long size) {
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        double temp = size;
        int index = 0;
        while (temp >= 1024) {
            temp /= 1024;
            index++;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(temp) + units[index];
    }

    /**
     * 计算和
     * @param sizeList
     */
    public static String formatSizeSum(List<String> sizeList) {
        Double allSize=0.0;
        for (String size:sizeList){
            if (size.endsWith("KB")){
                String substring = size.substring(0, size.indexOf("KB"));
                allSize=allSize+(Double.valueOf(substring)*1024);
                continue;
            }
            if (size.endsWith("MB")){
                String substring = size.substring(0, size.indexOf("MB"));
                allSize=allSize+(Double.valueOf(substring)*1024*1024);
                continue;
            }
            if (size.endsWith("GB")){
                String substring = size.substring(0, size.indexOf("GB"));
                allSize=allSize+(Double.valueOf(substring)*1024*1024*1024);
                continue;
            }
            if (size.endsWith("TB")){
                String substring = size.substring(0, size.indexOf("TB"));
                allSize=allSize+(Double.valueOf(substring)*1024*1024*1024*1024);
                continue;
            }
            if (size.endsWith("B")){
                String substring = size.substring(0, size.indexOf("B"));
                allSize=allSize+Double.valueOf(substring);
            }


        }

        return formatSize(Math.round(allSize));
    }
}
