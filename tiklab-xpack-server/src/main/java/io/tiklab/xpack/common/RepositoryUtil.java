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

}
