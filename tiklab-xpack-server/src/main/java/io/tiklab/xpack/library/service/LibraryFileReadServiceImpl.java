package io.tiklab.xpack.library.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class LibraryFileReadServiceImpl implements LibraryFileReadService{

    @Override
    public byte[] fileRead(String requestURI) {

        try {
            String[] split = requestURI.split("/");
            Integer indexes = split[1].length() + split[2].length() + 2;
            String url = requestURI.substring(indexes);
            File file = new File(url);

            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            byte[] bytes = bos.toByteArray();

            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
