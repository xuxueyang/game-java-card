package itemmanager.domain.battle;

import core.core.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


// 卡牌类

/**
 *限制：
 * 1。卡牌类型 ——即时的，场地的，陷进谋略的
 * 2。需要消耗星辰值
 * 3。有基础消耗与理论消耗
 *
 *属性
 * 1。品级 2。消耗星辰值 3。jsonMap放一些特殊属性
 */
@Entity
@Table(name = "it_card")
@Data
public class Card extends BaseEntity {

    @Column(nullable = false,name = "grade",length = 10)
    private String grade;
    @Column(nullable = false,name = "name",length = 20)
    private String name;
//    @Column(nullable = false,name = "imgPath",length = 255)
//    private String imgPath;
    @Column(nullable = false,name = "effectDesc",length = 255)
    private String effectDesc;
//    @Column(nullable = false,name = "smallDescription",length = 255)
//    private String smallDescription;
    @Column(nullable = false,name = "feiyong",length = 10)
    private Integer starForce;
    @Column(nullable = false,name = "type",length = 10)
    private String type;
    @ApiModelProperty(value = "标记是不是职业卡牌，空不是，不为空，则是职业的ID")
    @Column(nullable = false,name = "classify",length = 10)
    private String classify;
    @Column(nullable = false,name = "effectId",length = 10)
    private Long effectId;

    @Column(nullable = false,name = "link_skill",length = 255)
    private String linkSkills = "";//逗号分割技能ID

    @Column(nullable = false,name = "jsonMap",length = 255)
    private String jsonMap;
}
