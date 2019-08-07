package core.spring.service.room;

import core.core.RequestDTO;

public interface RoomInterface {
//    todo 初始化 void init();
//    todo 接受消息（加密數據和解密數據)
//    RequestDTO encode(RequestDTO dto);
    void receiveMessage(RequestDTO dto);
}
