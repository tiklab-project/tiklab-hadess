package com.tiklab.oms.role.service;


import com.tiklab.core.page.Pagination;
import com.tiklab.oms.role.model.Role;
import com.tiklab.oms.role.model.RoleQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* RoleService
*/
public interface RoleService {

    /**
    * 创建
    * @param role
    * @return
    */
    String createRole(@NotNull @Valid Role role);

    /**
    * 更新
    * @param role
    */
    void updateRole(@NotNull @Valid Role role);

    /**
    * 删除
    * @param id
    */
    void deleteRole(@NotNull String id);

    Role findOne(@NotNull String id);

    List<Role> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    Role findRole(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<Role> findAllRole();

    /**
    * 查询列表
    * @param roleQuery
    * @return
    */
    List<Role> findRoleList(RoleQuery roleQuery);

    /**
    * 按分页查询
    * @param roleQuery
    * @return
    */
    Pagination<Role> findRolePage(RoleQuery roleQuery);

}