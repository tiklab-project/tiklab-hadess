package io.thoughtware.hadess.upload.service;

import io.thoughtware.core.exception.SystemException;
import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.library.model.LibraryFileHand;
import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.hadess.repository.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class HandUploadServiceImpl implements HandUploadService {
    private static Logger logger = LoggerFactory.getLogger(HandUploadServiceImpl.class);

    @Value("${server.port:8080}")
    private String port;

    @Autowired
    XpackYamlDataMaService xpakYamlDataMaService;

    @Autowired
    RepositoryService repositoryService;


    //手动推送制品日志
    public static Map<String , String> handPushLog = new HashMap<>();

    @Override
    public byte[] fileRead(String requestURI) {

        try {
            File file = getFileUrl(requestURI);

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
        String serverIp = RepositoryUtil.getServerIp();
        String absoluteAddress="http://" + serverIp + ":" + port;
        return absoluteAddress;
    }

    @Override
    public String fileUpload(InputStream inputStream, String fileName) throws IOException {

        String folderPath=xpakYamlDataMaService.fileAddress();
        File folderfile = new File(folderPath);
        if (!folderfile.exists()){
            folderfile.mkdirs();
        }
        String filePath = folderfile + "/" + fileName;
        File file = new File(filePath);

        if (!file.exists()){
            file.createNewFile();

        }
        //用字节流写入文件
        FileOutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();

        return filePath;
    }


    @Override
    public String libraryHandPush(LibraryFileHand libraryFileHand)   {
        String loginId = LoginContext.getLoginId();
        handPushLog.remove(loginId+libraryFileHand.getRepositoryId());
        Repository repository = repositoryService.findOne(libraryFileHand.getRepositoryId());
        String repositoryUrl = repository.getRepositoryUrl();


        String path = repositoryUrl.replaceFirst("repository", "xpack/maven");
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable(){
            @Override
            public void run() {
                try {
                    Process process;
                    if (("generic").equals(libraryFileHand.getType())){
                        process= genericProcessBuilder(libraryFileHand,repositoryUrl) ;
                    }else {
                        //执行
                        ProcessBuilder  builder = new ProcessBuilder(
                                "mvn",
                                "deploy:deploy-file",
                                "-Durl="+path,
                                "-DgroupId="+libraryFileHand.getGroupId(),
                                "-DartifactId="+libraryFileHand.getArtifactId(),
                                "-Dversion="+libraryFileHand.getVersion(),
                                "-Dpackaging="+libraryFileHand.getPackaging(),
                                "-Dfile="+libraryFileHand.getFilePath(),
                                "-DgeneratePom="+libraryFileHand.getPom()
                        );
                        builder.command();
                         process = builder.start();
                        // 获取命令行输出
                        InputStream inputStream = process.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        // 读取maven命令行输出
                        StringBuilder excOutput = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            logger.info("手动推送"+libraryFileHand.getArtifactId()+":"+line);
                            excOutput.append(line);
                        }
                        handPushLog.put(loginId+libraryFileHand,excOutput.toString());
                    }


                    // 等待命令执行完成
                    int exitCode = process.waitFor();
                    if (exitCode==0){
                        handPushLog.put(loginId+libraryFileHand.getRepositoryId(),"succeed");
                    }else {
                        handPushLog.put(loginId+libraryFileHand.getRepositoryId(),"fail");
                    }
                }catch (Exception e){
                    handPushLog.put(loginId+libraryFileHand.getRepositoryId(),"fail");
                    logger.info("手动提交报错："+e.getMessage());
                }

            }});

        return "ok";
    }


    public String findHandPushResult(String repositoryId){
        String loginId = LoginContext.getLoginId();
        String result = handPushLog.get(loginId + repositoryId);
        return result;
    }

    public File getFileUrl(String requestURI){
        String[] split = requestURI.split("/");
        Integer indexes = split[1].length() + split[2].length() + 2;
        String url = requestURI.substring(indexes);

        File file = new File(xpakYamlDataMaService.repositoryAddress()+url);
        return file;
    }

    /**
     *  generic
     * @param repositoryUrl
     * @param libraryFileHand
     * @return
     */
    public Process genericProcessBuilder(LibraryFileHand libraryFileHand,String repositoryUrl) throws IOException {

        String path = repositoryUrl.replaceFirst("repository", "generic");

        String filePath = libraryFileHand.getFilePath();
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

        String lastPath = path + "/" + fileName + "?version=" + libraryFileHand.getVersion();

        Runtime runtime=Runtime.getRuntime();

        String command="curl -T "+libraryFileHand.getFilePath()+" -u "+libraryFileHand.getUser()+":xpackhand "+ lastPath;
        Process process = runtime.exec(command);
        //其他执行日志
        StringBuilder output = new StringBuilder();
        InputStream errorStream = process.getErrorStream();
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            logger.info("手动推送:"+errorLine);
            output.append(errorLine);
        }
        return process;
    }


}
