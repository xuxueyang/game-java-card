package roommanager.service.room.autochessroom;

import core.protocol.AutoChessRoomProtocol;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import roommanager.service.room.autochessroom.entity.MetaChess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Data
public class ChessManager {
    private static final int[] ratio = new int[]{9,7,5,3,2};
    private static final int base = 6;
    private static final int[][] chessRandom = {
            {100 , 0 , 0 , 0 , 0},
            {70 , 30 , 0 , 0 , 0},
            {60 , 30 , 10 , 0 , 0},
            {40 , 30 , 20 , 10 , 0},
            {20 , 30 , 30 , 20 , 0},
            {20 , 20 , 30 , 20 , 10},
            {10 , 10 , 30 , 30 , 20},
            {10 , 10 , 20 , 40 , 20},
            {10 , 10 , 10 , 40 , 30}
    };

    private static  String[] metaChessIds = new String[MetaChessName.values().length];
    {
        MetaChessName[] values = MetaChessName.values();
        for (int i = 0; i < values.length; i++) {
            metaChessIds[i] = values[i].name();
        }
    }
    private HashMap<String,Chess> chessAll= new HashMap<>();

    private List<Chess> publicPool = new ArrayList<>(AutoChessRoom.maxPlayerNum);

    private List<Equip> getRandomEquip(int num){//根据野怪数目，todo
        return null;
    }
    private MetaChess getRandomChessByLevelAndNum(int level){
        //不同等级，不同等级棋子有不同概率


        return null;
    }
    //根据等级，设置卡池（并附带装备)
    public void resetPoolByTimeNum(int level){
        //新建这些棋子，加入队列
        for(int i=0;i<AutoChessRoom.maxPlayerNum;i++){
            Chess chess = new Chess(Chess.PositionType.OTHER_GLOBAL);
            //todo 根据等级得到棋子列表和根据概率得到一定棋子————和每次刷新卡池是一样的算法
            MetaChess metaChess = getRandomChessByLevelAndNum(level);
            chess.setByMetaChess(metaChess);
            //TODO 设置装备

            publicPool.add(chess);
        }
    }
    //得到公共卡池
    public List<Chess> getPublicPoolInfo() {
        return publicPool;
    }


    //初始化卡池
    public void init(){
        for (String metaChessId : metaChessIds) {
            MetaChess meta = getChessByMetaId(metaChessId);
            int i = ratio[meta.getInitLevel()];
            for(int k=0;k<i*base;k++){
                Chess chess = new Chess(Chess.PositionType.GLOBAL);
                //TODO copy和初始化
                BeanUtils.copyProperties(meta,chess);
                chess.setId(metaChessId+ "-" + k);
                chess.setLevel(meta.getInitLevel());
                chess.setSkill(new Skill(meta.getSkillType()));

                chessAll.put(metaChessId+ "-" + k,chess);
            }

        }

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
