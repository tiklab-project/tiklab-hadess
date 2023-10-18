package io.tiklab.xpack.library.controller;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;

public class test {

    public static void main(String[] args) throws IOException {
        String folderPath = "/Users/limingliang/tiklab/123/test"; // 要删除的文件夹路径

        try {
            FileUtils.deleteDirectory(new File(folderPath));
            System.out.println("文件夹删除成功！");
        } catch (IOException e) {
            System.out.println("文件夹删除失败：" + e.getMessage());
        }
    }
}
