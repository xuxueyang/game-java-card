package roommanager.service.effect;

import core.protocol.Protocol;
import dist.RoomConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SysIncrStarForceEffect extends AbstractBaseEffect {

    private static Logger logger = LoggerFactory.getLogger(SysIncrStarForceEffect.class);

    public SysIncrStarForceEffect(Object BelongObject) {
        super(BelongObject);
    }

    @Override
    RoomConstants.EffectToObject getEffectToObject() {
        return RoomConstants.EffectToObject.StarForce;
    }

    @Override
    boolean canCancel() {
        return false;
    }

    @Override
    RoomConstants.EffectTime getEffectTime() {
        return RoomConstants.EffectTime.ST_PRE_INIT;
    }
    //增长一个水晶的效果


    @Override
    public EffectData effect(EffectData object) {
        try {
            //理论上是操作

            Field field = this.belongObject.getClass().getField(RoomConstants.Key_Effect.starForce.name());
            try {
                int anInt = field.getInt(this.belongObject);
                if(anInt<10)
                {
                    logger.debug("星魄增长1");
                    field.setInt(this.belongObject,anInt+1);
                    //并且加一个事件
                    EffectEvent effectEvent = getDefaultEvent();
                    Field field_userId = this.belongObject.getClass().getField(RoomConstants.Key_Effect.userId.name());
                    effectEvent.userId = field_userId.getLong(this.belongObject);

                    effectEvent.data = anInt+1;

                    effectEvent.effectResult = Protocol.PvpTwoRoomProtocol.SERVER_STAR_STAR_FORCE_INCR;
//                    if(object.eventList==null){
//                        object.eventList = new ArrayList();
//                    }
                    object.eventList.add(effectEvent);
                }else{
                    //不然就单纯返回
                    //返回错了
                    return super.effect(object);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            return super.effect(object);
        }
    }

    @Override
    Long getEffectId() throws Exception {
        return 1L;
    }

    @Override
    EffectEvent getDefaultEvent() {
        EffectEvent effectEvent = super.getDefaultEvent();
        try {
            effectEvent.effectId = getEffectId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        effectEvent.effectTime = getEffectTime();
        return effectEvent;
    }
}
