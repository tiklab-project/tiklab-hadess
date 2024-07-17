package io.thoughtware.hadess.upload.common.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.thoughtware.core.Result;
import io.thoughtware.hadess.upload.model.LibraryHelmClient;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GoResponse {


    /**
     * go类型制品 拉取返回类型
     * @param response response
     */
    public  static void goPull(Result<byte[]> result, String contextPath,HttpServletResponse response) throws IOException {
        if (result.getCode()==0){
            response.setStatus(HttpServletResponse.SC_OK);
            if (contextPath.endsWith(".zip")){
                response.setContentType("application/zip");
            }
            if (contextPath.endsWith(".list")){
                response.setContentType("application/json; charset=UTF-8");
            }
            if (contextPath.endsWith(".mod")){
                response.setContentType("text/plain; charset=UTF-8");
            }
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(result.getData());
            outputStream.close();
        }else {
            response.setStatus(result.getCode());
            response.setContentType("text/plain;charset=UTF-8");
            response.setHeader("X-Content-Type-Options", "nosniff");
            PrintWriter writer = response.getWriter();
            writer.print(result.getMsg());
            writer.close();
        }
    }
}
