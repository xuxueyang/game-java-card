package roommanager.service.room.pvptworoom;

import core.core.RequestDTO;
import core.protocol.Protocol;
import core.protocol.PvpTwoRoomProtocol;
import core.rpc.dto.CardRpcDTO;
import core.rpc.dto.DeckRpcDTO;
import core.rpc.dto.EnvoyRpcDTO;
import core.rpc.dto.ProfessionRpcDTO;
import org.springframework.beans.BeanUtils;
import roommanager.service.effect.AbstractBaseEffect;
import roommanager.service.effect.SysGetCardEffect;
import roommanager.service.effect.SysIncrStarForceEffect;
import roommanager.service.map.GameMap;
import roommanager.service.map.GameMapFactory;
import dist.RoomConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import roommanager.service.room.AbstractRoom;
import roommanager.service.room.RoomEventOverInterface;
import roommanager.service.room.RoomEventSendInterface;
import roommanager.service.room.RoomRabbitDTO;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static core.protocol.PvpTwoRoomProtocol.*;

public class PvpTwoRoom extends AbstractRoom<RoomRabbitDTO> {
    public static final Logger log = LoggerFactory.getLogger(PvpTwoRoom.class);
    private static final int CONFIG_MAX_STAR_FORCE = 10;
    private static final Long MAX_ROUNT_TIME = 115 * 1000L;
    private static final Long REMAINING_ROUNT_TIME = 5 * 1000L;
    static {
        //todo 待定玩法：1.毒圈收缩 2.资源点出现刷新得到强力卡牌或者棋子
    }

    enum RoundType {
        init,//        准备阶段init,0000010__
        pre_init,//        预准备阶段pre_init,0000001
        get_card, //抽卡階段
        start,//玩家操作阶段start,0000100
        pre_end,//预结束阶段pre_end,0001000
        end//结束阶段end,0001000
    }


    private Long _startTime;

    protected Integer _mapId;

    private ResourceManager _oneManager;
    private ResourceManager _twoManager;
    private GameMap _gameMap;
    private Boolean isOver = false;
    private Timer timer = new Timer();
    private _MsgFactory _MsgFactory = new _MsgFactory();
    private int _round = 1;//縂回合數
    private RoundType _roundType = RoundType.pre_init;

    private ResourceManager currentManager;

    /**
     * 队列处理效果
     */
    private ConcurrentHashMap<RoomConstants.EffectTime, List<AbstractBaseEffect>> effectList = new ConcurrentHashMap<>();

    {
        RoomConstants.EffectTime[] values = RoomConstants.EffectTime.values();
        for (int i = 0; i < values.length; i++) {
            effectList.put(values[i], new ArrayList<AbstractBaseEffect>());
        }
        //加入系统的每回合抽卡、增长水晶
        effectList.get(RoomConstants.EffectTime.ST_GET_CARD).add(new SysGetCardEffect(currentManager));
        effectList.get(RoomConstants.EffectTime.ST_PRE_INIT).add(new SysIncrStarForceEffect(currentManager));
//        effectList.get(RoomConstants.EffectTime.ST_PRE_INIT).add(new SysIncrStarForceEffect(currentManager));

    }


    public PvpTwoRoom(Byte area, String roomId, Long oneUserId, DeckRpcDTO oneUserDeck, Long twoUserId, DeckRpcDTO twoUserDeck,
                      RoomEventOverInterface<RoomEventOverInterface.DefaultOverDTO> roomEventOverInterface,
                      RoomEventSendInterface sendInterface)
            throws Exception {
        log.debug("init:戰鬥房間" + oneUserId + "  " + twoUserId);
        this._RoomEventOverInterface = roomEventOverInterface;
        this._RoomEventSendInterface = sendInterface;
        this._roomId = roomId;
        this.areaL = area;
        //todo 随机生成地图，开始就白板地图吧
        this._mapId = 0;
        this._gameMap = GameMapFactory.getGameMapById(this._mapId);
        ResourceManager resourceManager = _initResourceManager(oneUserId, oneUserDeck, true);
        ResourceManager resourceManager1 = _initResourceManager(twoUserId, twoUserDeck, false);
        this._oneManager = resourceManager;
        this._twoManager = resourceManager1;
        _checkData();//核查数据合法性，比如棋子3个，棋子数值不能过高，星辰值转为具体数值等等
        _initData();
    }

