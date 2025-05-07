package io.tiklab.hadess.upload.common.response;

import io.tiklab.core.Result;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GenericResponse {

    /**
     * 基础的返回类型
     * @param result result
     */
    public  static void  basicsRep(Result<byte[]> result,HttpServletResponse response ) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        if (result.getCode()==0){
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(result.getData());
            outputStream.close();
        }else {
          /*  PrintWriter out = response.getWriter();
            // 文件存在，发送文件内容
            String errorMessage = "Error: File not found - file";
            out.print("HTTP/1.1 404 Not Found\r\n");
            out.print("Content-Type: text/plain\r\n");
            out.print("Content-Length: "  +errorMessage.length()+ "\r\n");
            out.print("\r\n"); // 头部结束的空行
            out.print(errorMessage);
            out.flush();*/
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            PrintWriter writer = response.getWriter();
            writer.println(result.getMsg());
            writer.close();
        }
    }

    /**
     * 拉取返回错误信息
     * @param response response
     */
    public  static void  errorToClient(HttpServletResponse response,String msg)  {
        try {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            PrintWriter writer = response.getWriter();
            writer.println(msg);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 拉取返回
     * @param response response
     */
    public  static void  correctToClient(HttpServletResponse response,byte[] data )  {
        try {
            response.setContentType("application/json; charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(data);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void correctToClient(HttpServletResponse resp,String message) {
        try {
            resp.getWriter().write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
