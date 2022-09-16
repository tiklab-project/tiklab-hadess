package  net.tiklab.oms.role.service;


import  net.tiklab.core.page.Pagination;
import  net.tiklab.oms.role.model.RoleUser;
import  net.tiklab.oms.role.model.RoleUserQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* RoleUserService
*/
public interface RoleUserService {

    /**
    * 创建
    * @param roleUser
    * @return
    */
    String createRoleUser(@NotNull @Valid RoleUser roleUser);

    /**
    * 更新
    * @param roleUser
    */
    void updateRoleUser(@NotNull @Valid RoleUser roleUser);

    /**
    * 删除
    * @param id
    */
    void deleteRoleUser(@NotNull String id);

    RoleUser findOne(@NotNull String id);

    List<RoleUser> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    RoleUser findRoleUser(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<RoleUser> findAllRoleUser();

    /**
    * 查询列表
    * @param roleUserQuery
    * @return
    */
    List<RoleUser> findRoleUserList(RoleUserQuery roleUserQuery);

    /**
    * 按分页查询
    * @param roleUserQuery
    * @return
    */
    Pagination<RoleUser> findRoleUserPage(RoleUserQuery roleUserQuery);

}