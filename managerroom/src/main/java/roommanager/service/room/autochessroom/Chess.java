package roommanager.service.room.autochessroom;

import lombok.Data;
import roommanager.service.room.autochessroom.entity.MetaChess;

import java.util.List;

//棋子
@Data
class Chess {
    public Chess(PositionType positionType){
        this.positionType = positionType;
    }
    private static final int maxEquipNum =  3;

    public void setByMetaChess(MetaChess metaChess) {
        this.maxHp = metaChess.getMaxHp();
        this.maxMp = metaChess.getMaxMp();
        this.mp = metaChess.getMp();
        this.hp = metaChess.getHp();
        this.level = metaChess.getInitLevel();
        this.skill = Skill.getSkillById(metaChess.getSkillId());

    }

    public enum PositionType{
        WAIT,
        BATTLE,
        GLOBAL,//公共卡池
        OTHER_GLOBAL,//选秀卡池，独立算
    }

    private NodeManager.Node node = null;
    private int mp;
    private String metaChessId;
    private int hp;
    private int level = 1;
    private String id;
    private String name;
    private int maxMp;
    private int maxHp;
    private Skill skill;
    private Equip[] equips = new Equip[maxEquipNum];
    private PositionType positionType;



    public boolean putToNode(NodeManager.Node node){
        if(node.isCanUse()){
            this.node = node;
            return true;
        }else{
            return false;
        }
    }

}
