package io.thoughtware.hadess.common;

import com.alibaba.fastjson.JSONObject;
import io.thoughtware.core.context.AppHomeContext;
import io.thoughtware.core.exception.SystemException;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
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
     * 递归压缩当前文件夹 tar.gz
     * @param sourceFolder 当前文件夹路径
     * @param tos 压缩文件ZipOutputStream
     */
    public static void compressFolder(String sourceFolder, String parentEntryPath, TarArchiveOutputStream tos) throws IOException {
        tos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
        File folder = new File(sourceFolder);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果是子文件夹，创建对应的tar条目，然后递归压缩子文件夹中的内容
                    String entryName = parentEntryPath + file.getName() + "/";
                    TarArchiveEntry entry = new TarArchiveEntry(entryName);
                    tos.putArchiveEntry(entry);
                    tos.closeArchiveEntry();
                    compressFolder(file.getAbsolutePath(), entryName, tos);
                } else {
                    // 如果是文件，创建对应的tar条目，并将文件内容写入tar输出流中
                    String entryName = parentEntryPath + file.getName();
                    TarArchiveEntry entry = new TarArchiveEntry(entryName);
                    entry.setSize(file.length());
                    tos.putArchiveEntry(entry);

                    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = bis.read(buffer)) != -1) {
                            tos.write(buffer, 0, bytesRead);
                        }
                    }

                    // 关闭当前条目
                    tos.closeArchiveEntry();
                }
            }
        }
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
     * 获取与当前时间的时间差
     * @param longTime
     * @return 位置
     */
    public static String timeBad(Long longTime){
        long days = longTime / (24 * 60 * 60 * 1000); // 计算天数
        long hours = (longTime % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000); // 计算小时数
        long minutes = (longTime % (60 * 60 * 1000)) / (60 * 1000); // 计算分钟数
        long second =(longTime % (60 * 60 * 1000)) % (60 * 1000) / 1000;  //计算秒数

        String badTime=null;
        if (days!=0){
            badTime= days + "天"+ hours + "时";
        }
        if (days==0&&hours!=0){
            badTime= hours + "时"+ minutes + "分";
        }
        if (days==0&&hours==0){
            badTime= minutes + "分"+second+"秒";
        }
        if (days==0&&hours==0&&minutes==0){
            badTime= second+"秒";
        }

        return badTime;
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

    /**
     * 解压tar.gz文件夹
     * @param outputFolderPath 解压路径
     * @param inputFilePath 压缩包文件路径
     */

    public static void decompression(String inputFilePath,String outputFolderPath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(inputFilePath);
        GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(gzipInputStream);

        TarArchiveEntry entry;
        while ((entry = tarArchiveInputStream.getNextTarEntry()) != null) {
            String entryName = entry.getName();
            File outputFile = new File(outputFolderPath + entryName);

            if (entry.isDirectory()) {
                outputFile.mkdirs();
                continue;
            }

            File parentDir = outputFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = tarArchiveInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();
        }

        tarArchiveInputStream.close();
        gzipInputStream.close();
        fileInputStream.close();
    }

    /**
     * 返回系统时间
     * @param type 时间类型 1.(yyyy-MM-dd HH:mm:ss) 2.(yyyy-MM-dd) 3.(HH:mm:ss) 4.([format]) 5.(HH:mm)
     * @return 时间
     */
    public static String date(int type, Date date){
        switch (type) {
            case 2 -> {
                return new SimpleDateFormat("yyyy-MM-dd").format(date);
            }
            case 3 -> {
                return new SimpleDateFormat("HH:mm:ss").format(date);
            }
            case 4 -> {
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                return "[" + format + "]" + "  ";
            }
            case 5 -> {
                return new SimpleDateFormat("HH:mm").format(date);
            }
            default -> {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            }
        }
    }


    /**
     * SHA256 加密
     * @param input 加密内容
     * @return 时间
     */
    public static String generateSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    /**
     * 读取信息
     * @param file
     * @return
     */
    public static String readFile( File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(inputStream.available());
            BufferedInputStream in = new BufferedInputStream(inputStream);
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            String string = bos.toString();

            return string;
        }catch (Exception e){
           throw  new SystemException("读取信息失败");
        }
    }

    /**
     * 获取当前服务器的ip
     * @return
     */
    public static String getServerIp(){
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
        return ip;
    }

}
