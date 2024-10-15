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
            PrintWriter writer = response.getWriter();
            writer.println(result.getMsg());
            writer.close();
        }
    }


}
