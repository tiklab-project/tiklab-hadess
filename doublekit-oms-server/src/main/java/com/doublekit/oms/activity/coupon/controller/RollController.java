package com.doublekit.oms.activity.coupon.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.core.page.Pagination;
import com.doublekit.promotion.coupon.model.Roll;
import com.doublekit.promotion.coupon.model.RollQuery;
import com.doublekit.promotion.coupon.service.RollService;
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
 * RollController
 */
@RestController
@RequestMapping("/roll")
@Api(name = "RollController",desc = "RollController")
public class RollController {

    private static Logger logger = LoggerFactory.getLogger(RollController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private RollService rollService;

    @RequestMapping(path="/createRoll",method = RequestMethod.POST)
    @ApiMethod(name = "createRoll",desc = "createRoll")
    @ApiParam(name = "roll",desc = "roll",required = true)
    public Result<String> createRoll(@RequestBody @NotNull @Valid Roll roll){
        String id = rollService.createRoll(roll);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRoll",method = RequestMethod.POST)
    @ApiMethod(name = "updateRoll",desc = "updateRoll")
    @ApiParam(name = "roll",desc = "roll",required = true)
    public Result<Void> updateRoll(@RequestBody @NotNull @Valid Roll roll){
        rollService.updateRoll(roll);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRoll",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRoll",desc = "deleteRoll")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteRoll(@NotNull String id){
        rollService.deleteRoll(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRoll",method = RequestMethod.POST)
    @ApiMethod(name = "findRoll",desc = "findRoll")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Roll> findRoll(@NotNull String id){
        Roll roll = rollService.findRoll(id);

        return Result.ok(roll);
    }

    @RequestMapping(path="/findAllRoll",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRoll",desc = "findAllRoll")
    public Result<List<Roll>> findAllRoll(){
        List<Roll> rollList = rollService.findAllRoll();

        return Result.ok(rollList);
    }

    @RequestMapping(path = "/findRollList",method = RequestMethod.POST)
    @ApiMethod(name = "findRollList",desc = "findRollList")
    @ApiParam(name = "rollQuery",desc = "rollQuery",required = true)
    public Result<List<Roll>> findRollList(@RequestBody @Valid @NotNull RollQuery rollQuery){
        List<Roll> rollList = rollService.findRollList(rollQuery);

        return Result.ok(rollList);
    }

    @RequestMapping(path = "/findRollPage",method = RequestMethod.POST)
    @ApiMethod(name = "findRollPage",desc = "findRollPage")
    @ApiParam(name = "rollQuery",desc = "rollQuery",required = true)
    public Result<Pagination<Roll>> findRollPage(@RequestBody @Valid @NotNull RollQuery rollQuery){
        Pagination<Roll> pagination = rollService.findRollPage(rollQuery);

        return Result.ok(pagination);
    }

}