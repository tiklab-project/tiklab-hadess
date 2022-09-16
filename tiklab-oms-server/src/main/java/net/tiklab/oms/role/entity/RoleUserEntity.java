package  net.tiklab.oms.role.entity;


import  net.tiklab.dal.jpa.annotation.Column;
import  net.tiklab.dal.jpa.annotation.Entity;
import  net.tiklab.dal.jpa.annotation.Id;
import  net.tiklab.dal.jpa.annotation.Table;

import java.io.Serializable;

@Entity
@Table(name="prc_role_user")
public class RoleUserEntity implements Serializable {

    @Id
    @Column(name = "id",length = 32)
    private String id;

    @Column(name = "role_id",length = 32)
    private String roleId;

    @Column(name = "user_id",length = 32)
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
