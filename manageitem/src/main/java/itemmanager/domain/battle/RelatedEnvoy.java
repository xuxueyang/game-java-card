package itemmanager.domain.battle;


import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//玩家擁有的棋子
@Entity
@Table(name = "it_related_envoy")
public class RelatedEnvoy extends BaseEntity {
    //    1星辰力=14血量=4成长生命值=4基础攻击力=1成长攻击力=0.2移动力=0.1攻击距离=3防御=0.5成长防御
    @Column(nullable = false,name = "userId",length = 30)
    private Long userId;
    @Column(nullable = false,name = "envoyId",length = 30)
    private Long envoyId;
    @Column(nullable = false,name = "level",length = 5)
    private Integer level = 1;
    @Column(nullable = false,name = "plusAttack",length = 5)
    private Integer plusAttack = 0;
    @Column(nullable = false,name = "plusDefense",length = 5)
    private Integer plusDefense = 0;
    @Column(nullable = false,name = "plusBlood",length = 5)
    private Integer plusHp = 0;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEnvoyId() {
        return envoyId;
    }

    public void setEnvoyId(Long envoyId) {
        this.envoyId = envoyId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPlusAttack() {
        return plusAttack;
    }

    public void setPlusAttack(Integer plusAttack) {
        this.plusAttack = plusAttack;
    }

    public Integer getPlusDefense() {
        return plusDefense;
    }

    public void setPlusDefense(Integer plusDefense) {
        this.plusDefense = plusDefense;
    }

    public Integer getPlusHp() {
        return plusHp;
    }

    public void setPlusHp(Integer plusHp) {
        this.plusHp = plusHp;
    }
}
