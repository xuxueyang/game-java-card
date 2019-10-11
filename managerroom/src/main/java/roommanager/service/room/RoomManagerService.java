package roommanager.service.room;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.rpc.RoomRPCConstant;
import core.rpc.dto.DeckRpcDTO;
import core.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import netty.rabbitmq.RabbitMQConsumer;
import netty.rabbitmq.RabbitMQProducer;
import roommanager.rpc.DeckRpcClient;
import roommanager.service.room.autochessroom.AutoChessRoom;
import roommanager.service.room.pvptworoom.PvpTwoRoom;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class RoomManagerService implements RoomEventOverInterface<RoomEventOverInterface.DefaultOverDTO>,RoomEventSendInterface<RoomRabbitDTO> {
    public static final Logger log = LoggerFactory.getLogger(RoomManagerService.class);

    @Autowired
    private DeckRpcClient deckRpcClient;
    @Autowired
    private RabbitMQConsumer consumer;
    @Autowired
    private RabbitMQProducer producer;
//    private LinkedBlockingQueue<RequestDTO> storageConsumer = new LinkedBlockingQueue<RequestDTO>();
    private LinkedBlockingQueue<RequestDTO> storageProducer = new LinkedBlockingQueue<RequestDTO>();

    private LinkedBlockingQueue<String> _consumerMessage = new LinkedBlockingQueue<>();
    @PostConstruct
    private void initManager() {
        Runnable runnableConsumer = new Runnable() {
            @Override
            public void run() {
                //循环读取读出消息
                while (true){
                    if(consumer!=null){
                        try {
                            String consume = consumer.consume();
                            _consumerMessage.put(consume);
                            log.info(consume);
//                            RequestDTO dto = JSONObject.parseObject(consume, RequestDTO.class);
    //                        storageConsumer.put(dto);
//                            receiveMessage(dto);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        };
        Runnable _runnableConsumer = new Runnable() {
            @Override
            public void run() {
                //循环读取读出消息
                while (true){
                    if(consumer!=null){
                        try {
                            String consume = _consumerMessage.take();
                            RequestDTO dto = JSONObject.parseObject(consume, RequestDTO.class);
                            //                        storageConsumer.put(dto);
                            receiveMessage(dto);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        };
        Runnable runnableProducer = new Runnable() {
            @Override
            public void run() {
                if(producer!=null){
                    //循环读取读出消息
                    try {
                        RequestDTO take = storageProducer.take();
                        try {
                            producer.produce(JSON.toJSONString(take));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread threadConsumer = new Thread(runnableConsumer);

        Thread threadProducer  = new Thread(runnableProducer);
        Thread _threadConsumer  = new Thread(_runnableConsumer);

        threadConsumer.run();
        threadProducer.run();
        _threadConsumer.run();
    }
    //todo 注入rabbit，并且接受消息
//    Executor executor = Executors.newFixedThreadPool(10);
    private ConcurrentHashMap<String,AbstractRoom> _roomMap = new ConcurrentHashMap<>();
    public String CREATE_AUTO_CHESS_ROOM(int areaL,List userIds) throws Exception {

        //init初始化房間,根據xmind
        //創建一個綫程執行附件
        String roomId = UUIDGenerator.getUUID();
        //發送請求，讓玩家得知匹配成功

        AutoChessRoom pvpTwoRoom = new AutoChessRoom(areaL,roomId, userIds,this,this);
        _roomMap.put(roomId,pvpTwoRoom);
//        executor.execute(pvpTwoRoom);
        startRoom(pvpTwoRoom);
        return roomId;
    }
    public void CREATE_TWO_ROOM(Long oneUserId, Long twoUserId,Byte areaL) throws Exception {

        //init初始化房間,根據xmind
        //創建一個綫程執行附件
        String roomId = UUIDGenerator.getUUID();
        //發送請求，讓玩家得知匹配成功
        RequestDTO dto = new RequestDTO<>();
        Map<String,Object>  map = new HashMap<>();
        map.put(RoomRPCConstant.Key.userId.name(),oneUserId);
        map.put(RoomRPCConstant.Key.areaL.name(),areaL);
        dto.setData(map);
        ReturnResultDTO oneDeck = deckRpcClient.GET_DECK(dto);
        if(!oneDeck.getReturnCode().startsWith(ReturnCode.SUCCESS)){
            throw new Exception("獲取主玩家卡組失敗");
        }

        map.put(RoomRPCConstant.Key.userId.name(),twoUserId);

        ReturnResultDTO twoDeck = deckRpcClient.GET_DECK(dto);
        if(!twoDeck.getReturnCode().startsWith(ReturnCode.SUCCESS)){
            throw new Exception("獲取次玩家卡組失敗");
        }
        PvpTwoRoom pvpTwoRoom = new PvpTwoRoom(areaL,roomId, oneUserId,(DeckRpcDTO)oneDeck.getData(),twoUserId, (DeckRpcDTO)twoDeck.getData(),this,this);
        _roomMap.put(roomId,pvpTwoRoom);
//        executor.execute(pvpTwoRoom);
        startRoom(pvpTwoRoom);
    }
    public void receiveMessage(RequestDTO dto){
        String roomId = dto.getRoomId();
        if(roomId!=null&&_roomMap.containsKey(roomId)){
            try {
                _roomMap.get(roomId).receiveMessage(dto);
            }catch (Exception e){
                log.error("roomId : " + roomId + " ;message: " +  e.getMessage());
            }
        }else{
            log.debug(JSON.toJSONString(dto));
        }
    }
    public void startRoom(AbstractRoom room){
        //初始化完畢。發送請求給玩家，同時開始

        List<RoomRabbitDTO> roomRabbitDTOS = room.sendStartMsg();
        //TODO 消息推送到隊列，然後netty取出來消費發送給玩家
        //收到了check請求，然後再開始
        room.run();
    }
    @Override
    public void over(DefaultOverDTO defaultOverDTO) {
        //TODO 結束戰鬥時的通知
        //構造消息發送給玩家
    }

    @Override
    public void sendMsg(List<RoomRabbitDTO> msgList) {
        log.debug(JSON.toJSONString(msgList));
        //TODO 生产者
        if(msgList!=null){
            for(RoomRabbitDTO dto:msgList){
                RequestDTO requestDTO = new RequestDTO();
                requestDTO.setUserId(dto.getUserId());
                requestDTO.setProtocol(dto.getProtocol());
                requestDTO.setArea(dto.getArea());
                requestDTO.setData(dto.getData());
                requestDTO.setType(dto.getType());
                try {
                    storageProducer.put(requestDTO);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //TODO
                }
            }
        }
    }

    public String CREATE_ROOM(String roomKey,int areaL, Long[] userIds) {
        try {
            switch (RoomRPCConstant.RoomKey.getByStatus(roomKey)){
                case autochess:{
                    List<Long> longs = Arrays.asList(userIds);
                    CREATE_AUTO_CHESS_ROOM(areaL,longs);
                }break;
            }
            return "roomId";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
