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
    private String attributeName;//属性(code)

    @Column(nullable = false,name = "race",length = 5)
    private Integer race;//种族(code)
    @Transient
    private String raceName;//种族名字(code)

    @Column(nullable = false,name = "name",length = 5)
    private String name;//名字

    @Column(nullable = false,name = "icon",length = 255)
    private String icon;//头像

    @Column(nullable = false,name = "grade",length = 5)
    private Integer grade;//品级

    @Transient
    private String gradeName;//品级

    @Column(nullable = false,name = "starForce",length = 5)
    private Integer starForce;//星辰值

    @Column(nullable = false,name = "maxPlusStarForce",length = 5)
    private Integer maxPlusStarForce=20;//最大的星辰值

    @Column(nullable = false,name = "attack",length = 10)
    private Integer attack;//攻击力
    @Column(nullable = false,name = "incrAttack",length = 10)
    private Integer incrAttack;//成长攻击力

    @Column(nullable = false,name = "defense",length = 10)
    private Integer defense;//防御
    @Column(nullable = false,name = "incrDefense",length = 10)
    private Integer incrDefense;//成长防御
    @Column(nullable = false,name = "hp",length = 10)
    private Integer hp;//血量
    @Column(nullable = false,name = "incrHp",length = 10)
    private Integer incrHp;//成长血量
    @Column(nullable = false,name = "move",length = 10)
    private Integer move;//移动力
    @Column(nullable = false,name = "attackDistance",length = 10)
    private Integer attackDistance;//攻击距离

    @Column(nullable = false,name = "criticalRate",length = 10)
    private Integer criticalRate = 10;//暴击率

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

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Integer getRace() {
        return race;
    }

    public void setRace(Integer race) {
        this.race = race;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getStarForce() {
        return starForce;
    }

    public void setStarForce(Integer starForce) {
        this.starForce = starForce;
    }

    public Integer getMaxPlusStarForce() {
        return maxPlusStarForce;
    }

    public void setMaxPlusStarForce(Integer maxPlusStarForce) {
        this.maxPlusStarForce = maxPlusStarForce;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getIncrAttack() {
        return incrAttack;
    }

    public void setIncrAttack(Integer incrAttack) {
        this.incrAttack = incrAttack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getIncrDefense() {
        return incrDefense;
    }

    public void setIncrDefense(Integer incrDefense) {
        this.incrDefense = incrDefense;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getIncrHp() {
        return incrHp;
    }

    public void setIncrHp(Integer incrHp) {
        this.incrHp = incrHp;
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

    public Integer getCriticalRate() {
        return criticalRate;
    }

    public void setCriticalRate(Integer criticalRate) {
        this.criticalRate = criticalRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
