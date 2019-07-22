package acct.domain;


import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by UKi_Hi on 2019/7/15.
 */
@Entity
@Table(name = "mg_account")
public class Account extends BaseEntity {
    @Column(name = "tel",length = 20)
    private String tel;
    @Column(name = "email",length = 20)
    private String email;
    @Column(name = "login_name",length = 20)
    private String loginName;
//    private String nickName;
    @Column(name = "password",length = 32)
    private String password;
    @Column(name = "salt",length = 32)
    private String salt;

    @Column(name = "from_type",length = 5)
    private String fromType;
    @Column(name = "object")
    private String object;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
