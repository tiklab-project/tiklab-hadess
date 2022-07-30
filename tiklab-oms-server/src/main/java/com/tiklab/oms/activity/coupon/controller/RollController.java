package com.tiklab.oms.activity.coupon.controller;

import com.tiklab.postlink.annotation.Api;
import com.tiklab.postlink.annotation.ApiMethod;
import com.tiklab.postlink.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.promotion.coupon.model.Roll;
import com.tiklab.promotion.coupon.model.RollQuery;
import com.tiklab.promotion.coupon.service.RollService;
import com.tiklab.rpc.annotation.Reference;
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
