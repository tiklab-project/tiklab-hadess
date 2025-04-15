package io.tiklab.hadess.upload.controller;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;


public class test {

    public static void main(String[] args) throws Exception {

        File inputFile = new File("path/to/your/file.html");
        InputStream inputStream = new ByteArrayInputStream(getHtml().getBytes());
        Document document = Jsoup.parse(getHtml());
      

        // 修改 <title> 内容
        Element title = document.selectFirst("title");
        if (title != null) {
            title.text("Updated HTML Page");
        }

        // 修改 <h1> 内容
        Element h1 = document.selectFirst("h1");
        if (h1 != null) {
            h1.text("Greetings, World!");
        }

        // 添加新的 <a> 标签到 <body>
        Element body = document.body();
        body.appendChild(new Element("br")); // 添加换行
        Element newLink = new Element("a").attr("href", "https://example.com").text("Visit Example.com");
        body.appendChild(newLink);

        // 输出修改后的 HTML
        System.out.println(document.html());
    }
    public static String getHtml(){
        return  """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                   <META name="pypi:repository-version" content="1.0"/>
                    <title>Links for tensorflow</title>
                </head>
                <body>
                    <h1>Links for hadess</h1>
                    <a href="../../packages/tiklab/1.0.0/tiklab-1.0.0-py3-none-any.whl#sha256=ea78a0a7fd9b63a80a0acd5b5a651b08515c6bbc0581ff27964b7a497bcb980f" >hadess</a>
                </body>
                </html>
                """;
    }
}
