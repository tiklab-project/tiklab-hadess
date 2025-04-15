package io.tiklab.hadess.upload.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tiklab.core.Result;
import io.tiklab.hadess.common.RepositoryUtil;
import io.tiklab.hadess.common.UuidGenerator;
import io.tiklab.hadess.upload.model.error.DockerErrorResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

public class DockerResponse {


    /*
     * docker 服务端返回
     * */

    /**
     * 账号认证返回
     * @param result 返回结果
     * @param  response response
     */
    public  static void  dockerAccountVerify(Result<String> result, HttpServletResponse response) throws IOException {
        if (result.getCode()==0){
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().close();
        }else {
            if (result.getCode()==402){
                response.setHeader("WWW-Authenticate", "Basic realm=\"Docker Registry\", Bearer realm=\"Docker Registry\", service=\"registry.example.com\"");
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().close();
        }
    }


    /**
     * 账号认证返回
     * @param contextPath 请求路径
     * @param resultMap 返回结果
     * @param  response response
     */
    public static void dockerReadMirroring(String contextPath, Map<String, String> resultMap, HttpServletResponse response) throws IOException {

        String sha256 = contextPath.substring(contextPath.lastIndexOf("sha256"));
        String data = resultMap.get("data");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Content-Length",resultMap.get("size"));
        response.setHeader("Docker-Content-Digest", sha256);

        // 设置响应的内容类型和状态码
        if (contextPath.contains("/manifest")){
            response.setContentType("application/vnd.docker.distribution.manifest.v2+json");

            PrintWriter writer = response.getWriter();
            writer.write(resultMap.get("data"));
            writer.close();
        }else {
            if (data.startsWith("{")){
                response.setContentType("application/vnd.docker.container.image.v1+json");

                PrintWriter writer = response.getWriter();
                writer.write(data);
                writer.close();
            }else {
                //zip 拉取 边读边推
                response.setContentType("application/vnd.docker.image.rootfs.diff.tar.gzip");
                String url = resultMap.get("url");
                InputStream inputStream = new FileInputStream(new File(url));
                ServletOutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                // 关闭输入流和输出流
                inputStream.close();
                outputStream.close();
            }
        }
    }


    public static void dockerReadManifest(HttpServletResponse response,String contextPath,String data) throws IOException {
        long length = new File(contextPath).length();

        String sha256 = contextPath.substring(contextPath.lastIndexOf("sha256"));
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Docker-Content-Digest", sha256);
        response.setContentType("application/vnd.docker.distribution.manifest.v2+json");
        response.setHeader("Content-Length",String.valueOf(length));
        PrintWriter writer = response.getWriter();
        writer.write(data);
        writer.close();
    }


    /**
     * 拉取Manifests数据返回结果
     * @param resultMap 返回结果
     * @param method 请求方法 get、head
     * @param  response response
     */
    public static void dockerPullManifests(Map<String, String> resultMap,String method,HttpServletResponse response) throws IOException {
        if (("200").equals(resultMap.get("code"))){
            response.setContentType("application/vnd.docker.distribution.manifest.v2+json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("Docker-Content-Digest",resultMap.get("name"));
            PrintWriter writer = response.getWriter();
            writer.print(resultMap.get("data"));
            writer.close();
            //response.getWriter().close();
        }else {
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            response.setStatus(404);
            if (("GET").equals(method)){
                writer.print(resultMap.get("data"));
            }
            writer.close();
        }
    }


    /**
     * 拉取Sha25校验返回结果
     * @param result 返回结果
     * @param contextPath 请求路径
     * @param  response response
     */
    public static void DockerSha25Verify(Result result,String contextPath,HttpServletResponse response) throws IOException {
        if (result.getCode()==0){
            String substring = contextPath.substring(contextPath.indexOf("/") + 1);
            // 设置响应的状态码和内容类型
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("Docker-Content-Digest",substring);
            response.setHeader("Content-Length",result.getData().toString());
            response.setContentType("application/json");
            response.getWriter().close();
        }else {
            // 设置响应的状态码和内容类型
            response.setStatus(result.getCode());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            // 将 JSON 响应写入响应输出流
            PrintWriter writer = response.getWriter();
            writer.write(result.getMsg());
            writer.close();
        }
    }

    /**
     * 推送 创建会话
     * @param result 返回结果
     * @param contextPath 请求路径
     * @param  response response
     */
    public static void DockerCreateSession(Result result,String contextPath,HttpServletResponse response) throws IOException {
        if (result.getCode()==0){
            String num8 = UuidGenerator.gen(8);
            String lowerCaseNum = num8.toLowerCase();

            String num4 = UuidGenerator.gen(4);
            String lowerCase = num4.toLowerCase();

            String uploadID = UuidGenerator.gen(8) + "-" + UuidGenerator.gen(4) + "-" + lowerCase + "-" + lowerCaseNum;
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.setHeader("Location", contextPath + uploadID);
            response.setHeader("Docker-Upload-UUID", uploadID);
            response.setHeader("Range", "0-0");
            response.getWriter().close();
        }else {
            // 设置响应的状态码和内容类型
            response.setStatus(result.getCode());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            // 将 JSON 响应写入响应输出流
            PrintWriter writer = response.getWriter();
            writer.write(result.getMsg());
            writer.close();
        }
    }

    /**
     * 上传镜像层数据
     * @param result 返回结果
     * @param contextPath 请求路径
     * @param  response response
     */
    public static void DockerUploadBlobs(Result result, String contextPath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uploadID = contextPath.substring(contextPath.lastIndexOf("/") + 1);
        if (result.getCode()==0){
            String length = request.getHeader("content-length");
            //镜像文件大小区间
            int i = Integer.valueOf(length) - 1;
            String range="0-"+i;
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.setHeader("Location", contextPath);
            response.setHeader("Docker-Upload-UUID", uploadID);
            response.setHeader("Range", range);
            response.getWriter().close();
        }else {
            // 设置响应的状态码和内容类型
            response.setStatus(result.getCode());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            // 将 JSON 响应写入响应输出流
            PrintWriter writer = response.getWriter();
            writer.write(result.getMsg());
            writer.close();
        }
    }

    /**
     * 上传镜像层数据
     * @param tag 版本的数据
     * @param  response response
     */
    public static void dockerCreateTag(String tag,HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
        String sha256 = RepositoryUtil.sha256Encryption(tag);

        // 设置响应的内容类型和状态码
        response.setContentType("application/vnd.docker.distribution.manifest.v2+json");
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Docker-Content-Digest", "sha256:"+sha256);

        PrintWriter writer = response.getWriter();
        writer.write(tag);
        writer.close();
    }

    /**
     * 推送-创建文件
     * @param fileSize 文件大小
     * @param  response response
     */
    public static void dockerCreateFile(String fileSize,StringBuffer requestURL,String digest,HttpServletResponse response) throws IOException{
        String range="0-"+fileSize;
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Docker-Content-Digest", digest);

        String substring = requestURL.substring(0, requestURL.indexOf("uploads"));
        String location = substring + digest;
        response.setHeader("Location", location);
        response.setHeader("Content-Range", range);
        response.getWriter().close();
    }

    /**
     * 返回的失败结果
     * @param massage massage
     */
    public String errorToClient(String massage,String imageName,String version){
        DockerErrorResponse errorResponse = new DockerErrorResponse();

        DockerErrorResponse.ErrorDetail errorDetail = new DockerErrorResponse.ErrorDetail();
        errorDetail.setCode("MANIFEST_UNKNOWN");
        errorDetail.setMessage(massage);
        DockerErrorResponse.ErrorDetail.DetailItem detailItem = new DockerErrorResponse.ErrorDetail.DetailItem();
        detailItem.setName(imageName);
        detailItem.setTag(version);

        errorDetail.setDetail(Arrays.asList(detailItem));
        errorResponse.setErrors(Arrays.asList(errorDetail));

        //转json字符串
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(errorResponse);
            return s;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 读取blobs文件错误信息
     * @param data data
     * @param  response response
     */
    public static void errorMirroringData(HttpServletResponse response,String data) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        response.setStatus(404);
        writer.print(data);
        writer.close();
    }
}
