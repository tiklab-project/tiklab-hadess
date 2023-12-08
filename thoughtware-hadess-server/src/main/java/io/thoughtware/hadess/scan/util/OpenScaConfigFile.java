package io.thoughtware.hadess.scan.util;


import com.alibaba.fastjson.JSONObject;
import io.thoughtware.core.context.AppHomeContext;
import io.thoughtware.core.exception.ApplicationException;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.List;

@Component
public class OpenScaConfigFile {

    @Value("${openscan_mvn.repo}")
    private String mvnRepo;

    @Value("${openscan_mvn.user}")
    private String mvnUser;

    @Value("${openscan_mvn.password}")
    private String mvnPassword;
    @Bean
    public void updateConfig() throws ParseException {
        if (StringUtils.isNotEmpty(mvnRepo)){
            String appHome = AppHomeContext.getAppHome();
            //String  appHome=new File(AppHomeContext.getAppHome()).getParentFile().getParent();
            String openscaFile = appHome + "/embbed/opensca-1.0.13/config.json";

            String fileData = gainFileData(new File(openscaFile));
            JSONObject jsonObject = JSONObject.parseObject(fileData);

            List<JSONObject> mvn = (List<JSONObject>) jsonObject.get("maven");
            for (JSONObject data:mvn){
                String repo = data.get("repo").toString();
                if (!ObjectUtils.isEmpty(repo)){
                    return;
                }
                data.put("repo",mvnRepo);
                data.put("user",mvnUser);
                data.put("password",mvnPassword);
            }
            jsonObject.put("maven",mvn);
            writeFile(new File(openscaFile),jsonObject.toString());
            System.out.println("");
        }
    }

    /**
     *  读取file 文件
     *  @param file     文件
     * @return
     */
    public String gainFileData(File file){
        try {
            FileInputStream inputStream = new FileInputStream(file);
            StringBuilder result = new StringBuilder();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = null;
            while ((lineTxt = bfr.readLine()) != null) {
                String a=lineTxt;
                result.append(lineTxt).append(System.lineSeparator());
            }
            String toString = result.toString();

            inputStream.close();
            return toString;
        }catch (IOException e){
            throw new ApplicationException(e.getMessage());
        }
    }


    /**
     *  写入文件
     *  @param file     文件
     * @return
     */
    public void writeFile(File file,String fileData){
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileData);
            fileWriter.close();
        }catch (Exception e){
            throw new ApplicationException(5000,e.getMessage());
        }
    }
}
