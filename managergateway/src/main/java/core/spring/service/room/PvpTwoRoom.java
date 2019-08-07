package core.spring.service.room;

import core.core.RequestDTO;
import core.protocol.Protocol;
import core.rpc.dto.CardRpcDTO;
import core.rpc.dto.DeckRpcDTO;
import core.rpc.dto.EnvoyRpcDTO;
import core.spring.service.map.GameMap;
import core.spring.service.map.GameMapFactory;
import core.spring.world.WorldManager;
import dist.ItemConstants;
import dist.RoomConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PvpTwoRoom implements RoomInterface<RoomRabbitDTO> {
    public static final Logger log = LoggerFactory.getLogger(PvpTwoRoom.class);

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

    private ResourceManager currentManager;

    private RoomEventOverInterface<RoomEventOverInterface.DefaultOverDTO> _RoomEventOverInterface = null;
    private RoomEventSendInterface<RoomRabbitDTO> _RoomEventSendInterface = null;

    public PvpTwoRoom(Byte area,String roomId, DeckRpcDTO oneUserDeck, DeckRpcDTO twoUserDeck,
                      RoomEventOverInterface<RoomEventOverInterface.DefaultOverDTO> roomEventOverInterface,
                      RoomEventSendInterface sendInterface)
        throws Exception
    {
        log.debug("init:戰鬥房間"+ oneUserDeck.getUserId() + "  " + twoUserDeck.getUserId());
        this._RoomEventOverInterface = roomEventOverInterface;
        this._RoomEventSendInterface = sendInterface;
        this._roomId = roomId;
        this.area = area;
        //todo 随机生成地图
        this._mapId = 0;
        this._gameMap =  GameMapFactory.getGameMapById(this._mapId);
        ResourceManager resourceManager = _getResourceManager(oneUserDeck, true);
        ResourceManager resourceManager1 = _getResourceManager(twoUserDeck, false);
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

    }
    private void _startGame(){
        _startTime = System.currentTimeMillis();
        //階段處理
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

    @Override
    public List<RoomRabbitDTO> sendStartMsg() {
        //构造主玩家返回值
        ResourceManager[] list = new ResourceManager[]{this._oneManager,this._twoManager};
        List<RoomRabbitDTO> dtos = new ArrayList<RoomRabbitDTO>();
        for(int i=0;i<list.length;i++){
            ResourceManager manager = list[i];
            RoomRabbitDTO oneDto = new RoomRabbitDTO();
            oneDto.setUserId(manager.userId);
            Map<String,Object> map = new HashMap<>();
            map.put(RoomConstants.Key_PvpTwoRoom.mapId.name(),this._mapId);
            map.put(RoomConstants.Key_PvpTwoRoom.envoys.name(),manager.envoys);
            map.put(RoomConstants.Key_PvpTwoRoom.otherEnvoys.name(),list[i==0?1:0].envoys);
            map.put(RoomConstants.Key_PvpTwoRoom.otherHandCards.name(),list[i==0?1:0].handCards.size());
            map.put(RoomConstants.Key_PvpTwoRoom.cards.name(),manager.cards);
            map.put(RoomConstants.Key_PvpTwoRoom.isOne.name(),manager.isOne);
            map.put(RoomConstants.Key_PvpTwoRoom.handCards.name(),manager.handCards);
            oneDto.setData(map);
            oneDto.setArea(area);
            oneDto.setType(Protocol.Type.ROOM);
            oneDto.setProtocol(Protocol.PvpTwoRoomProtocol.SERVER_ROOM_INIT);
        }
        return dtos;
    }

    @Override
    public void receiveMessage(RequestDTO dto) {
        switch (dto.getProtocol()){
            case Protocol.ConstatnProtocol.Head:{
                long userId = Long.parseLong(dto.getData().toString());
                getByUserId(userId).timestamp = System.currentTimeMillis();
            } break;
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

            }
        }
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
    private ResourceManager _getResourceManager(DeckRpcDTO deckRpcDTO,boolean isOne){
        ResourceManager resourceManager = new ResourceManager();
        resourceManager.isOne = isOne;
        resourceManager.userId = deckRpcDTO.getUserId();
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
    }
    private ResourceManager getByUserId(long userId){
        if(_oneManager.userId == userId  )
            return _oneManager;
        if(_twoManager.userId == userId)
            return _twoManager;
        return null;
    }

}
