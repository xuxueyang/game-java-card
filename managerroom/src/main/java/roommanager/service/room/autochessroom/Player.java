package roommanager.service.room.autochessroom;

import core.protocol.AutoChessRoomProtocol;
import core.util.UUIDGenerator;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//玩家的管理类
@Data
class Player {
    private final static int maxTmpChess = 5;
    private final static int maxLevel = 9;
    private final static int maxWaitArea = 8;//最多8个等待棋子
//    private final static int

    private String id = UUIDGenerator.getUUID();
    private int hp;
    private int level = 1;
    private int toNextLevelEXP = 0;//经验
    private int currentEXP = 0;
    private int useChessNum = 1;//默认开始一个
    private int money = 0;
    private boolean isReady = false;
    private long timestamp = 0;
    private long startRoundTimestamp = 0;
    private boolean canPlayer = false;//能不能操作

//    private Chess[]  waitChess = new Chess[maxWaitArea];
    private List<Chess> useChessList = new ArrayList<>(maxLevel+3+maxWaitArea);
    private List<Equip> equipList = new ArrayList<>(10);

    private Chess[] tmpChess = new Chess[maxTmpChess];

    //方法
    public void buy(Chess chess){
        //构造出这样一个棋子
        //TODO 判断场上有没有一星的，依次，合成

        //更新所有棋子的状态,位置。掉下的装备需要放到装备池中
    }
    public Event sell(String chessId){
        for (Chess chess : useChessList) {
            if(chess.getId().equals(chessId)){
                //卖棋子:金币增长，棋子丢弃
                useChessList.remove(chess);
                money += chess.getLevel();
                Event event = new Event(AutoChessRoomProtocol.SERVER_PLAYER_SELLER_CHESS);
                event.put(Event.EventDataKey.Target.name(),chessId);
                return event;
            }
        }
        return new Event(AutoChessRoomProtocol.SERVER_PLAYER_SELLER_CHESS_ERRPR);
    }
    //自动刷新和手动刷新机制应该不一样，这里先简单期间用一用的
    public Event autoRefresh(){


        List<String> metaChessIds = new ArrayList<>(maxTmpChess);
        Event event = new Event(AutoChessRoomProtocol.SERVER_PLAYER_REFRESH_SELF);
        event.put(Event.EventDataKey.Refresh.name(),metaChessIds);
        return event;
    }
    public Event selfRefresh(){
        return autoRefresh();
    }
}
