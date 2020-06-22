package itemmanager.domain.battle;

import core.core.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 职业——以后会有一些职业卡牌
 */
@Entity
@Table(name = "it_profession")
@Data
public class Profession extends BaseEntity {
    @Column(nullable = false,name = "name",length = 20)
    private String name = "";

    @Column(nullable = false,name = "name",length = 20)
    private String desc = "";


    @Column(nullable = false,name = "link_skill",length = 255)
    private String linkSkills = "";//逗号分割技能ID
}
