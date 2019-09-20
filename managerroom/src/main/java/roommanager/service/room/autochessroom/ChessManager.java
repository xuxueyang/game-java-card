package roommanager.service.room.autochessroom;

import core.protocol.AutoChessRoomProtocol;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.HashSet;

@Data
public class ChessManager {
    private static final int[] ratio = new int[]{9,7,5,3,2};
    private static final int base = 5;

    private static  String[] metaChessIds = new String[MetaChessName.values().length];
    {
        MetaChessName[] values = MetaChessName.values();
        for (int i = 0; i < values.length; i++) {
            metaChessIds[i] = values[i].name();
        }
    }
    private HashMap<String,Chess> chessAll= new HashMap<>();
    //初始化卡池
    public void init(){
        for (String metaChessId : metaChessIds) {
            MetaChess meta = getChessByMetaId(metaChessId);
            int i = ratio[meta.getInitLevel()];
            for(int k=0;k<i*base;k++){
                Chess chess = new Chess();
                //TODO copy和初始化
                BeanUtils.copyProperties(meta,chess);
                chess.setId(metaChessId+ "-" + k);
                chess.setLevel(meta.getInitLevel());
                chess.setSkill(new Skill(meta.getSkillType()));

                chessAll.put(metaChessId+ "-" + k,chess);
            }

        }

    }
    @Data
    public static class MetaChess{
        private String id;
        private String name;
        private int initLevel;
        private int maxMp;
        private int maxHp;
        private int mp;
        private int hp;
        private AutoChessRoomProtocol.SkillType skillType;//自带的技能
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
    public static MetaChess getChessByMetaId(String chessMetaId){
        switch (ChessManager.getByName(chessMetaId)){
//            case GUAN_YU:{
//                return getDefault();
//            }
            default:{

                return getDefault();
            }
        }
    }
    private static MetaChess getDefault(){
        MetaChess metaChess = new MetaChess();
        metaChess.setInitLevel(1);
        metaChess.setMaxHp(500);
        metaChess.setMaxMp(75);
        metaChess.setHp(500);
        metaChess.setMp(25);
        metaChess.setId("-1");
        metaChess.setName("测试默认棋子");
        metaChess.setSkillType(AutoChessRoomProtocol.SkillType.BEI_DONG);

        return metaChess;
    }
    private  Chess getById(String id) {
        if(chessAll.containsKey(id))
            return chessAll.get(id);
        return null;
    }
}
