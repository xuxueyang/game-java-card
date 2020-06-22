package itemmanager.domain.battle;


import core.core.BaseEntity;
import core.protocol.AutoChessRoomProtocol;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table(name = "it_skill")
@Entity
public class Skill extends BaseEntity {

    @Column(nullable = false,name = "name",length = 20)
    private String name = "";

    @Column(nullable = false,name = "type",length = 20)
    private AutoChessRoomProtocol.SkillType type;

    @Column(nullable = false,name = "star_force",length = 20)
    private Integer starForce;//消耗星辰值

    @Column(nullable = false,name = "power",length = 20)
    private Integer power;//消耗行动力

    @Column(nullable = false,name = "code",length = 100)
    private String code;//唯一技能表示-》寻找效果code和函数
}
