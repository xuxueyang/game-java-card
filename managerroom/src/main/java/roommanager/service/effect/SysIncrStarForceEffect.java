package roommanager.service.effect;

import core.protocol.Protocol;
import core.protocol.PvpTwoRoomProtocol;
import dist.RoomConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static core.protocol.PvpTwoRoomProtocol.*;

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
            Field powerField = this.belongObject.getClass().getField(RoomConstants.Key_Effect.power.name());
            try {
                {
                    int anInt = field.getInt(this.belongObject);
                    if(anInt<max_star_force) {
                        logger.debug("星魄增长1");
                        field.setInt(this.belongObject,anInt+1);
                        //并且加一个事件
                        EffectEvent effectEvent = getDefaultEvent();
                        Field field_userId = this.belongObject.getClass().getField(RoomConstants.Key_Effect.userId.name());
                        effectEvent.userId = field_userId.getLong(this.belongObject);

                        effectEvent.data = anInt+1;

                        effectEvent.effectResult = PvpTwoRoomProtocol.SERVER_STAR_STAR_FORCE_INCR;
                        object.eventList.add(effectEvent);
                    }
                }
                {
                    int anInt = powerField.getInt(this.belongObject);
                    if(anInt<max_power) {
                        logger.debug("行动力增加");
                        field.setInt(this.belongObject,anInt+each_incr_power);
                        //并且加一个事件
                        EffectEvent effectEvent = getDefaultEvent();
                        Field field_userId = this.belongObject.getClass().getField(RoomConstants.Key_Effect.userId.name());
                        effectEvent.userId = field_userId.getLong(this.belongObject);

                        effectEvent.data = anInt+1;

                        effectEvent.effectResult = PvpTwoRoomProtocol.SERVER_STAR_POWER_INCR;
                        object.eventList.add(effectEvent);
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return super.effect(object);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return super.effect(object);
        } finally {
        }
        return object;
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