    private void _checkData() throws Exception {
        ResourceManager[] managers = {this._oneManager, this._twoManager};
        for (ResourceManager manager : managers) {
            if (manager.envoys.size() != max_envoy) {
                throw new Exception("棋子數目需要为10");
            }
            if (manager.cards.size() != max_card_num) {
                throw new Exception("卡牌数目需要为30");
            }
            if (manager.profession == null)
                throw new Exception("未选择职业");
        }
    }

    private void _initData() {
        //操作；1。随机卡组
        // 2。所有棋子，卡图+费用的形式呈现，用户挑选,将多余的棋子放在备战区域（同时，不然针对太强不公平）,只是技术上要wait一下，很难受，所以，作为最初用户刚进来挑选阶段好了。
        //
//        this._gameMap.initPosition(this._oneManager.envoys, 0); 改成自己放置
//        this._gameMap.initPosition(this._twoManager.envoys, 1);
        //構造一個init卡牌棋子的請求，客戶端儲存這些新的ID
        this.currentManager = this._oneManager;
        this._round = 1;
        this.currentManager.round += 1;
        //计算星辰值，换算单位
        // 1星辰力=14血量=4成长生命值=4基础攻击力=1成长攻击力=0.2移动力=0.1攻击距离=3防御=0.5成长防御，放到战斗房间中计算？
        ResourceManager[] list = new ResourceManager[]{_oneManager, _twoManager};
        for (ResourceManager manager : list) {
            List<EnvoyRpcDTO> envoys = manager.envoys;
            for (EnvoyRpcDTO envoy : envoys) {
//                envoy.setHp(14 * envoy.getHp());
//                envoy.setDefense(3 * envoy.getDefense());
//                envoy.setAttack(envoy.getAttack() * 4);
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

            for (int i = 0; i < cards.size(); i++) {
                manager.deckManager.add(cards.get(i));
            }
            //暂时不允许洗牌
            for (int i = 0; i < init_hard_card_num; i++) {
                CardRpcDTO poll = manager.deckManager.poll();
                manager.handCardsManager.add(poll);
            }
        }

    }

    public Object[] selectRandom(List list, int num) {
        //随机挑选num个出来
        Random random = new Random();
        if (list.size() < num)
            num = list.size();
        boolean r[] = new boolean[list.size()];
        Object[] re = new Object[num];

        int n = 0;
        while (n < num) {
            int temp = random.nextInt(list.size());
            if (!r[temp]) {
                if (n == num) {
                    re[n - 1] = list.get(temp);
                }
                r[temp] = true;
                n++;
            }
        }
        return re;
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
                if (timestamp - _oneManager.timestamp < Protocol.Head_TIME) {
                    //説明失去聯係了
                    overTime(_twoManager.userId, _oneManager.userId);
                    timer.cancel();
                    return;
                }
                if (timestamp - _twoManager.timestamp < Protocol.Head_TIME) {
                    overTime(_oneManager.userId, _twoManager.userId);
                    timer.cancel();
                    return;
                }
            }
        };
        timer.scheduleAtFixedRate(headListen, 0, 30);
        //心跳检测的
        //定时器，最多30s
        TimerTask readyListen = new TimerTask() {
            @Override
            public void run() {
                ResourceManager[] all = new ResourceManager[]{_oneManager, _twoManager};
                for (ResourceManager resourceManager : all) {
                    if (!resourceManager.isReady) {
                        synchronized (resourceManager) {
                            if (!resourceManager.isReady) {
                                //随机选择4个棋子放在对战区
                                List<EnvoyRpcDTO> envoys = resourceManager.envoys;
                                Object[] objects = selectRandom(envoys, max_battle_envoy);
                                for (int i = 0; i < objects.length; i++) {
                                    resourceManager.battle[i] = (EnvoyRpcDTO) objects[i];
                                }
                                resourceManager.isReady = true;
                            }
                        }
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(readyListen, 0, 30);
        _selectEnvoy();

        while (true) {
            if (this._oneManager.isReady && this._twoManager.isReady) {
                readyListen.cancel();
                break;
            }
        }

        //開始
        _startGame();
    }


//    @Override
//    public void receiveMessage(RequestDTO dto) {
//        super.receiveMessage(dto);
//        if(this._oneManager.isReady||this._twoManager.isReady){
//
//        }
//        case PvpTwoRoomProtocol.CLINET_ENVOY_SELECT:{
//            //todo 选择了初始化棋子，如果都不为ready哦
//            ResourceManager byUserId = getByUserId(dto.getUserId());
//            if(!byUserId.isReady){
//                dto.getData();
//            }
//
//        }break;
//    }
    private void changeTime(){
//        canReceiveMessage = false;
        blockingQueue.clear();
        sendMessage(_MsgFactory.getEndRoundMsg());
        currentManager.startRoundTimestamp = null;
        currentManager =
                currentManager == _oneManager ?
                        _twoManager : _oneManager;
        currentManager.startRoundTimestamp = null;
        _round++;
        _roundType = RoundType.pre_init;
//        canReceiveMessage = true;
    }
    private void _startGame() {
        _startTime = System.currentTimeMillis();
        this.currentManager.startRoundTimestamp = System.currentTimeMillis();
        //TODO 开启一个定时器,要结束的前10s发送通知
        TimerTask roundListen = new TimerTask() {
            @Override
            public void run() {
                if (currentManager.startRoundTimestamp == null) {
                    //说明才开始
                    currentManager.startRoundTimestamp = System.currentTimeMillis();
                } else {
                    //先定死一回合10s
                    //如果还剩下20秒要提示烧绳子,如果没时间了切换回合
                    if (System.currentTimeMillis() - currentManager.startRoundTimestamp > MAX_ROUNT_TIME) {
                        log.debug("TODO回合结束，切换");
                        //添加一个结束回合的消息即可
                        changeTime();


                    } else if (System.currentTimeMillis() - currentManager.startRoundTimestamp > MAX_ROUNT_TIME - REMAINING_ROUNT_TIME) {
                        log.debug("剩余时间:" + (System.currentTimeMillis() - currentManager.startRoundTimestamp) / 1000);
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(roundListen, 0, 5);

        //階段處理
        while (true) {
            switch (_roundType) {
                case init: {
                    //做階段預處理

                }
                break;
                case pre_init: {
                    //水晶和行动力增长
                    List<AbstractBaseEffect> abstractBaseEffects = this.effectList.get(RoomConstants.EffectTime.ST_PRE_INIT);
                    AbstractBaseEffect.EffectData<ResourceManager> data = new AbstractBaseEffect.EffectData();
                    data.eventList = new ArrayList<>();
                    for (AbstractBaseEffect abstractBaseEffect : abstractBaseEffects) {
                        data = abstractBaseEffect.effect(data);
                    }
                    //将data转换成输出的dto
                    List<RoomRabbitDTO> startRoundMsg = _MsgFactory.transferToAllDTO(data);
                    sendMessage(startRoundMsg);//還有水晶增長
                }
                break;

                case get_card: {
                    //抽卡
                    // 对自己和别人都是抽卡动画，只是对自己要发送ID
                    CardRpcDTO poll = currentManager.deckManager.poll();
                    if(currentManager.handCardsManager.size()>max_hard_card_num){
                        sendMessage(_MsgFactory.getSelectCard(null));
                    }else{
                        currentManager.handCardsManager.add(poll);
                        sendMessage(_MsgFactory.getSelectCard(poll.getId()));
                    }
                }
                break;
                case start: {
                    //阻塞等待玩家處理，同時記時間，超過60s結束回合
                    while (_roundType == RoundType.start) {
                        try {
                            RequestDTO take = blockingQueue.take();
                            switch (take.getProtocol()) {
                                case Protocol.ConstatnProtocol.Head: {
                                    long userId = Long.parseLong(take.getData().toString());
                                    getByUserId(userId).timestamp = System.currentTimeMillis();
                                }
                                break;
                                default: {
                                    if (_checkRequestDTO(take)) {
                                        try {
                                            //TODO 對這個協議發送回調，表明處理成功了（同時對另一個玩家通知動畫顯示）
                                            sendMessage(_reslove(take));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            //發送使用失敗的回調
                                            sendMessage(_MsgFactory.getErrorMsg(take));
                                        }
                                    } else {
                                        sendMessage(_MsgFactory.getErrorMsg(take));
                                    }
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
                case pre_end: {

                }
                break;
                case end: {
                    //理論上沒有消息了
                   changeTime();
                }
                break;
            }
        }
    }

    /**
     * 回合中对数据进行处理
     * @param dto
     * @return
     */
    private List<RoomRabbitDTO> _reslove(RequestDTO dto) {
        //這裏做2份，給2個玩家，但是data應該相同，保存整個機制的序列。什麽的觸發，銷毀，生命周期等
        //玩家正常操作心理
        //todo 1.消耗2个星辰，去塞回去，并且洗一张卡牌
        //todo 1。使用职业技能，造成效果
        //todo 2.指挥棋子移动，指挥棋子攻击，（消耗行动力），指挥使用棋子技能
        //todo 3。使用卡牌：（1）即时生效的，消耗了星辰 （2）放置到陷进区域 （3）其他
        //4.回合结束
        //todo 5.有棋子死亡，双方各选择一个棋子（赢得人抽、输掉的选择？），死亡的人，将棋子置战场(初始点，3个，最后一排即可）
        //todo 6.替换棋子：将新棋子替换旧的，继承buff，位置也继承（简单代码处理）
        switch (dto.getProtocol()) {
            case PvpTwoRoomProtocol.CLINET_CARD_USE: {

            }
            break;
            case PvpTwoRoomProtocol.CLINET_ENVOY_ATTACK: {

            }
            break;
            case PvpTwoRoomProtocol.CLINET_ENVOY_MOVE: {

            }
            break;
            case PvpTwoRoomProtocol.CLINET_ENVOY_USE_SKILL: {

            }
            case PvpTwoRoomProtocol.CLINET_PLAYER_SURRENDER: {

            }
            break;
            case PvpTwoRoomProtocol.CLINET_PLAYER_OVER: {
                changeTime();
            }
            break;
        }
        return _MsgFactory.getTimeStartSuccessMsg(dto);
    }


    private boolean _checkRequestDTO(RequestDTO dto) {
        boolean check = this._roundType == RoundType.start
                && dto.getRoomOperatorLong() != 0
                && dto.getUserId() == this.currentManager.userId
                && dto.getProtocol() >= PvpTwoRoomProtocol.CLINET_CARD_USE
                && dto.getProtocol() <= PvpTwoRoomProtocol.CLINET_PLAYER_SURRENDER;
        if (!check)
            return false;
        //判斷dto操作數要和自己一直
        //（不丢弃处理方式、不需要包顺序） 以后这样判断：会将数据存在队列中，每次取的时候判断是不是一样，如果不一样，要计算当前队列，将
        //比如吧dto和n-operator的位置和数组交换,Array，直到Array[0],有数据才处理，同时客户端有一个check，会check没回调成功的数据操作数
        //需要客户端配合，同时允许一部分操作放在客户端
        //因为目前演算都在服务器端，所以，不需要吧

        //目前丢弃方式，需要保证包顺序

        if (Protocol.ConstatnProtocol.Head != dto.getProtocol()
                && getByUserId(dto.getUserId()).operatorLong.longValue() != dto.getRoomOperatorLong()) {
            return false;
        }
        return true;
    }


    @Override
    public List<RoomRabbitDTO> sendStartMsg() {
        // 还需要把10个棋子id也一并返回哦，以及就已经挑选好的5个卡牌,以及自己的卡组
        return _MsgFactory.getStartRoomMsg();
    }


    @Override
    public void overTime(Long winnerUserId, Long failureUserId) {
        if (!isOver)
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

    private ResourceManager _initResourceManager(Long userId, DeckRpcDTO deckRpcDTO, boolean isOne) {
        ResourceManager resourceManager = new ResourceManager();
        resourceManager.isOne = isOne;
        resourceManager.userId = userId;
        resourceManager.envoys = deckRpcDTO.getEnvoyDTOs();
        resourceManager.cards = deckRpcDTO.getCardDTOS();
        resourceManager.profession = deckRpcDTO.getProfessionRpcDTO();
        return resourceManager;
    }

    //用内部的卡牌和棋子，和管理.
    //  將生成的卡牌(新的ID）返回給前端
    private class ResourceManager {
        //操作數
        public AtomicLong operatorLong = new AtomicLong(0);
        public ProfessionRpcDTO profession;
        public boolean isOne;//主玩家標記
        public int starForce = 1;//星魄
        public int power;//行动力
        public Long userId;
        public boolean isReady = false;
        public List<EnvoyRpcDTO> envoys;
        public EnvoyRpcDTO[] battle = new EnvoyRpcDTO[max_envoy];//对战区
        public EnvoyRpcDTO[] battling = new EnvoyRpcDTO[max_envoy];//实际对战中
        public int noCardDamage = 0;
        public List<CardRpcDTO> cards;//所有卡
        public ArrayBlockingQueue<CardRpcDTO> deckManager = new ArrayBlockingQueue<CardRpcDTO>(30);//
        public ArrayBlockingQueue<CardRpcDTO> handCardsManager = new ArrayBlockingQueue<CardRpcDTO>(max_hard_card_num);//

        public Long timestamp;//心跳
        public Long startRoundTimestamp;
        public int round = 0;

        public EnvoyRpcDTO getEnvoyById(String s) {
            for (EnvoyRpcDTO envoy : envoys) {
                if (envoy.getId().equals(s)) {
                    return envoy;
                }
            }
            return null;
        }

//        public RoomRabbitDTO addStarForce() {
//            //添加水晶增长，如果有的话需要通知（或者这个逻辑客户端做吧--）
//            if(currentManager.starForce<CONFIG_MAX_STAR_FORCE){
//                currentManager.starForce++;
//            }
//            return null;
//        }
    }

    private ResourceManager getByUserId(long userId) {
        if (_oneManager.userId == userId)
            return _oneManager;
        if (_twoManager.userId == userId)
            return _twoManager;
        return null;
    }

    private final class _MsgFactory {
        public List<RoomRabbitDTO> getSelectCard(String cardId){
            //todo 之后还消耗2个星辰值，洗回一枚卡牌，并且抽一张
            List<RoomRabbitDTO> dtos = new ArrayList<>(2);
            RoomRabbitDTO oneDto = new RoomRabbitDTO();
            oneDto.setUserId(_oneManager.userId);
            oneDto.setData(currentManager.userId==_oneManager.userId?cardId:"");
            oneDto.setArea(areaL);
            oneDto.setType(Protocol.Type.ROOM);
            oneDto.setProtocol(cardId!=null?SERVER_CARD_GET_SUCCESS:SERVER_CARD_GET_DESTROY_CARD);
            dtos.add(oneDto);
            // 發送給2個人
            RoomRabbitDTO twoDto = new RoomRabbitDTO();
            twoDto.setUserId(_twoManager.userId);
            twoDto.setData(currentManager.userId==_twoManager.userId?cardId:"");
            twoDto.setArea(areaL);
            twoDto.setType(Protocol.Type.ROOM);
            twoDto.setProtocol(cardId!=null?SERVER_CARD_GET_SUCCESS:SERVER_CARD_GET_DESTROY_CARD);
            dtos.add(twoDto);
            return dtos;
        }
        public List<RoomRabbitDTO> getStartRoundMsg() {
            List<RoomRabbitDTO> dtos = new ArrayList<>(2);
            RoomRabbitDTO oneDto = new RoomRabbitDTO();
            oneDto.setUserId(_oneManager.userId);
            oneDto.setData(currentManager.userId);
            oneDto.setArea(areaL);
            oneDto.setType(Protocol.Type.ROOM);
            oneDto.setProtocol(PvpTwoRoomProtocol.SERVER_PLAYER_START);
            dtos.add(oneDto);
            // 發送給2個人
            RoomRabbitDTO twoDto = new RoomRabbitDTO();
            twoDto.setUserId(_twoManager.userId);
            twoDto.setData(currentManager.userId);
            twoDto.setArea(areaL);
            twoDto.setType(Protocol.Type.ROOM);
            twoDto.setProtocol(PvpTwoRoomProtocol.SERVER_PLAYER_START);
            dtos.add(twoDto);
            return dtos;
        }

        public List<RoomRabbitDTO> getEndRoundMsg() {
            List<RoomRabbitDTO> startRoundMsg = getStartRoundMsg();
            for (int i = 0; i < startRoundMsg.size(); i++) {
                startRoundMsg.get(i).setProtocol(PvpTwoRoomProtocol.SERVER_PLAYER_OVER);
            }
            return startRoundMsg;
        }

        public List<RoomRabbitDTO> getStartRoomMsg() {
            //构造主玩家返回值
            ResourceManager[] list = new ResourceManager[]{_oneManager, _twoManager};
            List<RoomRabbitDTO> dtos = new ArrayList<RoomRabbitDTO>(2);
            for (int i = 0; i < 2; i++) {
                ResourceManager manager = list[i];
                RoomRabbitDTO oneDto = new RoomRabbitDTO();
                oneDto.setUserId(manager.userId);
                Map<String, Object> map = new HashMap<>();
                map.put(RoomConstants.Key_PvpTwoRoom.mapId.name(), _mapId);
                map.put(RoomConstants.Key_PvpTwoRoom.envoys.name(), manager.envoys);
                map.put(RoomConstants.Key_PvpTwoRoom.otherEnvoys.name(), list[i == 0 ? 1 : 0].envoys);
                map.put(RoomConstants.Key_PvpTwoRoom.otherHandCards.name(), list[i == 0 ? 1 : 0].handCardsManager.size());
                map.put(RoomConstants.Key_PvpTwoRoom.cards.name(), manager.cards);
                map.put(RoomConstants.Key_PvpTwoRoom.isOne.name(), manager.isOne);//表示位置其實
                map.put(RoomConstants.Key_PvpTwoRoom.handCards.name(), manager.handCardsManager);
                oneDto.setData(map);
                oneDto.setArea(areaL);
                oneDto.setType(Protocol.Type.ROOM);
                oneDto.setProtocol(PvpTwoRoomProtocol.SERVER_ROOM_INIT);
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

        public List<RoomRabbitDTO> getTimeStartSuccessMsg(RequestDTO dto) {
            //對錯誤的消息，需要原樣返回，説明失敗
            RoomRabbitDTO oneDto = new RoomRabbitDTO();
            oneDto.setUserId(dto.getUserId());
            oneDto.setData(true);
            oneDto.setArea(dto.getArea());
            oneDto.setRoomOperatorLong(dto.getRoomOperatorLong());
            oneDto.setType(dto.getType());
            oneDto.setProtocol(dto.getProtocol());
            ArrayList<RoomRabbitDTO> roomRabbitDTOS = new ArrayList<>(2);
            //同时通知对方，因为操作的是id，所以双方消息可以对等，直接传输
            RoomRabbitDTO twoDto = new RoomRabbitDTO();
            BeanUtils.copyProperties(oneDto,twoDto);

            roomRabbitDTOS.add(oneDto);
            roomRabbitDTOS.add(twoDto);
            getByUserId(dto.getUserId()).operatorLong.getAndIncrement();//計數增長1
            return roomRabbitDTOS;
        }

        public List<RoomRabbitDTO> transferToAllDTO(AbstractBaseEffect.EffectData<ResourceManager> topData) {
            Long[] ids = {_oneManager.userId, _twoManager.userId};
            List<RoomRabbitDTO> rabbitDTOS = new ArrayList<>(topData.eventList.size());
            for (AbstractBaseEffect.EffectEvent event : topData.eventList) {
                for (Long id : ids) {
                    RoomRabbitDTO rabbitDTO = new RoomRabbitDTO();
                    rabbitDTO.setProtocol(event.effectResult);
//                rabbitDTO.setUserId(event.userId);
                    rabbitDTO.setUserId(id);
                    rabbitDTO.setData(event.effectId);
                    rabbitDTOS.add(rabbitDTO);
                }
            }
            return rabbitDTOS;
        }
    }

    private void _selectEnvoy() {
        try {
            // 初始化备战区域棋子！
            RequestDTO take = blockingQueue.take();
            switch (take.getProtocol()) {
                case Protocol.ConstatnProtocol.Head: {
                    long userId = Long.parseLong(take.getData().toString());
                    getByUserId(userId).timestamp = System.currentTimeMillis();
                }
                break;
                case PvpTwoRoomProtocol.CLINET_ENVOY_SELECT: {
                    ResourceManager resourceManager = getByUserId(take.getUserId());
                    if (resourceManager != null && !resourceManager.isReady) {
                        synchronized (resourceManager) {
                            if (!resourceManager.isReady) {
                                //将四个棋子显示出来
                                try {
                                    List<String> ids = (List<String>) take.getData();
                                    if (ids.size() == max_battle_envoy) {
                                        //设置一下
                                        for (int i = 0; i < ids.size(); i++) {
                                            EnvoyRpcDTO envoyRpcDTO = resourceManager.getEnvoyById(ids.get(i));
                                            if (envoyRpcDTO == null) {
                                                throw new Exception("棋子不存在");
                                            }
                                            resourceManager.battle[i] = envoyRpcDTO;
                                        }
                                    }
                                    resourceManager.isReady = true;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

    }
}
