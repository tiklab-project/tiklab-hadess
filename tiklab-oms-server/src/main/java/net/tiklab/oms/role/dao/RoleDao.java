package  net.tiklab.oms.role.dao;

import  net.tiklab.core.page.Pagination;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
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
        QueryCondition queryCondition = QueryBuilders.createQuery(RoleEntity.class)
                .orders(roleQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,RoleEntity.class);
    }

    public Pagination<RoleEntity> findRolePage(RoleQuery roleQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(RoleEntity.class)
                .orders(roleQuery.getOrderParams())
                .pagination(roleQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,RoleEntity.class);
    }
}