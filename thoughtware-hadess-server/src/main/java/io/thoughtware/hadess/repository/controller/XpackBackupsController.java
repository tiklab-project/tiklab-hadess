package io.thoughtware.hadess.repository.controller;

import io.thoughtware.hadess.repository.model.ExecLog;
import io.thoughtware.hadess.repository.model.XpackBackups;
import io.thoughtware.hadess.repository.service.XpackBackupsServer;
import io.thoughtware.core.Result;
import io.thoughtware.core.context.AppHomeContext;
import io.thoughtware.core.exception.SystemException;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;


@RestController
@RequestMapping("/xpackBackups")
@Api(name = "BackupsController",desc = "数据备份")
public class XpackBackupsController {

    @Autowired
    XpackBackupsServer backupsServer;

    @RequestMapping(path="/backupsExec",method = RequestMethod.POST)
    @ApiMethod(name = "backupsExec",desc = "执行备份")
    @ApiParam(name = "auth",desc = "auth",required = true)
    public Result<String> backupsExec(@RequestBody @NotNull @Valid XpackBackups backups){

        String exec = backupsServer.backupsExec(backups);

        return Result.ok(exec);
    }

    @RequestMapping(path="/findBackups",method = RequestMethod.POST)
    @ApiMethod(name = "findBackups",desc = "查询备份相关数据")
    public Result<XpackBackups> findBackups(){
        String appHome = AppHomeContext.getAppHome();
        XpackBackups backups=  backupsServer.findBackups();

        return Result.ok(backups);
    }

    @RequestMapping(path="/updateBackups",method = RequestMethod.POST)
    @ApiMethod(name = "updateBackups",desc = "修改备份相关数据")
    @ApiParam(name = "auth",desc = "auth",required = true)
    public Result<String> updateBackups(@RequestBody @NotNull @Valid XpackBackups backups){

        backupsServer.updateBackups(backups);

        return Result.ok();
    }


    @RequestMapping(path="/recoveryData",method = RequestMethod.POST)
    @ApiMethod(name = "recoveryData",desc = "数据恢复")
    @ApiParam(name = "fileName",desc = "恢复的文件名称",required = true)
    public Result<String> recoveryData(@NotNull String fileName){

        String exec = backupsServer.recoveryData(fileName);

        return Result.ok(exec);
    }

    @RequestMapping(path="/gainBackupsRes",method = RequestMethod.POST)
    @ApiMethod(name = "gainBackupsRes",desc = "获取备份或者数据恢复结果")
    @ApiParam(name = "type",desc = "backups、recovery",required = true)
    public Result<ExecLog> gainBackupsRes(@NotNull String type){

        ExecLog gainBackupsRes = backupsServer.gainBackupsRes(type);

        return Result.ok(gainBackupsRes);
    }

    @RequestMapping(path="/uploadBackups",method = RequestMethod.POST)
    @ApiMethod(name = "uploadBackups",desc = "上传备份数据")
    @ApiParam(name = "uploadFile",desc = "uploadFile",required = true)
    public Result<String> uploadBackups(@RequestParam("uploadFile") MultipartFile uploadFile){
        try {
            String fileName = uploadFile.getOriginalFilename();   //获取文件名字
            InputStream inputStream = uploadFile.getInputStream();
            backupsServer.uploadBackups(inputStream,fileName);
        } catch (IOException e) {
            throw new SystemException(e);
        }
        return Result.ok();
    }


}
