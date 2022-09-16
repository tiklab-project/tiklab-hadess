package  net.tiklab.oms.role.dao;

import  net.tiklab.core.page.Pagination;
import  net.tiklab.oms.role.entity.RoleUserEntity;
import  net.tiklab.oms.role.model.RoleUserQuery;
import  net.tiklab.dal.jpa.JpaTemplate;
import  net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RoleUserDao
 */
@Repository
public class RoleUserDao{

    private static Logger logger = LoggerFactory.getLogger(RoleUserDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param roleUserEntity
     * @return
     */
    public String createRoleUser(RoleUserEntity roleUserEntity) {
        return jpaTemplate.save(roleUserEntity,String.class);
    }

    /**
     * 更新
     * @param roleUserEntity
     */
    public void updateRoleUser(RoleUserEntity roleUserEntity){
        jpaTemplate.update(roleUserEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteRoleUser(String id){
        jpaTemplate.delete(RoleUserEntity.class,id);
    }

    public void deleteRoleUser(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public RoleUserEntity findRoleUser(String id){
        return jpaTemplate.findOne(RoleUserEntity.class,id);
    }

    /**
    * findAllRoleUser
    * @return
    */
    public List<RoleUserEntity> findAllRoleUser() {
        return jpaTemplate.findAll(RoleUserEntity.class);
    }

    public List<RoleUserEntity> findRoleUserList(List<String> idList) {
        return jpaTemplate.findList(RoleUserEntity.class,idList);
    }

    public List<RoleUserEntity> findRoleUserList(RoleUserQuery roleUserQuery) {
        return jpaTemplate.findList(roleUserQuery,RoleUserEntity.class);
    }

    public Pagination<RoleUserEntity> findRoleUserPage(RoleUserQuery roleUserQuery) {
        return jpaTemplate.findPage(roleUserQuery,RoleUserEntity.class);
    }
}