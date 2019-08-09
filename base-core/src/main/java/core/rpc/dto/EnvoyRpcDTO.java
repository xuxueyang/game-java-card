package core.rpc.dto;

import dist.ItemConstants;

import java.io.Serializable;

public class EnvoyRpcDTO implements Serializable{
    private String id;
    private Long metaEnvoyId;
    private String roomId;
    private ItemConstants.Attribute attribute;//属性(code)
    private ItemConstants.Race race;//种族(code)
    private ItemConstants.Grade grade;//品级

    private Integer attack;//攻击力
    private Integer incrAttack;//成长攻击力
    private Integer defense;//防御
    private Integer incrDefense;//成长防御
    private Integer hp;//血量
    private Integer incrHp;//成长血量
    private Integer move;//移动力
    private Integer attackDistance;//攻击距离
    private Integer criticalRate;//暴击率
    private Integer x;//坐标
    private Integer y;//坐标
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMetaEnvoyId() {
        return metaEnvoyId;
    }

    public void setMetaEnvoyId(Long metaEnvoyId) {
        this.metaEnvoyId = metaEnvoyId;
    }

    public ItemConstants.Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(ItemConstants.Attribute attribute) {
        this.attribute = attribute;
    }

    public ItemConstants.Race getRace() {
        return race;
    }

    public void setRace(ItemConstants.Race race) {
        this.race = race;
    }

    public ItemConstants.Grade getGrade() {
        return grade;
    }

    public void setGrade(ItemConstants.Grade grade) {
        this.grade = grade;
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

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
