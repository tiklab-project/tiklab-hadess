package com.doublekit.oms.activity.controller;

import com.doublekit.activity.model.CashVolume;
import com.doublekit.activity.model.CashVolumeQuery;
import com.doublekit.activity.service.CashVolumeService;
import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.core.page.Pagination;
import com.doublekit.rpc.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * CashVolumeController
 */
@RestController
@RequestMapping("/cashVolume")
@Api(name = "CashVolumeController",desc = "现金卷活动管理")
public class CashVolumeController {

    private static Logger logger = LoggerFactory.getLogger(CashVolumeController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private CashVolumeService cashVolumeService;

    @RequestMapping(path="/createCashVolume",method = RequestMethod.POST)
    @ApiMethod(name = "createCashVolume",desc = "createCashVolume")
    @ApiParam(name = "cashVolume",desc = "cashVolume",required = true)
    public Result<String> createCashVolume(@RequestBody @NotNull @Valid CashVolume cashVolume){
        String id = cashVolumeService.createCashVolume(cashVolume);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateCashVolume",method = RequestMethod.POST)
    @ApiMethod(name = "updateCashVolume",desc = "updateCashVolume")
    @ApiParam(name = "cashVolume",desc = "cashVolume",required = true)
    public Result<Void> updateCashVolume(@RequestBody @NotNull @Valid CashVolume cashVolume){
        cashVolumeService.updateCashVolume(cashVolume);

        return Result.ok();
    }

    @RequestMapping(path="/deleteCashVolume",method = RequestMethod.POST)
    @ApiMethod(name = "deleteCashVolume",desc = "deleteCashVolume")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteCashVolume(@NotNull String id){
        cashVolumeService.deleteCashVolume(id);

        return Result.ok();
    }

    @RequestMapping(path="/findCashVolume",method = RequestMethod.POST)
    @ApiMethod(name = "findCashVolume",desc = "findCashVolume")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<CashVolume> findCashVolume(@NotNull String id){
        CashVolume cashVolume = cashVolumeService.findCashVolume(id);

        return Result.ok(cashVolume);
    }

    @RequestMapping(path="/findAllCashVolume",method = RequestMethod.POST)
    @ApiMethod(name = "findAllCashVolume",desc = "findAllCashVolume")
    public Result<List<CashVolume>> findAllCashVolume(){
        List<CashVolume> cashVolumeList = cashVolumeService.findAllCashVolume();

        return Result.ok(cashVolumeList);
    }

    @RequestMapping(path = "/findCashVolumeList",method = RequestMethod.POST)
    @ApiMethod(name = "findCashVolumeList",desc = "findCashVolumeList")
    @ApiParam(name = "cashVolumeQuery",desc = "cashVolumeQuery",required = true)
    public Result<List<CashVolume>> findCashVolumeList(@RequestBody @Valid @NotNull CashVolumeQuery cashVolumeQuery){
        List<CashVolume> cashVolumeList = cashVolumeService.findCashVolumeList(cashVolumeQuery);

        return Result.ok(cashVolumeList);
    }

    @RequestMapping(path = "/findCashVolumePage",method = RequestMethod.POST)
    @ApiMethod(name = "findCashVolumePage",desc = "findCashVolumePage")
    @ApiParam(name = "cashVolumeQuery",desc = "cashVolumeQuery",required = true)
    public Result<Pagination<CashVolume>> findCashVolumePage(@RequestBody @Valid @NotNull CashVolumeQuery cashVolumeQuery){
        Pagination<CashVolume> pagination = cashVolumeService.findCashVolumePage(cashVolumeQuery);

        return Result.ok(pagination);
    }

}
