package itemmanager.domain.battle;

import core.core.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 棋子：
 * 限制：
 * 1。技能数目理论上无限，但是目前可以有多个
 * 2。棋子使用的时候需要消耗星辰值（如果死亡替换，那么不需要消耗）
 * 3。棋子上场后，会进入休眠，下一回合才会可以移动
 * 4。上场后需不需要继承buff？
 *
 * 属性
 * 1。名字、2。攻击力 3。血量 4。防御力 5。附加的额外buff 6。技能 7。品级 8。消耗的星辰值 9。介绍 10.移动力
 *
 * 以后拓展：
 * 1。等级
 * 2。种族
 * 3。 所属属性
 *
 * PS：应该区分平时状态和非战斗状态，以及区分具体玩家类和抽象类
 *
 */
// 棋子类
@Entity
@Data
@Table(name = "it_envoy")
public class Envoy extends BaseEntity {

    @Column(nullable = false,name = "attribute",length = 5)
    private Integer attribute;//属性(netty.code)

//    @Transient
//    private String attributeName;//属性(netty.code)

//    @Column(nullable = false,name = "race",length = 5)
//    private Integer race;//种族(netty.code)
//    @Transient
//    private String raceName;//种族名字(netty.code)

    @Column(nullable = false,name = "name",length = 5)
    private String name;//名字

//    @Column(nullable = false,name = "icon",length = 255)
//    private String icon = "";//头像

    @Column(nullable = false,name = "grade",length = 5)
    private Integer grade;//品级

//    @Transient
//    private String gradeName;//品级

    @Column(nullable = false,name = "starForce",length = 5)
    private Integer starForce = 0;//星辰值

//    @Column(nullable = false,name = "maxPlusStarForce",length = 5)
//    private Integer maxPlusStarForce=20;//最大的星辰值

    @Column(nullable = false,name = "attack",length = 10)
    private Integer attack = 0;//攻击力
//    @Column(nullable = false,name = "incrAttack",length = 10)
//    private Integer incrAttack = 0;//成长攻击力

    @Column(nullable = false,name = "defense",length = 10)
    private Integer defense = 0;//防御
//    @Column(nullable = false,name = "incrDefense",length = 10)
//    private Integer incrDefense = 0;//成长防御
    @Column(nullable = false,name = "hp",length = 10)
    private Integer hp = 0;//血量
//    @Column(nullable = false,name = "incrHp",length = 10)
//    private Integer incrHp = 0;//成长血量
    @Column(nullable = false,name = "move",length = 10)
    private Integer move = 0;//移动力
    @Column(nullable = false,name = "attackDistance",length = 10)
    private Integer attackDistance = 0;//攻击距离

    @Column(nullable = false,name = "criticalRate",length = 10)
    private Integer criticalRate = 10;//暴击率

    @Column(nullable = false,name = "desc",length = 255)
    private String desc= "";

    @Column(nullable = false,name = "link_skill",length = 255)
    private String linkSkills = "";//逗号分割技能ID
//    @Column(nullable = false,name = "designDescription",length = 255)
//    private String designDescription= "";
//    @Column(nullable = false,name = "jsonMap",length = 255)
//    private String jsonMap = "";
//    @Column(nullable = false,name = "level",length = 5)
//    private Integer level = 1;

//    1星辰力=14血量=4成长生命值=4基础攻击力=1成长攻击力=0.2移动力=0.1攻击距离=3防御=0.5成长防御
    //提取出等级，和努力值修正 TODO 还是在组卡组的时候切换专精1.2
    //以后再加努力值设定吧
//    private Integer plusAttack = 0;
//    private Integer plusDefense;
//    private Integer plusBlood;

}
