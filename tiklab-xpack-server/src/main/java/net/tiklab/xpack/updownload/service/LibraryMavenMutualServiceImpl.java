package net.tiklab.xpack.updownload.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.user.user.model.User;
import net.tiklab.xpack.library.model.Library;
import net.tiklab.xpack.library.model.LibraryFile;
import net.tiklab.xpack.library.model.LibraryVersion;
import net.tiklab.xpack.library.service.LibraryFileService;
import net.tiklab.xpack.library.service.LibraryMavenService;
import net.tiklab.xpack.library.service.LibraryService;
import net.tiklab.xpack.library.service.LibraryVersionService;
import net.tiklab.xpack.repository.model.Repository;
import net.tiklab.xpack.repository.model.RepositoryQuery;
import net.tiklab.xpack.repository.service.RepositoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class LibraryMavenMutualServiceImpl implements LibraryMavenMutualService {
    private static Logger logger = LoggerFactory.getLogger(LibraryMavenMutualServiceImpl.class);
    @Value("${repository.library:null}")
    String repositoryLibrary;

    @Autowired
    LibraryService libraryService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    LibraryMavenService libraryMavenService;


    @Override
    public void mavenSubmit(String contextPath, InputStream inputStream) throws IOException {
        String url = StringUtils.substringBeforeLast(contextPath, "/");
        String path=repositoryLibrary+url;
        logger.warn("传入路径repositoryLibrary:{}",repositoryLibrary);
        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        test01(inputStream);
       /* String filePath=repositoryLibrary+contextPath;
        File file = new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        //用字节流写入
        FileOutputStream fos = new FileOutputStream(filePath);
        byte[] b = new byte[1024];
        while ((inputStream.read(b)) != -1) {
            fos.write(b);// 写入数据
        }
        inputStream.close();
        fos.close();// 保存数据*/

     /*   if (endsWith){
            //jar文件用FileOutputStream 写入
            FileOutputStream fos = new FileOutputStream(filePath);
            byte[] b = new byte[1024];
            while ((inputStream.read(b)) != -1) {
                fos.write(b);// 写入数据
            }
            inputStream.close();
            fos.close();// 保存数据
        }else {
            String line=null;
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            //写入内容
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            while((line=reader.readLine())!=null){
                writer.write(line+"\n");
            }
            writer.close();
        }*/
        //创建制品
        librarySplice(contextPath);
    }

    @Override
    public byte[] mavenInstall(String contextPath) {
        String mavenInstall = contextPath.substring(contextPath.indexOf("maven-install")+13);
        String fileUrl=repositoryLibrary+"/test"+mavenInstall;
        try {
            byte[] bytes = readFileContent(fileUrl);
            return bytes;
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *  拉取后-读取文件内容
     * @param filePath: 文件内容
     * @return
     */
    public byte[] readFileContent(String filePath)  throws IOException {
        File f = new File(filePath);
        long length = f.length();
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        byte[] bytes = bos.toByteArray();
        String s = new String(bytes, "UTF-8");
        return bytes;
    }

    /**
     *  制品创建、修改
     * @param contextPath     全路径
     * @return
     */
    public void librarySplice(String contextPath){

        String document = contextPath.substring(contextPath.lastIndexOf("/") + 1);
        int indexOf = document.indexOf("maven-metadata");
        if (indexOf==-1){
            String[]  single=contextPath.split("/");
            int length = single.length;
            String repositoryName = single[3];
            String libraryName = single[length - 3];
            String version = single[length - 2];

            //查询制品库是否存在
            List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName(repositoryName));
            if (CollectionUtils.isNotEmpty(repositoryList)){
                //创建制品
                Library library = libraryService.createLibraryData(libraryName, "maven",repositoryList.get(0));
                //制品版本创建、修改

                LibraryVersion libraryVersion = new LibraryVersion();
                libraryVersion.setLibrary(library);
                libraryVersion.setRepository(repositoryList.get(0));
                libraryVersion.setVersion(version);
                libraryVersion.setLibraryType("maven");
                libraryVersion.setSize("100mb");
                User user = new User();
                user.setId("111111");
                libraryVersion.setUser(user);
                String libraryVersionId =libraryVersionService.libraryVersionSplice(libraryVersion);


                //截取文件名称
                String fileName = contextPath.substring(contextPath.lastIndexOf("/") + 1);
                //截取文件路径
                String fileUrl = StringUtils.substringBeforeLast(contextPath, "/");
                LibraryFile libraryFile = new LibraryFile();
                libraryFile.setLibrary(library);
                libraryFile.setFileName(fileName);
                libraryFile.setFileSize("1kb");
                libraryFile.setFileUrl(repositoryLibrary+fileUrl);
                //制品文件
               libraryFileService.libraryFileSplice(libraryFile,libraryVersionId);

                // 制品maven
                libraryMavenService.libraryMavenSplice(libraryName,single,library);
            }

        }
    }

    public void test01(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(inputStream.available());
        BufferedInputStream in = new BufferedInputStream(inputStream);
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        String toString = bos.toString();
        System.out.println("");
    }


}
