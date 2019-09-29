package roommanager.service.room.autochessroom;

import com.alibaba.fastjson.JSON;
import core.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import roommanager.service.map.GameMapFactory;
import roommanager.service.room.AbstractRoom;
import roommanager.service.room.RoomEventOverInterface;
import roommanager.service.room.RoomEventSendInterface;
import roommanager.service.room.RoomRabbitDTO;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoChessRoom extends AbstractRoom<RoomRabbitDTO> {
    public static final Logger log = LoggerFactory.getLogger(AutoChessRoom.class);

    public static final int maxPlayerNum = 8;
    public static final int minPlayerNum = 2;
    TimerTask roundListen = new TimerTask() {
        @Override
        public void run() {
            //每过45s，设置回合结束
            for (Player player : players) {
                player.setCanPlayer(false);
            }
        }
    };
    enum RoundType{
        PRE_INIT,//预备阶段——结算利息
        COMMON,//一般玩家回合——买旗子、卖棋子、将棋子上阵、添加和合成装备__自动的：合成棋子，羁绊buff添加，出现利息动画等
        BATTLE,//玩家对抗回合
        PRE_END,//结算胜负
    }

    private Player[] players = null;
    private Timer timer = new Timer();
    private long _startTime;

    @Override
    public void run() {
        //等待一會，然後發送主玩傢回合開始
        //開始心跳檢討
        for (Player player : this.players) {
            player.setTimestamp(System.currentTimeMillis());
        }
        TimerTask headListen = new TimerTask() {
            @Override
            public void run() {
                long timestamp = System.currentTimeMillis();
                for (Player player : players) {
                    player.setTimestamp(System.currentTimeMillis());
                    if(timestamp-player.getTimestamp()< Protocol.Head_TIME){
                        //説明失去聯係了
//                        TODO 不考虑超时 overTime(player.userId,_oneManager.userId);
                        timer.cancel();
                        return;
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(headListen, 0,30);
        boolean notReady = true;
        while (notReady){
            for (Player player : players) {
                if(player.isReady())
                {
                    notReady = true;
                    break;
                }
            }
        }
        //開始
        _startGame();
    }
    private void _startGame() {
        _startTime = System.currentTimeMillis();
        for (Player player : this.players) {
            player.setCanPlayer(true);
        }
        timer.scheduleAtFixedRate(roundListen, 0,45);
        //階段處理
    }



    @Override
    public List sendStartMsg() {
        return null;
    }

    @Override
    protected void overTime(Long winnerUserId, Long failureUserId) {

    }




    public AutoChessRoom(int areaL, String roomId, List<Long> userIds,
                      RoomEventOverInterface<RoomEventOverInterface.DefaultOverDTO> roomEventOverInterface,
                      RoomEventSendInterface sendInterface)
            throws Exception
    {
        log.debug("init:戰鬥房間"+ JSON.toJSON(userIds));
        this.players  = new Player[userIds.size()];
        for (int i = 0; i < this.players.length; i++) {
            players[i].setUserId(userIds.get(i));
        }
        this._RoomEventOverInterface = roomEventOverInterface;
        this._RoomEventSendInterface = sendInterface;
        this._roomId = roomId;
        this.areaL = areaL;
        //todo 随机生成地图
        _checkData();//核查数据合法性，比如棋子3个，棋子数值不能过高，星辰值转为具体数值等等
        _initData();
    }

    private void _initData() {
    }

    private void _checkData() {
        
    }

}
