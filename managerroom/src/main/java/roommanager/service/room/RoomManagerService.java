package roommanager.service.room;

import com.alibaba.fastjson.JSON;
import rabbitmq.mq.consumer.RabbitMQConsumer;
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
import rabbitmq.mq.producer.RabbitMQProducer;
import roommanager.rpc.DeckRpcClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomManagerService implements RoomEventOverInterface<RoomEventOverInterface.DefaultOverDTO>,RoomEventSendInterface<RoomRabbitDTO> {
    public static final Logger log = LoggerFactory.getLogger(RoomManagerService.class);

    @Autowired
    private DeckRpcClient deckRpcClient;
    @Autowired
    private RabbitMQConsumer consumer;
    @Autowired
    private RabbitMQProducer producer;


    //todo 注入rabbit，并且接受消息
//    Executor executor = Executors.newFixedThreadPool(10);
    private ConcurrentHashMap<String,RoomInterface> _roomMap = new ConcurrentHashMap<>();
    public void CREATE_TWO_ROOM(Long oneUserId, Long twoUserId,Byte area) throws Exception {
        //init初始化房間,根據xmind
        //創建一個綫程執行附件
        String roomId = UUIDGenerator.getUUID();
        //發送請求，讓玩家得知匹配成功
        RequestDTO dto = new RequestDTO<>();
        Map<String,Object>  map = new HashMap<>();
        map.put(RoomRPCConstant.Key.userId.name(),oneUserId);
        map.put(RoomRPCConstant.Key.area.name(),area);
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
        PvpTwoRoom pvpTwoRoom = new PvpTwoRoom(area,roomId, oneUserId,(DeckRpcDTO)oneDeck.getData(),twoUserId, (DeckRpcDTO)twoDeck.getData(),this,this);
        _roomMap.put(roomId,pvpTwoRoom);
//        executor.execute(pvpTwoRoom);
        startRoom(pvpTwoRoom);
    }
    public void receiveMessage(RequestDTO dto){
        String roomId = dto.getRoomId();
        if(_roomMap.contains(roomId)){
            try {
                _roomMap.get(roomId).receiveMessage(dto);
            }catch (Exception e){
                log.error("roomId : " + roomId + " ;message: " +  e.getMessage());
            }
        }
    }
    public void startRoom(RoomInterface room){
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
    }
}
