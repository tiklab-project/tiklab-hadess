package io.tiklab.hadess.common;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.core.context.AppHomeContext;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.exception.SystemException;
import io.tiklab.hadess.upload.common.UploadTool;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
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
     * @ type 类型
     */
    public static String httpGet(String address,String type) throws Exception {

        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(30000); //设置连接超时时间 单位毫秒
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            if (("go").equals(type)){
                response.append(line).append("\n");
            }else {
                response.append(line) ;
            }
        }
        reader.close();
        conn.disconnect();
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
            File outputFile = new File(outputFolderPath +"/"+ entryName);

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
     * 返回今天星期几
     * @return 1: 周一 7:周天
     */
    public static int week() {
        Calendar calendar=Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (i == 0){
            return 7;
        }
        return i;
    }




    public static String   getSHA256ByPath(String filePath)  {

        try {
            MessageDigest digest  = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(filePath);
            FileChannel channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(8192); // 8 KB buffer
            while (channel.read(buffer) != -1) {
                buffer.flip();
                digest.update(buffer);
                buffer.clear();
            }
            byte[] hash = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("sha256加密失败："+e);
        }
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
           throw  new SystemException(HadessFinal.FILE_EXCEPTION,"读取信息失败");
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


    /**
     *通过字节计算大小
     * @param sizeByte 大小 单位字节
     * @return
     */
    public static String countStorageSize(long sizeByte){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (sizeByte==0L){
            return sizeByte+".00";
        }
        double num =(double) sizeByte / 1024;
        if (sizeByte<1048576){
            String KbNum = decimalFormat.format(num);
            return KbNum+"Kb";
        }
        //小于1G
        if (num<1048576){
            double l = (double) sizeByte / (1024 * 1024);
            String MB = decimalFormat.format(l);
            return MB+"MB";
        }
        //大于1G
        if (num>=1048576){
            double gbNum =(double) sizeByte / 1024/1024/1024;
            String GB = decimalFormat.format(gbNum);
            return GB+"GB";
        }
        return null;
    }

    /*
     * 获取磁盘空间大小
     * */
    public static float findDiskSize(String dir){
        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        while (folder.getParentFile() != null) {
            folder = folder.getParentFile();
        }
        String rootPath = folder.getPath();
        File root = new File(rootPath);
        long diskSpace =  root.getTotalSpace();
        float l = (float)diskSpace / (1024 * 1024 * 1024);
        // 使用 BigDecimal 控制小数位数
        BigDecimal decimalL = new BigDecimal(Float.toString(l));
        decimalL = decimalL.setScale(2, RoundingMode.HALF_UP);

        return decimalL.floatValue();
    }


    /**
     * 字符串转换成时间
     * @param time 时间字符串
     * @return 时间
     */
    public static Date StringChengeDate(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date targetTime;
        try {
            targetTime = sdf.parse(time);
        } catch (ParseException e) {
            throw new ApplicationException(HadessFinal.TIME_EXCEPTION,"时间转换失败，不是yyyy-MM-dd HH:mm:ss格式:"+time);
        }
        return targetTime;
    }

    /**
     *  读取文件信息
     *  @param file     文件
     * @return
     */
    public static byte[] readFileData(File file) throws IOException {
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

    }

    /**
     *  初始化helm的索引文件
     *  @param indexFilePath   索引文件路径
     */
    public static void initHelmIndexFile(String indexFilePath)  {
        File indexPathFile = new File(indexFilePath);
        if (!indexPathFile.exists()){
            try {
                indexPathFile.createNewFile();
                Map<String, Object> hashMap = new LinkedHashMap<>();
                hashMap.put("apiVersion","v1");
                hashMap.put("entries",new LinkedHashMap());
                hashMap.put("generated", Instant.now().toString());
                UploadTool.writeYamlFile(hashMap,indexFilePath);
            } catch (IOException e) {
                throw new RuntimeException("创建helm索引文件失败："+e);
            }
        }
    }

    /**
     *  256加密
     *  @param data   需要加密的内容
     */
    public static String sha256Encryption(String data) throws NoSuchAlgorithmException {
        // 创建 SHA-256 MessageDigest 实例
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // 计算哈希值
        byte[] hashBytes = digest.digest(data.getBytes());

        // 将字节数组转换为十六进制字符串
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return  hexString.toString();
    }

    /**
     *  获取随机数
     *  @param num   取摸数
     */
    public static int getRandomNum(int num) {
        // 获取当前时间的纳秒数
        long nanoTime = System.nanoTime();

        return (int) (nanoTime % num);
    }

}
