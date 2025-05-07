package io.tiklab.hadess.upload.common.response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

public class NugetResponse {

    /**
     * 返回校验错误信息
     * @param message 错误信息
     * @param  resp response
     */
    public static void errorAuthToClient(HttpServletResponse resp ,String message) {
        try {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            resp.setContentType("text/plain; charset=utf-8"); // 必须设置文本类型
            resp.setHeader("WWW-Authenticate","BASIC realm=\"Hadess Repository Manager\"");
            resp.setCharacterEncoding("UTF-8");
            // 明确指定编码
            resp.getWriter().write(message);
            resp.getWriter().flush();
            resp.getWriter().close(); // 立即终止响应

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回错误信息
     * @param message 错误信息
     * @param  resp response
     */
    public static void errorToClient(HttpServletResponse resp,int coed ,String message) {
        try {
            resp.setStatus(coed);
            resp.setContentType("application/json");
            resp.getWriter().write(
                    "{\n" +
                            "\"status\": \"error\""+
                            "  \"error\": \""+message+"\",\n" +
                            "  \"details\": \""+message+"\",\n" +
                            "}"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


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
