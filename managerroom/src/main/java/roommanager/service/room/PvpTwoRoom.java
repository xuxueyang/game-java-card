package roommanager.service.room;

import core.core.RequestDTO;
import core.protocol.Protocol;
import core.rpc.dto.CardRpcDTO;
import core.rpc.dto.DeckRpcDTO;
import core.rpc.dto.EnvoyRpcDTO;
import roommanager.service.map.GameMap;
import roommanager.service.map.GameMapFactory;
import dist.ItemConstants;
import dist.RoomConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class PvpTwoRoom implements RoomInterface<RoomRabbitDTO> {
    public static final Logger log = LoggerFactory.getLogger(PvpTwoRoom.class);

    //回合狀態，5個R
    enum RoundType{
        pre_init,//        预准备阶段pre_init,0000001
        init,//        准备阶段init,0000010
        get_card, //抽卡階段
        start,//玩家操作阶段start,0000100
        pre_end,//预结束阶段pre_end,0001000
        end//结束阶段end,0001000
    }
    private String _roomId;
    private Long winnerUserId;
    private Long failureUserId;
    private Long _startTime;
    private Integer _mapId;
    private ResourceManager _oneManager;
    private ResourceManager _twoManager;
    private GameMap _gameMap;
    private Byte area;
    private Boolean isOver = false;
    private Timer timer = new Timer();
    private _MsgFactory _MsgFactory = new _MsgFactory();
    private int _round = 1;//縂回合數
    private RoundType roundType = RoundType.pre_init;

    private ResourceManager currentManager;
    private LinkedBlockingQueue<RequestDTO> blockingQueue = new LinkedBlockingQueue(20);

    private RoomEventOverInterface<RoomEventOverInterface.DefaultOverDTO> _RoomEventOverInterface = null;
    private RoomEventSendInterface<RoomRabbitDTO> _RoomEventSendInterface = null;

    public PvpTwoRoom(Byte area, String roomId, Long oneUserId, DeckRpcDTO oneUserDeck, Long twoUserId, DeckRpcDTO twoUserDeck,
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
        this._mapId = 0;
        this._gameMap =  GameMapFactory.getGameMapById(this._mapId);
        ResourceManager resourceManager = _getResourceManager(oneUserId,oneUserDeck, true);
        ResourceManager resourceManager1 = _getResourceManager(twoUserId,twoUserDeck, false);
        this._oneManager = resourceManager;
        this._twoManager = resourceManager1;
        _checkData();//核查数据合法性，比如棋子3个，棋子数值不能过高，星辰值转为具体数值等等
        _initData();
    }
    private void _checkData() throws Exception{
        if(this._oneManager.envoys.size()!=3
                ||this._twoManager.envoys.size()!=3){
            throw  new Exception("棋子數目不爲3");
        }

    }
    private void _initData(){
        this._gameMap.initPosition(this._oneManager.envoys,0);
        this._gameMap.initPosition(this._twoManager.envoys,1);
        //構造一個init卡牌棋子的請求，客戶端儲存這些新的ID
        this.currentManager = this._oneManager;
        this._round = 1;
        this.currentManager.round += 1;
        //计算星辰值，换算单位
        // 1星辰力=14血量=4成长生命值=4基础攻击力=1成长攻击力=0.2移动力=0.1攻击距离=3防御=0.5成长防御，放到战斗房间中计算？
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
                if(timestamp-_oneManager.timestamp<Protocol.Head_TIME){
                    //説明失去聯係了
                    winnerUserId = _twoManager.userId;
                    failureUserId = _oneManager.userId;
                    over();
                    timer.cancel();
                    return;
                }
                if(timestamp-_twoManager.timestamp<Protocol.Head_TIME){
                    winnerUserId = _oneManager.userId;
                    failureUserId = _twoManager.userId;
                    over();
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
    private void _startGame(){
        _startTime = System.currentTimeMillis();
        //階段處理
        while (true){
            switch (roundType){
                case pre_init:{
                    sendMessage(_MsgFactory.getStartRoundMsg());//還有水晶增長
                }break;
                case end:{
                    //理論上沒有消息了
                    this.blockingQueue.clear();
                    sendMessage(_MsgFactory.getEndRoundMsg());
                    this.currentManager =
                            this.currentManager == this._oneManager?
                                    this._twoManager: this._oneManager;
                    this.roundType = RoundType.pre_init;
                }break;
                case init:{
                    //做階段預處理
                } break;
                case get_card:{
                    //抽卡
                } break;
                case start:{
                    //阻塞等待玩家處理，同時記時間，超過40s結束回合
                    while (roundType==RoundType.start){
                        try {
                            RequestDTO take = blockingQueue.take();
                            sendMessage(_reslove(take));
                            //TODO 對這個協議發送回調，表明處理成功了（同時對另一個玩家通知動畫顯示）

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    private List<RoomRabbitDTO> _reslove(RequestDTO dto){
        //這裏做2份，給2個玩家，但是data應該相同，保存整個機制的序列。什麽的觸發，銷毀，生命周期等
        switch (dto.getProtocol()){
            case Protocol.PvpTwoRoomProtocol.CLINET_CARD_USE:{

            } break;
            case Protocol.PvpTwoRoomProtocol.CLINET_ENVOY_ATTACK:{

            } break;
            case Protocol.PvpTwoRoomProtocol.CLINET_ENVOY_MOVE:{

            } break;
            case Protocol.PvpTwoRoomProtocol.CLINET_ENVOY_USE_SKILL:{

            }
            case Protocol.PvpTwoRoomProtocol.CLINET_PLAYER_OVER:{

            } break;
            case Protocol.PvpTwoRoomProtocol.CLINET_PLAYER_SURRENDER:{

            }break;
        }
        return null;
    }
    @Override
    public void receiveMessage(RequestDTO dto) {
        switch (dto.getProtocol()){
            case Protocol.ConstatnProtocol.Head:{
                long userId = Long.parseLong(dto.getData().toString());
                getByUserId(userId).timestamp = System.currentTimeMillis();
            } break;
            default:{
                if(_checkRequestDTO(dto)){
                    try {
                        blockingQueue.put(dto);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //發送使用失敗的回調
                        sendMessage(_MsgFactory.getErrorMsg(dto));
                    }
                }
            }
        }
    }
    private boolean _checkRequestDTO(RequestDTO dto) {
        return this.roundType == RoundType.start
                &&dto.getUserId() == this.currentManager.userId
                &&dto.getProtocol()>= Protocol.PvpTwoRoomProtocol.CLINET_CARD_USE
                && dto.getProtocol()<=Protocol.PvpTwoRoomProtocol.CLINET_PLAYER_SURRENDER;
    }


    @Override
    public List<RoomRabbitDTO> sendStartMsg() {
        return _MsgFactory.getStartRoomMsg();
    }



    @Override
    public void sendMessage(List<RoomRabbitDTO> msgList) {
        this._RoomEventSendInterface.sendMsg(msgList);
    }

    @Override
    public void over() {
        if(!isOver)
            return;
        isOver = !isOver;
        //結算
        RoomEventOverInterface.DefaultOverDTO defaultOverDTO = new RoomEventOverInterface.DefaultOverDTO();
        defaultOverDTO.winnerUserId = this.winnerUserId;
        defaultOverDTO.battleTime = System.currentTimeMillis() - this._startTime;
        defaultOverDTO.failureUserId = this.failureUserId;
        defaultOverDTO.roomId = _roomId;

        _RoomEventOverInterface.over(defaultOverDTO);
    }
    private ResourceManager _getResourceManager(Long userId,DeckRpcDTO deckRpcDTO,boolean isOne){
        ResourceManager resourceManager = new ResourceManager();
        resourceManager.isOne = isOne;
        resourceManager.userId = userId;
        resourceManager.envoys = deckRpcDTO.getEnvoyDTOs();
        resourceManager.cards = deckRpcDTO.getCardDTOS();
        return resourceManager;
    }

    //用内部的卡牌和棋子，和管理.
    // TODO 將生成的卡牌(新的ID）返回給前端
    private class ResourceManager{
        public boolean isOne;//主玩家標記
        public Long userId;
        public boolean isReady = false;
        public List<EnvoyRpcDTO> envoys;
        public List<CardRpcDTO> cards;
        public List<CardRpcDTO> handCards = new ArrayList<>();
        public Long timestamp;//心跳
        public int round = 0;
    }
    private ResourceManager getByUserId(long userId){
        if(_oneManager.userId == userId  )
            return _oneManager;
        if(_twoManager.userId == userId)
            return _twoManager;
        return null;
    }
    private final class _MsgFactory{
        public List<RoomRabbitDTO> getStartRoundMsg(){
//            RoomRabbitDTO oneDto = new RoomRabbitDTO();
//            oneDto.setUserId(currentManager.userId);
//            Map<String,Object> map = new HashMap<>();
//            oneDto.setData(map);
//            oneDto.setArea(area);
//            oneDto.setType(Protocol.Type.ROOM);
//            oneDto.setProtocol(Protocol.PvpTwoRoomProtocol.SERVER_ROOM_INIT);
            //TODO 發送給2個人
            return null;
        }
        public List<RoomRabbitDTO> getEndRoundMsg() {
            return null;
        }
        public List<RoomRabbitDTO> getStartRoomMsg(){
            //构造主玩家返回值
            ResourceManager[] list = new ResourceManager[]{_oneManager,_twoManager};
            List<RoomRabbitDTO> dtos = new ArrayList<RoomRabbitDTO>(2);
            for(int i=0;i<2;i++){
                ResourceManager manager = list[i];
                RoomRabbitDTO oneDto = new RoomRabbitDTO();
                oneDto.setUserId(manager.userId);
                Map<String,Object> map = new HashMap<>();
                map.put(RoomConstants.Key_PvpTwoRoom.mapId.name(),_mapId);
                map.put(RoomConstants.Key_PvpTwoRoom.envoys.name(),manager.envoys);
                map.put(RoomConstants.Key_PvpTwoRoom.otherEnvoys.name(),list[i==0?1:0].envoys);
                map.put(RoomConstants.Key_PvpTwoRoom.otherHandCards.name(),list[i==0?1:0].handCards.size());
                map.put(RoomConstants.Key_PvpTwoRoom.cards.name(),manager.cards);
                map.put(RoomConstants.Key_PvpTwoRoom.isOne.name(),manager.isOne);//表示位置其實
                map.put(RoomConstants.Key_PvpTwoRoom.handCards.name(),manager.handCards);
                oneDto.setData(map);
                oneDto.setArea(area);
                oneDto.setType(Protocol.Type.ROOM);
                oneDto.setProtocol(Protocol.PvpTwoRoomProtocol.SERVER_ROOM_INIT);
            }
            return dtos;
        }


        public List<RoomRabbitDTO> getErrorMsg(RequestDTO dto) {
            //對錯誤的消息，需要原樣返回，説明失敗
            RoomRabbitDTO oneDto = new RoomRabbitDTO();
            oneDto.setUserId(dto.getUserId());
            oneDto.setData(false);
            oneDto.setArea(dto.getArea());
            oneDto.setType(dto.getType());
            oneDto.setProtocol(dto.getProtocol());
            ArrayList<RoomRabbitDTO> roomRabbitDTOS = new ArrayList<>(1);
            roomRabbitDTOS.add(oneDto);
            return roomRabbitDTOS;
        }
    }
}
