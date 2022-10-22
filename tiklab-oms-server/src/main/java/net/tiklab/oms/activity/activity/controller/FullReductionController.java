package  net.tiklab.oms.activity.activity.controller;

import  net.tiklab.promotion.activity.model.FullReduction;
import  net.tiklab.promotion.activity.model.FullReductionQuery;
import  net.tiklab.promotion.activity.service.FullReductionService;
import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.rpc.annotation.Reference;
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
 * FullReductionController
 */
@RestController
@RequestMapping("/fullReduction")
@Api(name = "FullReductionController",desc = "满减活动管理")
public class FullReductionController {

    private static Logger logger = LoggerFactory.getLogger(FullReductionController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private FullReductionService fullReductionService;

    @RequestMapping(path="/createFullReduction",method = RequestMethod.POST)
    @ApiMethod(name = "createFullReduction",desc = "createFullReduction")
    @ApiParam(name = "fullReduction",desc = "fullReduction",required = true)
    public Result<String> createFullReduction(@RequestBody @NotNull @Valid FullReduction fullReduction){
        String id = fullReductionService.createFullReduction(fullReduction);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateFullReduction",method = RequestMethod.POST)
    @ApiMethod(name = "updateFullReduction",desc = "updateFullReduction")
    @ApiParam(name = "fullReduction",desc = "fullReduction",required = true)
    public Result<Void> updateFullReduction(@RequestBody @NotNull @Valid FullReduction fullReduction){
        fullReductionService.updateFullReduction(fullReduction);

        return Result.ok();
    }

    @RequestMapping(path="/deleteFullReduction",method = RequestMethod.POST)
    @ApiMethod(name = "deleteFullReduction",desc = "deleteFullReduction")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteFullReduction(@NotNull String id){
        fullReductionService.deleteFullReduction(id);

        return Result.ok();
    }

    @RequestMapping(path="/findFullReduction",method = RequestMethod.POST)
    @ApiMethod(name = "findFullReduction",desc = "findFullReduction")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<FullReduction> findFullReduction(@NotNull String id){
        FullReduction fullReduction = fullReductionService.findFullReduction(id);

        return Result.ok(fullReduction);
    }

    @RequestMapping(path="/findAllFullReduction",method = RequestMethod.POST)
    @ApiMethod(name = "findAllFullReduction",desc = "findAllFullReduction")
    public Result<List<FullReduction>> findAllFullReduction(){
        List<FullReduction> fullReductionList = fullReductionService.findAllFullReduction();

        return Result.ok(fullReductionList);
    }

    @RequestMapping(path = "/findFullReductionList",method = RequestMethod.POST)
    @ApiMethod(name = "findFullReductionList",desc = "findFullReductionList")
    @ApiParam(name = "fullReductionQuery",desc = "fullReductionQuery",required = true)
    public Result<List<FullReduction>> findFullReductionList(@RequestBody @Valid @NotNull FullReductionQuery fullReductionQuery){
        List<FullReduction> fullReductionList = fullReductionService.findFullReductionList(fullReductionQuery);

        return Result.ok(fullReductionList);
    }

    @RequestMapping(path = "/findFullReductionPage",method = RequestMethod.POST)
    @ApiMethod(name = "findFullReductionPage",desc = "findFullReductionPage")
    @ApiParam(name = "fullReductionQuery",desc = "fullReductionQuery",required = true)
    public Result<Pagination<FullReduction>> findFullReductionPage(@RequestBody @Valid @NotNull FullReductionQuery fullReductionQuery){
        Pagination<FullReduction> pagination = fullReductionService.findFullReductionPage(fullReductionQuery);

        return Result.ok(pagination);
    }

}
