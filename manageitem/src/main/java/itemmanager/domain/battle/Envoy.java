package itemmanager.domain.battle;

import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

// 棋子类
@Entity
@Table(name = "it_envoy")
public class Envoy extends BaseEntity {

    @Column(nullable = false,name = "attribute",length = 5)
    private Integer attribute;//属性(code)

    @Transient
    private Integer attributeName;//属性(code)

    @Column(nullable = false,name = "race",length = 5)
    private Integer race;//种族(code)
    @Transient
    private Integer raceName;//属性(code)

    @Column(nullable = false,name = "name",length = 5)
    private String name;//名字

    @Column(nullable = false,name = "icon",length = 255)
    private String icon;//头像

    @Column(nullable = false,name = "pinJi",length = 5)
    private Integer pinJi;//品级

    @Transient
    private String pinJiName;//品级

    @Column(nullable = false,name = "xingChenZhi",length = 5)
    private Integer xingChenZhi;//星辰值

    @Column(nullable = false,name = "plusXingChenZhi",length = 5)
    private Integer plusXingChenZhi=20;//最大的星辰值

    @Column(nullable = false,name = "attack",length = 10)
    private Integer attack;
    @Column(nullable = false,name = "defense",length = 10)
    private Integer defense;
    @Column(nullable = false,name = "blood",length = 10)
    private Integer blood;
    @Column(nullable = false,name = "move",length = 10)
    private Integer move;
    @Column(nullable = false,name = "attackDistance",length = 10)
    private Integer attackDistance;

    @Column(nullable = false,name = "ct",length = 10)
    private Integer ct = 10;//暴击率

    @Column(nullable = false,name = "description",length = 255)
    private String description;

//    @Column(nullable = false,name = "level",length = 5)
//    private Integer level = 1;

//    1星辰力=14血量=4成长生命值=4基础攻击力=1成长攻击力=0.2移动力=0.1攻击距离=3防御=0.5成长防御
    //提取出等级，和努力值修正 TODO 还是在组卡组的时候切换专精1.2
    //以后再加努力值设定吧
//    private Integer plusAttack = 0;
//    private Integer plusDefense;
//    private Integer plusBlood;

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Integer getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(Integer attributeName) {
        this.attributeName = attributeName;
    }

    public Integer getRace() {
        return race;
    }

    public void setRace(Integer race) {
        this.race = race;
    }

    public Integer getRaceName() {
        return raceName;
    }

    public void setRaceName(Integer raceName) {
        this.raceName = raceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPinJi() {
        return pinJi;
    }

    public void setPinJi(Integer pinJi) {
        this.pinJi = pinJi;
    }

    public String getPinJiName() {
        return pinJiName;
    }

    public void setPinJiName(String pinJiName) {
        this.pinJiName = pinJiName;
    }

    public Integer getXingChenZhi() {
        return xingChenZhi;
    }

    public void setXingChenZhi(Integer xingChenZhi) {
        this.xingChenZhi = xingChenZhi;
    }

    public Integer getPlusXingChenZhi() {
        return plusXingChenZhi;
    }

    public void setPlusXingChenZhi(Integer plusXingChenZhi) {
        this.plusXingChenZhi = plusXingChenZhi;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getBlood() {
        return blood;
    }

    public void setBlood(Integer blood) {
        this.blood = blood;
    }

    public Integer getMove() {
        return move;
    }

    public void setMove(Integer move) {
        this.move = move;
    }

    public Integer getAttackDistance() {
        return attackDistance;
    }

    public void setAttackDistance(Integer attackDistance) {
        this.attackDistance = attackDistance;
    }

    public Integer getCt() {
        return ct;
    }

    public void setCt(Integer ct) {
        this.ct = ct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
