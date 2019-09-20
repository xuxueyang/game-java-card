package roommanager.service.room.autochessroom;

import lombok.Data;

import java.util.List;

//棋子
@Data
class Chess {
    private static final int maxEquipNum =  3;
    public enum PositionType{
        WAIT,
        BATTLE,
        GLOBAL,//公共卡池
    }

    private NodeManager.Node node = null;
    private int mp;
    private int maxMp;
    private int hp;
    private int maxHp;
    private int level;
    private String id;
    private String metaChessId;
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
