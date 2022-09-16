package  net.tiklab.oms.role.controller;

import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.oms.role.model.Role;
import  net.tiklab.oms.role.model.RoleQuery;
import  net.tiklab.oms.role.service.RoleService;
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
 * RoleController
 */
@RestController
@RequestMapping("/role")
@Api(name = "RoleController",desc = "RoleController")
public class RoleController {

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @RequestMapping(path="/createRole",method = RequestMethod.POST)
    @ApiMethod(name = "createRole",desc = "createRole")
    @ApiParam(name = "role",desc = "role",required = true)
    public Result<String> createRole(@RequestBody @NotNull @Valid Role role){
        String id = roleService.createRole(role);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRole",method = RequestMethod.POST)
    @ApiMethod(name = "updateRole",desc = "updateRole")
    @ApiParam(name = "role",desc = "role",required = true)
    public Result<Void> updateRole(@RequestBody @NotNull @Valid Role role){
        roleService.updateRole(role);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRole",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRole",desc = "deleteRole")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteRole(@NotNull String id){
        roleService.deleteRole(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRole",method = RequestMethod.POST)
    @ApiMethod(name = "findRole",desc = "findRole")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Role> findRole(@NotNull String id){
        Role role = roleService.findRole(id);

        return Result.ok(role);
    }

    @RequestMapping(path="/findAllRole",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRole",desc = "findAllRole")
    public Result<List<Role>> findAllRole(){
        List<Role> roleList = roleService.findAllRole();

        return Result.ok(roleList);
    }

    @RequestMapping(path = "/findRoleList",method = RequestMethod.POST)
    @ApiMethod(name = "findRoleList",desc = "findRoleList")
    @ApiParam(name = "roleQuery",desc = "roleQuery",required = true)
    public Result<List<Role>> findRoleList(@RequestBody @Valid @NotNull RoleQuery roleQuery){
        List<Role> roleList = roleService.findRoleList(roleQuery);

        return Result.ok(roleList);
    }

    @RequestMapping(path = "/findRolePage",method = RequestMethod.POST)
    @ApiMethod(name = "findRolePage",desc = "findRolePage")
    @ApiParam(name = "roleQuery",desc = "roleQuery",required = true)
    public Result<Pagination<Role>> findRolePage(@RequestBody @Valid @NotNull RoleQuery roleQuery){
        Pagination<Role> pagination = roleService.findRolePage(roleQuery);

        return Result.ok(pagination);
    }

}
