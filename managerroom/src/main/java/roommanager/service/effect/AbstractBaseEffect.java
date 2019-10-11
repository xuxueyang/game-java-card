package roommanager.service.effect;

import core.protocol.Protocol;
import core.protocol.PvpTwoRoomProtocol;
import dist.RoomConstants;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBaseEffect<BelongObject> {

    protected BelongObject belongObject;
    private Long _effectId = 0L;

    public AbstractBaseEffect(BelongObject BelongObject){
        this.belongObject = BelongObject;
    }
    Long getEffectId() throws Exception {
        throw new Exception("EffectID为空");
    }
    abstract RoomConstants.EffectToObject getEffectToObject();
    abstract boolean canCancel();
    EffectEvent getDefaultEvent(){
        EffectEvent effectEvent = new EffectEvent();
        return effectEvent;
    }
    abstract RoomConstants.EffectTime getEffectTime();
    public EffectData effect(EffectData object){
        //加入一个失败的消息
        List eventList = object.eventList;
        eventList.add(new EffectEvent().effectResult = PvpTwoRoomProtocol.SERVER_ERROR);
        return object;
    }//效果生效

    public static class EffectData<T>{
       //传递的数据
        public T data;
        //生成一个效果事件
        //谁 在什么时候 做了什么 怎么做的 消耗了什么 有什么影响
        public List<EffectEvent> eventList;
    }
    public static class EffectEvent<T>{
        public int effectResult;
        public Long userId;
        public Long timestamp;
        public Long effectId;
        public T data;
        public RoomConstants.EffectTime effectTime;

    }

}
