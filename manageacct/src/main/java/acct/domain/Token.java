package acct.domain;

import core.core.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by UKi_Hi on 2018/5/29.
 */

@Entity
@Table(name = "mg_token")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Token extends BaseEntity implements Serializable{

    @Column(name = "ACCESS_TOKEN",length = 45)
    private String accesstoken;
    @Column(name = "REFRESH_TOKEN",length = 45)
    private String refreshtoken;
    @Column(name = "VALID_TIME",length = 20)
    private Long validtime;


    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }

    public Long getValidtime() {
        return validtime;
    }

    public void setValidtime(Long validtime) {
        this.validtime = validtime;
    }


}
