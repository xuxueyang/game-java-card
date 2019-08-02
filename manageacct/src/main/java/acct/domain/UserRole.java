package acct.domain;


import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by UKi_Hi on 2019/7/15.
 */
@Entity
@Table(name = "mg_user_role")
public class UserRole extends BaseEntity {
    @Column(name = "user_id",length = 20)
    private Long userId;
    @Column(name = "role_id",length = 20)
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
