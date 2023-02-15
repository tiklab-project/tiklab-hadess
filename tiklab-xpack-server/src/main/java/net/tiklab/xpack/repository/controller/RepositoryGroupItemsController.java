package net.tiklab.xpack.repository.controller;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.xpack.repository.model.RepositoryGroupItems;
import net.tiklab.xpack.repository.model.RepositoryGroupItemsQuery;
import net.tiklab.xpack.repository.service.RepositoryGroupItemsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * RepositoryGroupItemsController
 */
@RestController
@RequestMapping("/repositoryGroupItems")
@Api(name = "RepositoryGroupItemsController",desc = "组合库和本地远程库关联管理")
public class RepositoryGroupItemsController {

    private static Logger logger = LoggerFactory.getLogger(RepositoryGroupItemsController.class);

    @Autowired
    private RepositoryGroupItemsService repositoryGroupItemsService;

    @RequestMapping(path="/createRepositoryGroupItems",method = RequestMethod.POST)
    @ApiMethod(name = "createRepositoryGroupItems",desc = "createRepositoryGroupItems")
    @ApiParam(name = "repositoryGroupItems",desc = "repositoryGroupItems",required = true)
    public Result<String> createRepositoryGroupItems(@RequestBody @NotNull @Valid RepositoryGroupItems repositoryGroupItems){
        String id = repositoryGroupItemsService.createRepositoryGroupItems(repositoryGroupItems);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRepositoryGroupItems",method = RequestMethod.POST)
    @ApiMethod(name = "updateRepositoryGroupItems",desc = "updateRepositoryGroupItems")
    @ApiParam(name = "repositoryGroupItems",desc = "repositoryGroupItems",required = true)
    public Result<Void> updateRepositoryGroupItems(@RequestBody @NotNull @Valid RepositoryGroupItems repositoryGroupItems){
        repositoryGroupItemsService.updateRepositoryGroupItems(repositoryGroupItems);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRepositoryGroupItems",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRepositoryGroupItems",desc = "deleteRepositoryGroupItems")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteRepositoryGroupItems(@NotNull String id){
        repositoryGroupItemsService.deleteRepositoryGroupItems(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRepositoryGroupItems",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryGroupItems",desc = "findRepositoryGroupItems")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<RepositoryGroupItems> findRepositoryGroupItems(@NotNull String id){
        RepositoryGroupItems repositoryGroupItems = repositoryGroupItemsService.findRepositoryGroupItems(id);

        return Result.ok(repositoryGroupItems);
    }

    @RequestMapping(path="/findAllRepositoryGroupItems",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRepositoryGroupItems",desc = "findAllRepositoryGroupItems")
    public Result<List<RepositoryGroupItems>> findAllRepositoryGroupItems(){
        List<RepositoryGroupItems> repositoryGroupItemsList = repositoryGroupItemsService.findAllRepositoryGroupItems();

        return Result.ok(repositoryGroupItemsList);
    }

    @RequestMapping(path = "/findRepositoryGroupItemsList",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryGroupItemsList",desc = "findRepositoryGroupItemsList")
    @ApiParam(name = "repositoryGroupItemsQuery",desc = "repositoryGroupItemsQuery",required = true)
    public Result<List<RepositoryGroupItems>> findRepositoryGroupItemsList(@RequestBody @Valid @NotNull RepositoryGroupItemsQuery repositoryGroupItemsQuery){
        List<RepositoryGroupItems> repositoryGroupItemsList = repositoryGroupItemsService.findRepositoryGroupItemsList(repositoryGroupItemsQuery);

        return Result.ok(repositoryGroupItemsList);
    }

    @RequestMapping(path = "/findRepositoryGroupItemsPage",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryGroupItemsPage",desc = "findRepositoryGroupItemsPage")
    @ApiParam(name = "repositoryGroupItemsQuery",desc = "repositoryGroupItemsQuery",required = true)
    public Result<Pagination<RepositoryGroupItems>> findRepositoryGroupItemsPage(@RequestBody @Valid @NotNull RepositoryGroupItemsQuery repositoryGroupItemsQuery){
        Pagination<RepositoryGroupItems> pagination = repositoryGroupItemsService.findRepositoryGroupItemsPage(repositoryGroupItemsQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path="/compileRepositoryGroupItems",method = RequestMethod.POST)
    @ApiMethod(name = "compileRepositoryGroupItems",desc = "根据条件编辑组合库关联")
    @ApiParam(name = "repositoryGroupItems",desc = "repositoryGroupItems",required = true)
    public Result<String> compileRepositoryGroupItems(@RequestBody @NotNull @Valid RepositoryGroupItems repositoryGroupItems){
        String id = repositoryGroupItemsService.compileRepositoryGroupItems(repositoryGroupItems);

        return Result.ok(id);
    }
}
