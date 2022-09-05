package com.tiklab.oms.role.service;

import com.tiklab.core.page.Pagination;
import com.tiklab.core.page.PaginationBuilder;
import com.tiklab.oms.role.dao.RoleDao;
import com.tiklab.oms.role.entity.RoleEntity;
import com.tiklab.oms.role.model.Role;
import com.tiklab.oms.role.model.RoleQuery;

import com.tiklab.beans.BeanMapper;
import com.tiklab.join.JoinTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
* RoleServiceImpl
*/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createRole(@NotNull @Valid Role role) {
        RoleEntity roleEntity = BeanMapper.map(role, RoleEntity.class);

        return roleDao.createRole(roleEntity);
    }

    @Override
    public void updateRole(@NotNull @Valid Role role) {
        RoleEntity roleEntity = BeanMapper.map(role, RoleEntity.class);

        roleDao.updateRole(roleEntity);
    }

    @Override
    public void deleteRole(@NotNull String id) {
        roleDao.deleteRole(id);
    }

    @Override
    public Role findOne(String id) {
        RoleEntity roleEntity = roleDao.findRole(id);

        Role role = BeanMapper.map(roleEntity, Role.class);
        return role;
    }

    @Override
    public List<Role> findList(List<String> idList) {
        List<RoleEntity> roleEntityList =  roleDao.findRoleList(idList);

        List<Role> roleList =  BeanMapper.mapList(roleEntityList,Role.class);
        return roleList;
    }

    @Override
    public Role findRole(@NotNull String id) {
        Role role = findOne(id);

        joinTemplate.joinQuery(role);

        return role;
    }

    @Override
    public List<Role> findAllRole() {
        List<RoleEntity> roleEntityList =  roleDao.findAllRole();

        List<Role> roleList =  BeanMapper.mapList(roleEntityList,Role.class);

        joinTemplate.joinQuery(roleList);

        return roleList;
    }

    @Override
    public List<Role> findRoleList(RoleQuery roleQuery) {
        List<RoleEntity> roleEntityList = roleDao.findRoleList(roleQuery);

        List<Role> roleList = BeanMapper.mapList(roleEntityList,Role.class);

        joinTemplate.joinQuery(roleList);

        return roleList;
    }

    @Override
    public Pagination<Role> findRolePage(RoleQuery roleQuery) {
        Pagination<RoleEntity>  pagination = roleDao.findRolePage(roleQuery);

        List<Role> roleList = BeanMapper.mapList(pagination.getDataList(),Role.class);

        joinTemplate.joinQuery(roleList);

        return PaginationBuilder.build(pagination,roleList);
    }
}