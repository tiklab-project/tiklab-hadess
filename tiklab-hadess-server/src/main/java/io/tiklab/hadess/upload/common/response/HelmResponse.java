package io.tiklab.hadess.upload.common.response;

import io.tiklab.core.Result;
import io.tiklab.core.exception.SystemException;
import io.tiklab.hadess.upload.model.LibraryHelmClient;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

public class HelmResponse {

    private static final long serialVersionUID = 1L;
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 1024 * 1;
    private static final String TEMP_DIR = "temp";


    /**
     * helm制品类型用户校验后Response返回数据
     * @param result  返回的code ：401、200
     * @param response
     */
    public  static void  helmRepVerify(Result<byte[]> result,HttpServletResponse response) throws IOException {
       if (result.getCode()==0){
           response.setStatus(200);
           response.setHeader("Content-Type", "text/x-yaml");
           byte[] data = result.getData();
           String s = new String(data, "UTF-8");
           ServletOutputStream outputStream = response.getOutputStream();
           outputStream.write(result.getData());
       }else {
           if (result.getCode() == 401) {
               response.setHeader("WWW-Authenticate", "BASIC realm=\"Hadess Repository Manager\"");
           }
           response.setHeader("X-Content-Type-Options", "nosniff");
           response.setContentType("text/plain; charset=UTF-8");
           //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           response.setStatus(result.getCode());

           PrintWriter writer = response.getWriter();
           writer.write(result.getMsg());
           writer.close();
       }
    }


    /**
     * helm制品类型Response返回数据
     * @param result
     */
    public  static void  helmRep(Result<String> result,HttpServletResponse response ) throws IOException {
          response.setContentType("application/json; charset=UTF-8");
          PrintWriter writer = response.getWriter();
          if (result.getCode()==0){
              writer.println(result.getData());
          }else {
              writer.println(result.getMsg());
          }
        writer.close();
    }




    /**
     * 获取 MIME Multipart Media 格式的 HTTP 请求的文件数据
     * @param request request
     */
    public  static LibraryHelmClient findMultipartFile(HttpServletRequest request) {
        LibraryHelmClient libraryHelm = new LibraryHelmClient();

        boolean multipartContent = ServletFileUpload.isMultipartContent(request);
        if (!multipartContent){
            return null;
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置文件存储内存的阈值
        factory.setSizeThreshold(MAX_MEMORY_SIZE);
        /*
         * 临时存储库，超过阈值，文件保存到这个目录下面，以防止内存溢出。
         * 临时存储库必须是一个存在的目录，并且必须有足够的空间来存储临时文件。如果临时目录不存在，或者没有足够的空间，那么 Apache Commons FileUpload 库会抛出一个 FileUploadException。
         * */
        factory.setRepository(new File(TEMP_DIR));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_MEMORY_SIZE);
        upload.setSizeMax(MAX_MEMORY_SIZE);
        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                String fieldName = item.getFieldName();
                String fileName = item.getName();
                String contentType = item.getContentType();
                boolean isInMemory = item.isInMemory();
                long sizeInBytes = item.getSize();
                InputStream inputStream = item.getInputStream();

                libraryHelm.setInputStream(inputStream);
                libraryHelm.setFileName(fileName);
                libraryHelm.setFileSize(sizeInBytes);
            }
        } catch (Exception e) {
            throw new SystemException(500,"获取上传信息失败："+e);
        }
        return libraryHelm;

    }

}
