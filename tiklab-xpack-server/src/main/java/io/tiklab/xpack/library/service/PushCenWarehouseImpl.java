package io.tiklab.xpack.library.service;

import io.tiklab.core.exception.SystemException;
import io.tiklab.xpack.library.model.*;
import io.tiklab.xpack.util.RepositoryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PushCenWarehouseImpl implements PushCenWarehouse {
    private static Logger logger = LoggerFactory.getLogger(PushCenWarehouseImpl.class);
    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    LibraryMavenService libraryMavenService;

    @Autowired
    PushLibraryService pushLibraryService;

    //推送结果
    public static Map<String , String> pushResultMap = new HashMap<>();

    @Override
    public void pushCentralWare(String libraryId, String type) {
        pushResultMap.remove(libraryId);
        if (("maven".equals(type))){
            mavenPush(libraryId);
        }
        if (("npm").equals(type)){
            npmPush(libraryId);
        }
    }

    @Override
    public String pushResult(String libraryId) {
        return pushResultMap.get(libraryId);
    }

    /**
     * maven  中央仓库提交
     * @param libraryId  制品id
     */
    public void mavenPush(String libraryId){
        try {
            //查询gpg 是否安装
            ProcessBuilder builder = new ProcessBuilder("which", "gpg");
            Integer code = exec(builder, "gpgIsInstall",libraryId);
            if (code==1){
              /*  //安装gpg
                ProcessBuilder builder01 = new ProcessBuilder("brew", "install", "gpg");
                exec(builder01,"gpgInstall",libraryId);*/

              /*  //生成gpg 密钥
                ProcessBuilder builder02 = new ProcessBuilder("gpg", "--gen-key");
                exec(builder02,"gpgSecretKey",versionId);*/
            }
           /* //生成gpg 密钥
            ProcessBuilder builder02 = new ProcessBuilder("gpg", "--gen-key");
            exec(builder02,"gpgSecretKey");*/

            //查询gpg 密钥
            ProcessBuilder builder03 = new ProcessBuilder("gpg", "--list-keys");
            exec(builder03,"gpgKey",libraryId);

            List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryId));
            if (CollectionUtils.isNotEmpty(libraryFileList)){
                List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> a.getFileName().endsWith(".jar"))
                        .sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
                LibraryFile libraryFile = libraryFiles.get(0);

                List<LibraryMaven> mavenList = libraryMavenService.findLibraryMavenList(new LibraryMavenQuery().setLibraryId(libraryFile.getLibrary().getId()));
                LibraryMaven libraryMaven = mavenList.get(0);

                String durl;
                if (StringUtils.isNotEmpty(libraryFile.getSnapshotVersion())){
                     durl="https://s01.oss.sonatype.org/content/repositories/snapshots";
                }else {
                    durl="https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/";
                }
                for (LibraryFile libraryFile1:libraryFiles){

                }
                //执行
                ProcessBuilder builder04 = new ProcessBuilder(
                        "mvn",
                        "gpg:sign-and-deploy-file",
                        "-Dgpg.executable=/opt/homebrew/bin/gpg",
                        "-Durl="+durl,
                        "-DrepositoryId=sonatype-snapshots",
                        "-DgroupId="+libraryMaven.getGroupId(),
                        "-DartifactId="+libraryMaven.getArtifactId(),
                        "-Dversion="+libraryFile.getLibraryVersion().getVersion(),
                        "-Dpackaging=jar",
                        "-Dfile="+libraryFile.getFileUrl()
                );
                Integer pushCode = exec(builder04, "push", libraryId);
                if (pushCode!=0){
                    this.updatePushLibrary(libraryId,"fail");
                    pushResultMap.put(libraryId,"推送失败");
                    return;
                }
                this.updatePushLibrary(libraryId,"succeed");
                pushResultMap.put(libraryId,"succeed");
            }
        } catch (Exception e) {
            this.updatePushLibrary(libraryId,"fail");
            pushResultMap.put(libraryId,"推送失败");
            throw  new SystemException("maven推送失败:"+e.getMessage());
        }
    }

    /**
     * npm  中央仓库提交
     * @param libraryId  制品id
     */
    public void npmPush(String libraryId) {

        List<LibraryFile> fileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryId));
        if (CollectionUtils.isNotEmpty(fileList)){
            //查询最新的版本
            List<LibraryFile> libraryFiles = fileList.stream().sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
            LibraryFile libraryFile = libraryFiles.get(0);
            String extractPath = libraryFile.getFileUrl().substring(0, libraryFile.getFileUrl().lastIndexOf(libraryFile.getFileName()));
            try {
                // 解压
                ProcessBuilder decompression = new ProcessBuilder("tar", "-xzf", libraryFile.getFileUrl(), "-C", extractPath);
                Process process = decompression.start();
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    extractPath = extractPath + "package";

                    RepositoryUtil.modifyPackageJson(extractPath,"https://registry.npmjs.org");

                    //解压包路径
                    String npmCommand = "npm publish";
                    String registryUrl = "https://registry.npmjs.org/";
                    String username = "tiklab";
                    String password = "Darth2020...";
                    ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", npmCommand);
                    processBuilder.directory(new File(extractPath));
                    // 设置环境变量传递用户信息
                    processBuilder.environment().put("NPM_USERNAME", username);
                    processBuilder.environment().put("NPM_PASSWORD", password);
                    processBuilder.environment().put("NPM_REGISTRY", registryUrl);
                    Integer pushCode = exec(processBuilder, "pushNpmCommand", libraryId);
                    if (pushCode==0){
                        pushResultMap.put(libraryId,"succeed");
                        this.updatePushLibrary(libraryId,"succeed");
                    }
                    /**
                     *  删除解压后的文件
                     */
                    FileUtils.deleteDirectory(new File(extractPath));
                }else {
                    this.updatePushLibrary(libraryId,"fail");
                    pushResultMap.put(libraryId,"解压文件失败");
                    throw new SystemException("npm推送解压tgz包失败");
                }
            } catch (IOException | InterruptedException e) {
                this.updatePushLibrary(libraryId,"fail");
                pushResultMap.put(libraryId,"解压文件失败");
               throw new SystemException(e.getMessage());
            }
        }
    }


    /**
     * 执行 命令
     * @param builder
     * @param  type
     * @return
     */
    public Integer exec( ProcessBuilder builder,String type,String libraryId) throws IOException, InterruptedException {
        Process process = builder.start();

        // 获取命令行输出
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        // 读取命令行输出
        StringBuilder excOutput = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            logger.info(type+"执行命令日志01:"+line);
            excOutput.append(line);
        }

        StringBuilder output = new StringBuilder();
        InputStream errorStream = process.getErrorStream();
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            logger.info(type+"执行命令日志:"+errorLine);
            output.append(errorLine);
        }
        // 等待命令执行完成
        int exitCode = process.waitFor();
        logger.info(type+"执行命令code:"+exitCode);

        // 检查命令的退出码和输出来确定gpg是否安装
        if (("gpgIsInstall").equals(type)){
            if (exitCode == 0 && output.length() > 0) {
              return 0;
            } else {
               return 1;
            }
        }
        if ("pushNpmCommand".equals(type)){
            if ( output.toString().contains("code E403")) {
                this.updatePushLibrary(libraryId,"fail");
                pushResultMap.put(libraryId,"403 推送中央仓库的版本不能重复且必须大于仓库中存在的版本");
            }
        }

        return exitCode;
    }

    /**
     * 执行 命令
     * @param LibraryId 制品id
     * @param  resultState  结果状态
     * @return
     */
    public void updatePushLibrary(String LibraryId,String resultState){
        List<PushLibrary> pushLibraryList = pushLibraryService.findPushLibraryList(new PushLibraryQuery().setLibraryId(LibraryId));
        PushLibrary pushLibrary = pushLibraryList.get(0);
        pushLibrary.setLastPushTime(new Timestamp(System.currentTimeMillis()));
        pushLibrary.setLastPushResult(resultState);

        pushLibraryService.updatePushLibrary(pushLibrary);
    }
}
