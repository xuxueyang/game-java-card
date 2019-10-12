package roommanager.service.room.autochessroom;

import core.protocol.AutoChessRoomProtocol;
import core.util.UUIDGenerator;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import roommanager.service.room.autochessroom.entity.MetaChess;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class ChessManager {
    private static final int[] ratio = new int[]{9,7,5,3,2};
    private static final int base = 6;
    private static final int NUM = 5;
    private static final int[][] chessRandom = {
            {100 , 0 , 0 , 0 , 0},
            {100 , 30 , 0 , 0 , 0},
            {70 , 30 , 0 , 0 , 0},
            {55 , 30 , 15 , 0 , 0},
            {40 , 30 , 25 , 5 , 0},//5
            {29 , 29 , 31 , 10 , 1},
            {24 , 28 , 31 , 15 , 2},
            {20 , 24 , 31 , 20 , 5},
            {10 , 19 , 31 , 30 , 10}
    };

    private static  String[] metaChessIds = new String[MetaChessName.values().length];
    {
        MetaChessName[] values = MetaChessName.values();
        for (int i = 0; i < values.length; i++) {
            metaChessIds[i] = values[i].name();
        }
    }
    private ConcurrentHashMap<String,Chess> chessAll= new ConcurrentHashMap<>();

    private List<Chess> otherPublicPool = new ArrayList<>(AutoChessRoom.maxPlayerNum);

    private List<Equip> getRandomEquip(int num){//根据野怪数目，todo
        return null;
    }

    //这里num主要是8（人数）和每轮刷新，从公共卡池中，概率抽取
    private List<Chess> getRandomChessByLevelAndNum(int level,boolean isOtherPublicPool){
        int num = isOtherPublicPool?AutoChessRoom.maxPlayerNum:NUM;
        List<Chess> chessList = new ArrayList<>(num);
        Random random = new Random();//默认构造方法
        int[] randoms = new int[NUM];
        System.arraycopy(chessRandom[level - 1],0,randoms,0,NUM);
        int[] levelArr = new int[num];
        for(int k=0;k<num;k++){
            //不同等级，不同等级棋子有不同概率
            //https://mini.eastday.com/game/gonglue/3131.html
            //先抽取卡星，再从星级中抽取卡，如果数目不足，那么，放弃，自动到下一级卡中抽取
//        某个棋子的概率是和场上局势动态相关的，其概率由两个因素决定：a.同等稀有度棋子还留在棋池中的棋子总数，和 b.需要抽取的特定棋子在场上的个数。
            //从高星判断棋子数目足不足够，不足够就把多余的数目存入到下一级
            //每次默认刷新5个,已0,0,0，1,0的形势算成,得到一个权重
            //从0到10中随机生成一个数，然后乘以比例权重，然后比较得到排序，取第一个
            int[] tmp = new int[NUM];
            for (int i = 0; i < randoms.length; i++) {
                tmp[i] =  randoms[i] * random.nextInt(10);
            }
            int maxIndex = -1;
            int max = -1;
            for(int i=0;i<NUM;i++){
                if(tmp[i]>=max){
                    max = tmp[i];
                    maxIndex = i;
                }
            }
            levelArr[k] = maxIndex+1;//卡的星级
        }
        //通过卡的星级随机挑选,从高星到低星，因为怕高星没有棋子了
        int tmp = -1;
        for(int i=0;i<num;i++){
            for(int j=i+1;j<num;j++){
                if(levelArr[i]<levelArr[j]){
                    tmp = levelArr[i];
                    levelArr[i] = levelArr[j];
                    levelArr[j] = tmp;
                }
            }
        }
        for (int i = 0; i < num; ) {
            int chessLevel = levelArr[i];
            if (isOtherPublicPool) {
                //直接选取，不需要判断数目足不足
                List<MetaChess> metaChessByLevel = getMetaChessByLevel(chessLevel);
                Chess chess = new Chess(Chess.PositionType.OTHER_GLOBAL);
                chess.setByMetaChess(metaChessByLevel.get(random.nextInt(metaChessByLevel.size())));
                chess.setId(UUIDGenerator.getUUID());//TODO 在选秀选取后，触发加入玩家队列的方法，此棋子卖了会进入公共池

                chessList.add(chess);
            }else {
                List<Chess> poolChessNumByInitLevel = getPoolChessNumByInitLevel(chessLevel);
                //从中随机挑选一个
                if(poolChessNumByInitLevel.size()<1){
                    levelArr[i] -=1;
                    continue;
                }else{
                    chessList.add(poolChessNumByInitLevel.get(random.nextInt(poolChessNumByInitLevel.size())));
                    //理论上不会出现没卡的情况
                }
            }
            i++;
        }

        return chessList;
    }
    //根据等级，设置卡池（并附带装备)
    public void resetPoolByTimeNum(int level){
        //新建这些棋子，加入队列
        List<Chess> metaChess = getRandomChessByLevelAndNum(level,true);
        for(int i=0;i<metaChess.size();i++){
//            Chess chess = new Chess(Chess.PositionType.OTHER_GLOBAL);
            //根据等级得到棋子列表和根据概率得到一定棋子————和每次刷新卡池是一样的算法
//            chess.setByMetaChess(metaChess.get(i));
            Chess chess = metaChess.get(i);
            //TODO 设置装备

            otherPublicPool.add(chess);
        }
    }
    //得到选秀卡池
    public List<Chess> getPublicPoolInfo() {
        return otherPublicPool;
    }

    public List<Chess> getPoolChessNumByInitLevel(int level){
        List<Chess> chess = new ArrayList<>();
        Iterator<Map.Entry<String, Chess>> iterator = chessAll.entrySet().iterator();
        while (iterator.hasNext()){
            Chess value = iterator.next().getValue();
            if(Chess.PositionType.GLOBAL==value.getPositionType()&&value.getLevel()==level){
                chess.add(value);
            }
        }
        return chess;
    }
    private static final List<List<MetaChess>> metaChessByLevel = new ArrayList<>(NUM);
    {
        for(int i=0;i<NUM;i++){
            ArrayList<MetaChess> e = new ArrayList<>();
            e.add(getDefault());
            metaChessByLevel.add(e);
        }

    }
    public static List<MetaChess> getMetaChessByLevel(int level){
        return metaChessByLevel.get(level-1);
    }
    //初始化卡池
    public void init(){
        for (String metaChessId : metaChessIds) {
            MetaChess meta = getChessByMetaId(metaChessId);
            int i = ratio[meta.getInitLevel()];
            for(int k=0;k<i*base;k++){
                Chess chess = new Chess(Chess.PositionType.GLOBAL);
                chess.setByMetaChess(meta);
                chess.setId(metaChessId+ "-" + k);
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
        metaChess.setSkillId(1);
//        metaChess.setSkillType(AutoChessRoomProtocol.SkillType.BEI_DONG);

        return metaChess;
    }
    private  Chess getById(String id) {
        if(chessAll.containsKey(id))
            return chessAll.get(id);
        return null;
    }
}
