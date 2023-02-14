package net.tiklab.xpack.updownload.tcpserver;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class MavenDeploy {

    public static void main(String[] args) {

            try {
                ServerSocket socket=new ServerSocket(8083);
                //2 获取链接过来的客户端对象；
                Socket client=socket.accept();
                String ip=client.getInetAddress().getHostAddress();
                //3 通过客户端获得输入流，读取发过来的数据；
                InputStream in=client.getInputStream();
                byte[] buf=new byte[1024];
                int len=in.read(buf);
                String text=new String(buf,0,len);
                System.out.println(ip+":"+text);
                //通过socket流将文本发送到客户端；
                OutputStream os=client.getOutputStream();
                os.write("收到了".getBytes());
                //关闭资源
                client.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
