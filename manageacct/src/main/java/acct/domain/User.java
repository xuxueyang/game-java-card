package acct.domain;


import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by UKi_Hi on 2019/7/15.
 */
@Entity
@Table(name = "mg_user")
public class User extends BaseEntity {
    @Column(name = "account_id",length = 20)
    private Long accountId;
    @Column(name = "nick_name",length = 20)
    private String nickName;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
