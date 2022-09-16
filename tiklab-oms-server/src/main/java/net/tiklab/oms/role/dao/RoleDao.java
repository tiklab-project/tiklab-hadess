package  net.tiklab.oms.role.dao;

import  net.tiklab.core.page.Pagination;
import  net.tiklab.oms.role.entity.RoleEntity;
import  net.tiklab.oms.role.model.RoleQuery;
import  net.tiklab.dal.jpa.JpaTemplate;
import  net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RoleDao
 */
@Repository
public class RoleDao{

    private static Logger logger = LoggerFactory.getLogger(RoleDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param roleEntity
     * @return
     */
    public String createRole(RoleEntity roleEntity) {
        return jpaTemplate.save(roleEntity,String.class);
    }

    /**
     * 更新
     * @param roleEntity
     */
    public void updateRole(RoleEntity roleEntity){
        jpaTemplate.update(roleEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteRole(String id){
        jpaTemplate.delete(RoleEntity.class,id);
    }

    public void deleteRole(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public RoleEntity findRole(String id){
        return jpaTemplate.findOne(RoleEntity.class,id);
    }

    /**
    * findAllRole
    * @return
    */
    public List<RoleEntity> findAllRole() {
        return jpaTemplate.findAll(RoleEntity.class);
    }

    public List<RoleEntity> findRoleList(List<String> idList) {
        return jpaTemplate.findList(RoleEntity.class,idList);
    }

    public List<RoleEntity> findRoleList(RoleQuery roleQuery) {
        return jpaTemplate.findList(roleQuery,RoleEntity.class);
    }

    public Pagination<RoleEntity> findRolePage(RoleQuery roleQuery) {
        return jpaTemplate.findPage(roleQuery,RoleEntity.class);
    }
}