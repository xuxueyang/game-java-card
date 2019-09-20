package roommanager.service.room.autochessroom;

import lombok.Data;

@Data
public class ChessManager {
    private static final int[] ratio = new int[]{9,7,5,3,2};

    private static  String[] metaChessIds = new String[MetaChessName.values().length];
    {
        MetaChessName[] values = MetaChessName.values();
        for (int i = 0; i < values.length; i++) {
            metaChessIds[i] = values[i].name();
        }
    }

    //初始化卡池
    public void init(){


    }
    @Data
    private class MetaChess{
        private String id;
        private int level;
        private int maxMp;
        private int maxHp;
        private String skillId;//自带的技能
    }
    public static MetaChessName getByName(String metaChessId){
        for (MetaChessName metaChessName : MetaChessName.values()) {
            if(metaChessName.name().equals(metaChessId)){
                return metaChessName;
            }
        }
        return null;
    }
    protected  enum MetaChessName{
        ZHAO_YUN,
        LV_BU,
        LIU_BEI,
        GUAN_YU,
        ZHANG_FEI
    }
}
