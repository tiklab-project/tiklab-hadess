package io.tiklab.hadess.upload.common.response;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

public class ComposerResponse {

    /**
     * 返回上传错误信息
     * @param message 错误信息
     * @param  resp response
     * @param  code 错误code
     */
    public static void errorUpToClient(HttpServletResponse resp,int code ,String message) {
        try {
            resp.setStatus(code);
            resp.setContentType("application/json"); // 必须设置文本类型
            resp.setCharacterEncoding("UTF-8");              // 明确指定编码
            resp.getWriter().write(message);
            resp.getWriter().flush();
            resp.getWriter().close(); // 立即终止响应

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回下载错误信息
     * @param message 错误信息
     * @param  resp response
     * @param  code 错误code
     */
    public static void errorDoToClient(HttpServletResponse resp,int code ,String message) {
        try {
            // 构建Composer格式的错误JSON
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "error");
            errorResponse.put("message",message );
            // 设置响应头
            resp.setContentType("application/json");
            resp.setStatus(code);
            // 发送响应
            PrintWriter out = resp.getWriter();
            out.print(errorResponse.toString());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 正确返回
     * @param data 内容
     * @param  resp response
     */
    public static void correctToClient(HttpServletResponse resp,String data) {
        try {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(data);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
