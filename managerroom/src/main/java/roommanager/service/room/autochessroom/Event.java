package roommanager.service.room.autochessroom;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Event {
    //事件列表
    private int order = 0;
    //协议，事件类型
    private int protocol = -1;

    private String desc = "";//事件描述

    private Map<String,Object> data = null;//传输的data

    private Event pre = null;
    private Event next = null;


    public Event(int protocol){
        this.protocol = protocol;
    }
    public void put(String key,Object value){
        if(this.data==null){
            this.data = new HashMap<>();
        }
        data.put(key,value);
    }
    enum EventDataKey{
        //一些key
        Target,
        TargetType,
        SourceType,
        Source,
        Refresh,
//        LastEventType
    }
}
