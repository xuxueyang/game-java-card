package core.spring.service.room;

import core.core.RequestDTO;

import java.util.List;

public interface RoomInterface<T> extends Runnable{
//    todo 初始化 void init();
//    todo 接受消息（加密數據和解密數據)
//    RequestDTO encode(RequestDTO dto);
    List<T> sendStartMsg();
    void receiveMessage(RequestDTO dto);
    void sendMessage(List<T> msg);
    void over();
}
