package acct.domain;


import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by UKi_Hi on 2019/7/15.
 */
@Entity
@Table(name = "mg_user_related")
public class UserRelated extends BaseEntity {
    @Column(name = "user_id_a",length = 20)
    private Long userIdA;
    @Column(name = "user_id_b",length = 20)
    private Long userIdB;

    public Long getUserIdA() {
        return userIdA;
    }

    public void setUserIdA(Long userIdA) {
        this.userIdA = userIdA;
    }

    public Long getUserIdB() {
        return userIdB;
    }

    public void setUserIdB(Long userIdB) {
        this.userIdB = userIdB;
    }
}
