package io.tiklab.hadess.upload.service;

import io.tiklab.core.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

public interface GenericUploadService {


    /**
     * generic提交
     * @param request request
     * @param   response response
     * @return
     */
     void GenericUpload(HttpServletRequest request, HttpServletResponse response);

    /**
     * generic下载
     * @param   request request
     * @param response response
     * @return
     */
    void GenericDownload(HttpServletRequest request, HttpServletResponse response);


}
