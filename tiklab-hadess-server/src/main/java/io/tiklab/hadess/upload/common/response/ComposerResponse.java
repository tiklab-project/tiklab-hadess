package io.tiklab.hadess.upload.common.response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ComposerResponse {

    /**
     * 返回错误信息
     * @param message 错误信息
     * @param  resp response
     * @param  code 错误code
     */
    public static void errorToClient(HttpServletResponse resp,int code ,String message) {
        try {
            //resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            resp.setStatus(code);
            resp.setContentType("text/plain; charset=utf-8"); // 必须设置文本类型
            resp.setCharacterEncoding("UTF-8");              // 明确指定编码
            resp.getWriter().write(message);
            resp.getWriter().flush();
            resp.getWriter().close(); // 立即终止响应

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
            //resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            writer.write(data);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
