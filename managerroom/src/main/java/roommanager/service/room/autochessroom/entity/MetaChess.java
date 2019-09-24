package roommanager.service.room.autochessroom.entity;

import core.protocol.AutoChessRoomProtocol;
import lombok.Data;


@Data
public class MetaChess{
    private String id;
    private String name;
    private int initLevel;
    private int maxMp;
    private int maxHp;
    private int mp;
    private int hp;
    private AutoChessRoomProtocol.SkillType skillType;//自带的技能
}