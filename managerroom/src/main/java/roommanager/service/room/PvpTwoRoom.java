package roommanager.service.room;

import core.core.RequestDTO;
import core.protocol.Protocol;
import core.rpc.dto.CardRpcDTO;
import core.rpc.dto.DeckRpcDTO;
import core.rpc.dto.EnvoyRpcDTO;
import roommanager.service.effect.AbstractBaseEffect;
import roommanager.service.effect.SysGetCardEffect;
import roommanager.service.effect.SysIncrStarForceEffect;
import roommanager.service.map.GameMap;
import roommanager.service.map.GameMapFactory;
import dist.ItemConstants;
import dist.RoomConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class PvpTwoRoom implements RoomInterface<RoomRabbitDTO> {
    public static final Logger log = LoggerFactory.getLogger(PvpTwoRoom.class);
    private static final int CONFIG_MAX_STAR_FORCE = 10;
    private static final Long MAX_ROUNT_TIME = 15*1000L;
    private static final Long REMAINING_ROUNT_TIME = 5*1000L;


    enum RoundType{
        pre_init,//        预准备阶段pre_init,0000001
        init,//        准备阶段init,0000010
        get_card, //抽卡階段
        start,//玩家操作阶段start,0000100
        pre_end,//预结束阶段pre_end,0001000
        end//结束阶段end,0001000
    }


    private String _roomId;
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
    private  RoundType _roundType = RoundType.pre_init;

    private ResourceManager currentManager;
    private LinkedBlockingQueue<RequestDTO> blockingQueue = new LinkedBlockingQueue(20);

    /**
     * 队列处理效果
     */
    private ConcurrentHashMap<RoomConstants.EffectTime,List<AbstractBaseEffect>> effectList = new ConcurrentHashMap<>();
    {
        RoomConstants.EffectTime[] values = RoomConstants.EffectTime.values();
        for(int i=0;i<values.length;i++){
            effectList.put(values[i],new ArrayList<AbstractBaseEffect>());
        }
        //加入系统的每回合抽卡、增长水晶
        effectList.get(RoomConstants.EffectTime.ST_GET_CARD).add(new SysGetCardEffect(currentManager));
        effectList.get(RoomConstants.EffectTime.ST_PRE_INIT).add(new SysIncrStarForceEffect(currentManager));
    }


    /**
     * 消息队列
     */
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
        ResourceManager resourceManager = _initResourceManager(oneUserId,oneUserDeck, true);
        ResourceManager resourceManager1 = _initResourceManager(twoUserId,twoUserDeck, false);
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
        ResourceManager[] list = new ResourceManager[]{_oneManager,_twoManager};
        for(ResourceManager manager:list){
            List<EnvoyRpcDTO> envoys = manager.envoys;
            for(EnvoyRpcDTO envoy:envoys){
                envoy.setHp(14*envoy.getHp());
                envoy.setDefense(3*envoy.getDefense());
                envoy.setAttack(envoy.getAttack()*4);
                envoy.setRoomId(_roomId);
            }
            // 初始化话手牌和卡组
            //先排序卡组，然后出列3个到手牌中
            //随机生成30个随机数
            //根据ID也行。UUID反正也是随机的
            List<CardRpcDTO> cards = manager.cards;
            cards.sort(new Comparator<CardRpcDTO>() {
                @Override
                public int compare(CardRpcDTO o1, CardRpcDTO o2) {
                    return o1.getId().compareToIgnoreCase(o2.getId());
                }
            });

            for(int i=0;i<cards.size();i++){
                manager.deckManager.add(cards.get(i));
            }
            //暂时不允许洗牌
            for(int i=0;i<3;i++){
                CardRpcDTO poll = manager.deckManager.poll();
                manager.handCardsManager.add(poll);
            }
        }

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


    private void _startGame(){
        _startTime = System.currentTimeMillis();
        this.currentManager.startRoundTimestamp = System.currentTimeMillis();
        //TODO 开启一个定时器,要结束的前10s发送通知
        TimerTask roundListen = new TimerTask() {
            @Override
            public void run() {
                if(currentManager.startRoundTimestamp==null){
                    //说明才开始
                    currentManager.startRoundTimestamp=System.currentTimeMillis();
                }else{
                    //先定死一回合10s
                    //如果还剩下20秒要提示烧绳子,如果没时间了切换回合
                    if(System.currentTimeMillis() - currentManager.startRoundTimestamp > MAX_ROUNT_TIME){
                        log.debug("TODO回合结束，切换");
                        //添加一个结束回合的消息即可
                        RequestDTO dto = new RequestDTO();
                        dto.setProtocol(Protocol.PvpTwoRoomProtocol.CLINET_PLAYER_OVER);
                        dto.setUserId(currentManager.userId);
                        dto.setRoomOperatorLong(getByUserId(currentManager.userId).operatorLong.longValue());
                        receiveMessage(dto);

                    }else if(System.currentTimeMillis()-currentManager.startRoundTimestamp > MAX_ROUNT_TIME -  REMAINING_ROUNT_TIME){
                        log.debug("剩余时间:" + (System.currentTimeMillis()-currentManager.startRoundTimestamp)/1000);
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(roundListen, 0,5);

        //階段處理
        while (true){
            switch (_roundType){
                case pre_init:{
                    List<AbstractBaseEffect> abstractBaseEffects = this.effectList.get(RoomConstants.EffectTime.ST_PRE_INIT);
                    AbstractBaseEffect.EffectData<ResourceManager> data = new AbstractBaseEffect.EffectData();
                    data.eventList = new ArrayList<>();
                    for(AbstractBaseEffect abstractBaseEffect:abstractBaseEffects){
                        data = abstractBaseEffect.effect(data);
                    }
                    //将data转换成输出的dto
                    List<RoomRabbitDTO> startRoundMsg = _MsgFactory.transferToDTO(data);
                    sendMessage(startRoundMsg);//還有水晶增長
                }break;
                case init:{
                    //做階段預處理
                } break;
                case get_card:{
                    //抽卡
                } break;
                case start:{
                    //阻塞等待玩家處理，同時記時間，超過60s結束回合
                    while (_roundType==RoundType.start){
                        try {
                            RequestDTO take = blockingQueue.take();
                            sendMessage(_reslove(take));
                            //TODO 對這個協議發送回調，表明處理成功了（同時對另一個玩家通知動畫顯示）

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } break;
                case pre_end:{

                } break;
                case end:{
                    //理論上沒有消息了
                    this.blockingQueue.clear();
                    sendMessage(_MsgFactory.getEndRoundMsg());
                    this.currentManager.startRoundTimestamp = null;
                    this.currentManager =
                            this.currentManager == this._oneManager?
                                    this._twoManager: this._oneManager;
                    this.currentManager.startRoundTimestamp = null;
                    this._round++;
                    this._roundType = RoundType.pre_init;
                }break;
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
        return _MsgFactory.getSuccessMsg(dto);
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
                }else{
                    sendMessage(_MsgFactory.getErrorMsg(dto));
                }
            }
        }
    }
    private boolean _checkRequestDTO(RequestDTO dto) {
        boolean check = this._roundType == RoundType.start
                &&dto.getRoomOperatorLong()!=null
                &&dto.getUserId() == this.currentManager.userId
                &&dto.getProtocol()>= Protocol.PvpTwoRoomProtocol.CLINET_CARD_USE
                && dto.getProtocol()<=Protocol.PvpTwoRoomProtocol.CLINET_PLAYER_SURRENDER;
        if(!check)
            return false;
        //判斷dto操作數要和自己一直
        if(Protocol.ConstatnProtocol.Head != dto.getProtocol()
                &&getByUserId(dto.getUserId()).operatorLong.longValue()!=dto.getRoomOperatorLong()){
            return false;
        }
        return true;
    }


    @Override
    public List<RoomRabbitDTO> sendStartMsg() {
        return _MsgFactory.getStartRoomMsg();
    }



    @Override
    public void sendMessage(List<RoomRabbitDTO> msgList) {
        if(msgList!=null){
            this._RoomEventSendInterface.sendMsg(msgList);
        }
    }

    @Override
    public void overTime(Long winnerUserId,Long failureUserId) {
        if(!isOver)
            return;
        isOver = !isOver;
        //結算
        RoomEventOverInterface.DefaultOverDTO defaultOverDTO = new RoomEventOverInterface.DefaultOverDTO();
        defaultOverDTO.winnerUserId = winnerUserId;
        defaultOverDTO.battleTime = System.currentTimeMillis() - this._startTime;
        defaultOverDTO.failureUserId = failureUserId;
        defaultOverDTO.roomId = _roomId;

        _RoomEventOverInterface.over(defaultOverDTO);
    }
    private ResourceManager _initResourceManager(Long userId,DeckRpcDTO deckRpcDTO,boolean isOne){
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
        //操作數
        public AtomicLong operatorLong = new AtomicLong(0);

        public boolean isOne;//主玩家標記
        public int starForce;//星魄
        public Long userId;
        public boolean isReady = false;
        public List<EnvoyRpcDTO> envoys;
        public int noCardDamage = 0;
        public List<CardRpcDTO> cards;//所有卡
        public ArrayBlockingQueue<CardRpcDTO> deckManager = new ArrayBlockingQueue<CardRpcDTO>(20);//
        public ArrayBlockingQueue<CardRpcDTO> handCardsManager = new ArrayBlockingQueue<CardRpcDTO>(6);//

        public Long timestamp;//心跳
        public Long startRoundTimestamp;
        public int round = 0;

//        public RoomRabbitDTO addStarForce() {
//            //添加水晶增长，如果有的话需要通知（或者这个逻辑客户端做吧--）
//            if(currentManager.starForce<CONFIG_MAX_STAR_FORCE){
//                currentManager.starForce++;
//            }
//            return null;
//        }
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
            List<RoomRabbitDTO> dtos = new ArrayList<>(2);
            RoomRabbitDTO oneDto = new RoomRabbitDTO();
            oneDto.setUserId(_oneManager.userId);
            oneDto.setData(currentManager.userId);
            oneDto.setArea(area);
            oneDto.setType(Protocol.Type.ROOM);
            oneDto.setProtocol(Protocol.PvpTwoRoomProtocol.SERVER_PLAYER_START);
            dtos.add(oneDto);
            // 發送給2個人
            RoomRabbitDTO twoDto = new RoomRabbitDTO();
            twoDto.setUserId(_twoManager.userId);
            twoDto.setData(currentManager.userId);
            twoDto.setArea(area);
            twoDto.setType(Protocol.Type.ROOM);
            twoDto.setProtocol(Protocol.PvpTwoRoomProtocol.SERVER_PLAYER_START);
            dtos.add(twoDto);
            return dtos;
        }
        public List<RoomRabbitDTO> getEndRoundMsg() {
            List<RoomRabbitDTO> startRoundMsg = getStartRoundMsg();
            for(int i=0;i<startRoundMsg.size();i++){
                startRoundMsg.get(i).setProtocol(Protocol.PvpTwoRoomProtocol.SERVER_PLAYER_OVER);
            }
            return startRoundMsg;
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
                map.put(RoomConstants.Key_PvpTwoRoom.otherHandCards.name(),list[i==0?1:0].handCardsManager.size());
                map.put(RoomConstants.Key_PvpTwoRoom.cards.name(),manager.cards);
                map.put(RoomConstants.Key_PvpTwoRoom.isOne.name(),manager.isOne);//表示位置其實
                map.put(RoomConstants.Key_PvpTwoRoom.handCards.name(),manager.handCardsManager);
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
            oneDto.setRoomOperatorLong(dto.getRoomOperatorLong());
            oneDto.setType(dto.getType());
            oneDto.setProtocol(dto.getProtocol());
            ArrayList<RoomRabbitDTO> roomRabbitDTOS = new ArrayList<>(1);
            roomRabbitDTOS.add(oneDto);
            return roomRabbitDTOS;
        }
        public List<RoomRabbitDTO> getSuccessMsg(RequestDTO dto) {
            //對錯誤的消息，需要原樣返回，説明失敗
            RoomRabbitDTO oneDto = new RoomRabbitDTO();
            oneDto.setUserId(dto.getUserId());
            oneDto.setData(true);
            oneDto.setArea(dto.getArea());
            oneDto.setRoomOperatorLong(dto.getRoomOperatorLong());
            oneDto.setType(dto.getType());
            oneDto.setProtocol(dto.getProtocol());
            ArrayList<RoomRabbitDTO> roomRabbitDTOS = new ArrayList<>(1);
            roomRabbitDTOS.add(oneDto);
            getByUserId(dto.getUserId()).operatorLong.getAndIncrement();//計數增長1
            return roomRabbitDTOS;
        }

        public List<RoomRabbitDTO> transferToDTO(AbstractBaseEffect.EffectData<ResourceManager> topData) {
            List<RoomRabbitDTO> rabbitDTOS = new ArrayList<>(topData.eventList.size());
            for(AbstractBaseEffect.EffectEvent event: topData.eventList){
                RoomRabbitDTO rabbitDTO = new RoomRabbitDTO();
                rabbitDTO.setProtocol(event.effectResult);
                rabbitDTO.setUserId(event.userId);
                rabbitDTO.setData(event.effectId);

                rabbitDTOS.add(rabbitDTO);
            }
            return rabbitDTOS;
        }
    }
}
