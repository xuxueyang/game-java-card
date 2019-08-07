package core.spring.service.room;

import java.util.List;

public interface RoomEventSendInterface<RoomRabbitDTO> {
    //結束戰鬥
    void sendMsg(List<RoomRabbitDTO> t);
}
