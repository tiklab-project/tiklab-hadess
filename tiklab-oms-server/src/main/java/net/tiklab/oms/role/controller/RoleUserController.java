package  net.tiklab.oms.role.controller;

import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.oms.role.model.RoleUser;
import  net.tiklab.oms.role.model.RoleUserQuery;
import  net.tiklab.oms.role.service.RoleUserService;
import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
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
 * RoleUserController
 */
@RestController
@RequestMapping("/roleUser")
@Api(name = "RoleUserController",desc = "RoleUserController")
public class RoleUserController {

    private static Logger logger = LoggerFactory.getLogger(RoleUserController.class);

    @Autowired
    private RoleUserService roleUserService;

    @RequestMapping(path="/createRoleUser",method = RequestMethod.POST)
    @ApiMethod(name = "createRoleUser",desc = "createRoleUser")
    @ApiParam(name = "roleUser",desc = "roleUser",required = true)
    public Result<String> createRoleUser(@RequestBody @NotNull @Valid RoleUser roleUser){
        String id = roleUserService.createRoleUser(roleUser);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRoleUser",method = RequestMethod.POST)
    @ApiMethod(name = "updateRoleUser",desc = "updateRoleUser")
    @ApiParam(name = "roleUser",desc = "roleUser",required = true)
    public Result<Void> updateRoleUser(@RequestBody @NotNull @Valid RoleUser roleUser){
        roleUserService.updateRoleUser(roleUser);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRoleUser",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRoleUser",desc = "deleteRoleUser")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteRoleUser(@NotNull String id){
        roleUserService.deleteRoleUser(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRoleUser",method = RequestMethod.POST)
    @ApiMethod(name = "findRoleUser",desc = "findRoleUser")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<RoleUser> findRoleUser(@NotNull String id){
        RoleUser roleUser = roleUserService.findRoleUser(id);

        return Result.ok(roleUser);
    }

    @RequestMapping(path="/findAllRoleUser",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRoleUser",desc = "findAllRoleUser")
    public Result<List<RoleUser>> findAllRoleUser(){
        List<RoleUser> roleUserList = roleUserService.findAllRoleUser();

        return Result.ok(roleUserList);
    }

    @RequestMapping(path = "/findRoleUserList",method = RequestMethod.POST)
    @ApiMethod(name = "findRoleUserList",desc = "findRoleUserList")
    @ApiParam(name = "roleUserQuery",desc = "roleUserQuery",required = true)
    public Result<List<RoleUser>> findRoleUserList(@RequestBody @Valid @NotNull RoleUserQuery roleUserQuery){
        List<RoleUser> roleUserList = roleUserService.findRoleUserList(roleUserQuery);

        return Result.ok(roleUserList);
    }

    @RequestMapping(path = "/findRoleUserPage",method = RequestMethod.POST)
    @ApiMethod(name = "findRoleUserPage",desc = "findRoleUserPage")
    @ApiParam(name = "roleUserQuery",desc = "roleUserQuery",required = true)
    public Result<Pagination<RoleUser>> findRoleUserPage(@RequestBody @Valid @NotNull RoleUserQuery roleUserQuery){
        Pagination<RoleUser> pagination = roleUserService.findRoleUserPage(roleUserQuery);

        return Result.ok(pagination);
    }

}
