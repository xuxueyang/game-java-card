package roommanager.service.room.autochessroom;

import com.alibaba.fastjson.JSON;
import core.protocol.AutoChessRoomProtocol;
import core.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import roommanager.service.map.GameMapFactory;
import roommanager.service.room.AbstractRoom;
import roommanager.service.room.RoomEventOverInterface;
import roommanager.service.room.RoomEventSendInterface;
import roommanager.service.room.RoomRabbitDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
            //TODO 或者当所有玩家都操作完毕时（尤其是选池)
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
    private int currentTimeNum = 0 ;//当前回合数(num/5+1)-(num%5+1)回合
    //公共选池
    private ChessManager chessManager = null;

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
//                        timer.cancel();
                        return;
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(headListen, 0,30);
        boolean notReady = false;//TODO 默认不需要准备直接开始
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
        timer.scheduleAtFixedRate(roundListen, 0,30);
        //todo 客户端每次动画的时候需要同步一次时间

        //階段處理 和消息处理
        while (true){
            //todo 如果都好了或者到时间了，那么就进入下个阶段。
            // todo 同时结算胜负钱、利息、连胜连败、血量排名
            switch (currentTimeType){
                case SELECT:
                {
                    //todo 需要等待和判断每个玩家选中棋子，
                    //todo 需要不断解锁让玩家行动
                }break;
                case VS_PVE:
                {
                    //和野怪对战，每次回合都需要等待所有玩家战斗结束或者到时间，才能触发下一个回合
                    //野怪需要掉落装备
                }break;
                case VS_PVP:
                {
                    //轮盘转法（含有影子生成）来匹配2个玩家战斗，对于每个玩家，对面的都是【生成】的npc
                }break;
            }
        }
    }



    @Override
    public List sendStartMsg() {
        //发送开始消息，并且附带第一次卡池的选项
        List<Chess> chessList = chessManager.getPublicPoolInfo();
        //todo 封装成RoomRabbitDTO返回
        List<RoomRabbitDTO> list = new ArrayList<>();
        for (Player player : players) {
            RoomRabbitDTO dto = new RoomRabbitDTO();
            dto.setUserId(player.getUserId());
            dto.setData(chessList);
            dto.setType(Protocol.Type.ROOM);
            dto.setArea(Protocol.Area.Netty);
            dto.setProtocol(AutoChessRoomProtocol.SERVER_INIT_SUCCESS);
            list.add(dto);
        }
        return list;
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
        if(userIds.size()<minPlayerNum||userIds.size()>maxPlayerNum){
            throw new Exception("超出最大人数");
        }
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
        //初始化公共选池
        chessManager = new ChessManager();
        chessManager.init();
        //开始肯定就一级
        chessManager.resetPoolByTimeNum(getLevelByTimeNum(this.currentTimeNum));
    }

    private TimeType currentTimeType = TimeType.SELECT;
    //回合类型
    enum TimeType{
        VS_PVE,
        SELECT,//选秀
        VS_PVP,//玩家战斗
    }
    private static int[] __PVE = new int[]
    {
        1,2,3,10,15,21,28,36,45,55
    };
    private static int[] __SELECT = new int[]
    {
        6,13,19,26,34,43,50
    };

    public static TimeType getTimeTypeByCurrentTimeNum(int currentTimeNum){
        for (int i : __PVE) {
            if(i==currentTimeNum){
                return TimeType.VS_PVE;
            }
        }
        for (int i:__SELECT){
            if(i==currentTimeNum){
                return TimeType.SELECT;
            }
        }
        return TimeType.VS_PVP;
    }
    public static int getLevelTimeByTimeNum(int currentTimeNum){
        int level = getLevelByTimeNum(currentTimeNum);
        return (currentTimeNum-(level-1)*level/2);
    }

    public static int getLevelByTimeNum(int currentTimeNum){
        int i = 0;
        while (i*i-i<2*currentTimeNum){
            i++;
        }
        return i-1;
    }
    private void _checkData() {
        
    }
    private static void _test(){
        for(int i=1;i<100;i++){
            int level= getLevelByTimeNum(i);
            int time = getLevelTimeByTimeNum(i);
            System.out.println(level + "-" + time +":" + i);

        }
    }
    public static void main(String[] args){
        _test();
    }

}
