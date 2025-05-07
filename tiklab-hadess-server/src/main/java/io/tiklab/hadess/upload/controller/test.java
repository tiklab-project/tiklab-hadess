package io.tiklab.hadess.upload.controller;



import io.tiklab.hadess.common.RepositoryUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class test {

    public static void main(String[] args) throws Exception {

        LocalDate currentDate = LocalDate.now();
        LocalDate localDate = currentDate.minusDays(30);
        ZoneId zoneId = ZoneId.systemDefault(); // 使用系统默认时区
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId); // 转换为ZonedDateTime表示当天的开始时间
        long millis = zonedDateTime.toInstant().toEpochMilli(); // 转换为毫秒

        long l = System.currentTimeMillis();
        System.out.println();
    }

}
