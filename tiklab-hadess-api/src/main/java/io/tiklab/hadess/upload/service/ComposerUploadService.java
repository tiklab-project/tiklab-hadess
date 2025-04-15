package io.tiklab.hadess.upload.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ComposerUploadService {
    void downloadPackage(HttpServletRequest request, HttpServletResponse response);


    void uploadPackage(HttpServletRequest request, HttpServletResponse response);
}
