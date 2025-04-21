package io.tiklab.hadess.common;

import io.tiklab.core.exception.SystemException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {


    /**
     *  读取文件信息并写入response
     *  @param file     文件
     * @return
     */
    public static void readFileData(File file, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();

        InputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        // 关闭输入流和输出流
        inputStream.close();
        outputStream.close();
    }


    /**
     *  读取输入流中的数据
     * @param  inputStream 数据流
     * @return  data 读取的数据
     */
    public static  String readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(inputStream.available());
        BufferedInputStream in = new BufferedInputStream(inputStream);
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        return bos.toString();
    }


    /**
     * copy文件内容
     * @param inputStream 内容流
     * @param folderPath 文件需要复制到的地址
     * @param fileName 文件名字
     */
    public static Path copyFileData(InputStream inputStream, String folderPath, String fileName)  {
        try {
            // 规范化路径并创建目录（如果不存在）
            Path dirPath = Paths.get(folderPath);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // 构建目标文件路径
            Path filePath = dirPath.resolve(fileName);

            // 使用 try-with-resources 确保流关闭，并直接复制输入流到文件
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath;
        }catch (IOException e){
            throw new SystemException(HadessFinal.WRITE_EXCEPTION,"写入数据失败") ;
        }
    }


    /**
     * 写入string到文件路径中
     * @param content 内容
     * @param filePath 文件路径
     */
    public static void writeStringToFile(String content, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,false))) {
            writer.write(content);
        }
    }


    /**
     * 读取zip压缩包里面的文件内容
     * @param zipFilePath 压缩包地址
     * @param fileNameInZip 文件名字
     */
    public static String readFileInZip(String zipFilePath,String fileNameInZip){
        try (

           ZipFile zipFile = new ZipFile(zipFilePath)) {
            ZipEntry entry = zipFile.getEntry(fileNameInZip);
            if (entry != null) {
                StringBuilder fileContent = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line).append(System.lineSeparator()); // 添加内容和换行符
                    }
                }
                return fileContent.toString();
            } else {
                return "500";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(HadessFinal.READ_LOCAL_EXCEPTION,e.getMessage()) ;
        }
    }

}
