package  net.tiklab.oms.role.service;

import  net.tiklab.core.page.Pagination;
import  net.tiklab.core.page.PaginationBuilder;
import  net.tiklab.oms.role.dao.RoleUserDao;
import  net.tiklab.oms.role.entity.RoleUserEntity;
import  net.tiklab.oms.role.model.RoleUser;
import  net.tiklab.oms.role.model.RoleUserQuery;

import  net.tiklab.beans.BeanMapper;
import  net.tiklab.join.JoinTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
* RoleUserServiceImpl
*/
@Service
public class RoleUserServiceImpl implements RoleUserService {

    @Autowired
    RoleUserDao roleUserDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createRoleUser(@NotNull @Valid RoleUser roleUser) {
        RoleUserEntity roleUserEntity = BeanMapper.map(roleUser, RoleUserEntity.class);

        return roleUserDao.createRoleUser(roleUserEntity);
    }

    @Override
    public void updateRoleUser(@NotNull @Valid RoleUser roleUser) {
        RoleUserEntity roleUserEntity = BeanMapper.map(roleUser, RoleUserEntity.class);

        roleUserDao.updateRoleUser(roleUserEntity);
    }

    @Override
    public void deleteRoleUser(@NotNull String id) {
        roleUserDao.deleteRoleUser(id);
    }

    @Override
    public RoleUser findOne(String id) {
        RoleUserEntity roleUserEntity = roleUserDao.findRoleUser(id);

        RoleUser roleUser = BeanMapper.map(roleUserEntity, RoleUser.class);
        return roleUser;
    }

    @Override
    public List<RoleUser> findList(List<String> idList) {
        List<RoleUserEntity> roleUserEntityList =  roleUserDao.findRoleUserList(idList);

        List<RoleUser> roleUserList =  BeanMapper.mapList(roleUserEntityList,RoleUser.class);
        return roleUserList;
    }

    @Override
    public RoleUser findRoleUser(@NotNull String id) {
        RoleUser roleUser = findOne(id);

        joinTemplate.joinQuery(roleUser);

        return roleUser;
    }

    @Override
    public List<RoleUser> findAllRoleUser() {
        List<RoleUserEntity> roleUserEntityList =  roleUserDao.findAllRoleUser();

        List<RoleUser> roleUserList =  BeanMapper.mapList(roleUserEntityList,RoleUser.class);

        joinTemplate.joinQuery(roleUserList);

        return roleUserList;
    }

    @Override
    public List<RoleUser> findRoleUserList(RoleUserQuery roleUserQuery) {
        List<RoleUserEntity> roleUserEntityList = roleUserDao.findRoleUserList(roleUserQuery);

        List<RoleUser> roleUserList = BeanMapper.mapList(roleUserEntityList,RoleUser.class);

        joinTemplate.joinQuery(roleUserList);

        return roleUserList;
    }

    @Override
    public Pagination<RoleUser> findRoleUserPage(RoleUserQuery roleUserQuery) {
        Pagination<RoleUserEntity>  pagination = roleUserDao.findRoleUserPage(roleUserQuery);

        List<RoleUser> roleUserList = BeanMapper.mapList(pagination.getDataList(),RoleUser.class);

        joinTemplate.joinQuery(roleUserList);

        return PaginationBuilder.build(pagination,roleUserList);
    }
}