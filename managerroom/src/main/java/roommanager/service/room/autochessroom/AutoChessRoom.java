package roommanager.service.room.autochessroom;

import core.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import roommanager.service.map.GameMapFactory;
import roommanager.service.room.AbstractRoom;
import roommanager.service.room.RoomEventOverInterface;
import roommanager.service.room.RoomEventSendInterface;
import roommanager.service.room.RoomRabbitDTO;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AutoChessRoom extends AbstractRoom<RoomRabbitDTO> {
    public static final Logger log = LoggerFactory.getLogger(AutoChessRoom.class);

    public static final int maxPlayerNum = 8;
    public static final int minPlayerNum = 2;

    private Player[] players = new Player[8];
    private Timer timer = new Timer();


    @Override
    public List sendStartMsg() {
        return null;
    }

    @Override
    protected void overTime(Long winnerUserId, Long failureUserId) {

    }

    @Override
    public void run() {
        //等待一會，然後發送主玩傢回合開始
        //開始心跳檢討
        this._oneManager.timestamp = System.currentTimeMillis();
        this._twoManager.timestamp = this._oneManager.timestamp;
        TimerTask headListen = new TimerTask() {
            @Override
            public void run() {
                long timestamp = System.currentTimeMillis();
                if(timestamp-_oneManager.timestamp< Protocol.Head_TIME){
                    //説明失去聯係了
                    overTime(_twoManager.userId,_oneManager.userId);
                    timer.cancel();
                    return;
                }
                if(timestamp-_twoManager.timestamp<Protocol.Head_TIME){
                    overTime(_oneManager.userId, _twoManager.userId);
                    timer.cancel();
                    return;
                }
            }
        };
        timer.scheduleAtFixedRate(headListen, 0,30);
        while (true){
            if(this._oneManager.isReady&&this._twoManager.isReady)
                break;
        }
        //開始
        _startGame();
    }

    private void _startGame() {

    }

    public AutoChessRoom(Byte area, String roomId, Long oneUserId, Long twoUserId,
                      RoomEventOverInterface<RoomEventOverInterface.DefaultOverDTO> roomEventOverInterface,
                      RoomEventSendInterface sendInterface)
            throws Exception
    {
        log.debug("init:戰鬥房間"+ oneUserId + "  " + twoUserId);
        this._RoomEventOverInterface = roomEventOverInterface;
        this._RoomEventSendInterface = sendInterface;
        this._roomId = roomId;
        this.area = area;
        //todo 随机生成地图
//        ResourceManager resourceManager = _initResourceManager(oneUserId,oneUserDeck, true);
//        ResourceManager resourceManager1 = _initResourceManager(twoUserId,twoUserDeck, false);
//        this._oneManager = resourceManager;
//        this._twoManager = resourceManager1;
        _checkData();//核查数据合法性，比如棋子3个，棋子数值不能过高，星辰值转为具体数值等等
        _initData();
    }

    private void _initData() {
    }

    private void _checkData() {
        
    }

}
