package io.thoughtware.hadess.upload.service;

import io.thoughtware.hadess.common.UserCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelmUploadServiceImpl implements HelmUploadService {

    @Autowired
    UserCheckService userCheckService;
    @Override
    public int userCheck(String userData) {
        try {
            userCheckService.dockerUserCheck(userData);
            return 200;
        }catch (Exception e){
            return 401;
        }
    }
}
