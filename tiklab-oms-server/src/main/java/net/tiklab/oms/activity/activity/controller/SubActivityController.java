package  net.tiklab.oms.activity.activity.controller;

import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.promotion.activity.model.SubActivity;
import  net.tiklab.promotion.activity.model.SubActivityQuery;
import  net.tiklab.promotion.activity.service.SubActivityService;
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
 * SubActivityController
 */
@RestController
@RequestMapping("/subActivity")
@Api(name = "SubActivityController",desc = "SubActivityController")
public class SubActivityController {

    private static Logger logger = LoggerFactory.getLogger(SubActivityController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private SubActivityService subActivityService;

    @RequestMapping(path="/createSubActivity",method = RequestMethod.POST)
    @ApiMethod(name = "createSubActivity",desc = "createSubActivity")
    @ApiParam(name = "subActivity",desc = "subActivity",required = true)
    public Result<String> createSubActivity(@RequestBody @NotNull @Valid SubActivity subActivity){
        String id = subActivityService.createSubActivity(subActivity);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateSubActivity",method = RequestMethod.POST)
    @ApiMethod(name = "updateSubActivity",desc = "updateSubActivity")
    @ApiParam(name = "subActivity",desc = "subActivity",required = true)
    public Result<Void> updateSubActivity(@RequestBody @NotNull @Valid SubActivity subActivity){
        subActivityService.updateSubActivity(subActivity);

        return Result.ok();
    }

    @RequestMapping(path="/deleteSubActivity",method = RequestMethod.POST)
    @ApiMethod(name = "deleteSubActivity",desc = "deleteSubActivity")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteSubActivity(@NotNull String id){
        subActivityService.deleteSubActivity(id);

        return Result.ok();
    }

    @RequestMapping(path="/findSubActivity",method = RequestMethod.POST)
    @ApiMethod(name = "findSubActivity",desc = "findSubActivity")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<SubActivity> findSubActivity(@NotNull String id){
        SubActivity subActivity = subActivityService.findSubActivity(id);

        return Result.ok(subActivity);
    }

    @RequestMapping(path="/findAllSubActivity",method = RequestMethod.POST)
    @ApiMethod(name = "findAllSubActivity",desc = "findAllSubActivity")
    public Result<List<SubActivity>> findAllSubActivity(){
        List<SubActivity> subActivityList = subActivityService.findAllSubActivity();

        return Result.ok(subActivityList);
    }

    @RequestMapping(path = "/findSubActivityList",method = RequestMethod.POST)
    @ApiMethod(name = "findSubActivityList",desc = "findSubActivityList")
    @ApiParam(name = "subActivityQuery",desc = "subActivityQuery",required = true)
    public Result<List<SubActivity>> findSubActivityList(@RequestBody @Valid @NotNull SubActivityQuery subActivityQuery){
        List<SubActivity> subActivityList = subActivityService.findSubActivityList(subActivityQuery);

        return Result.ok(subActivityList);
    }

    @RequestMapping(path = "/findSubActivityPage",method = RequestMethod.POST)
    @ApiMethod(name = "findSubActivityPage",desc = "findSubActivityPage")
    @ApiParam(name = "subActivityQuery",desc = "subActivityQuery",required = true)
    public Result<Pagination<SubActivity>> findSubActivityPage(@RequestBody @Valid @NotNull SubActivityQuery subActivityQuery){
        Pagination<SubActivity> pagination = subActivityService.findSubActivityPage(subActivityQuery);

        return Result.ok(pagination);
    }

}
