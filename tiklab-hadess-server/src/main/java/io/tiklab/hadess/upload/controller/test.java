package io.tiklab.hadess.upload.controller;



import io.tiklab.hadess.common.RepositoryUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;


public class test {

    public static void main(String[] args) throws Exception {
        String s = RepositoryUtil.SHA1Encryption("/Users/limingliang/Downloads/tiklab_test.zip");
        System.out.println("s:"+s);

    }

}
