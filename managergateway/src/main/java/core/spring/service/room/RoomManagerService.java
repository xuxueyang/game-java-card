package core.spring.service.room;

import core.core.RequestDTO;
import core.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomManagerService {
    //todo 注入rabbit，并且接受消息

    private ConcurrentHashMap<String,RoomInterface> _roomMap = new ConcurrentHashMap<>();
    public void CREATE_TWO_ROOM(Long oneUserId, Long twoUserId) {
        //init初始化房間,根據xmind
        //創建一個綫程執行附件
        String roomId = UUIDGenerator.getUUID();
        //發送請求，讓玩家得知匹配成功
        PvpTwoRoom pvpTwoRoom = new PvpTwoRoom(roomId, oneUserId, twoUserId);
        _roomMap.put(roomId,pvpTwoRoom);
        pvpTwoRoom.run();
    }
    public void receiveMessage(RequestDTO dto){
        String roomId = dto.getRoomId();
        if(_roomMap.contains(roomId)){
            _roomMap.get(roomId).receiveMessage(dto);
        }
    }
}
