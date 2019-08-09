package roommanager.service.effect;

import core.protocol.Protocol;
import core.rpc.dto.CardRpcDTO;
import dist.RoomConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class SysGetCardEffect extends AbstractBaseEffect {

    private static Logger logger = LoggerFactory.getLogger(SysGetCardEffect.class);

    public SysGetCardEffect(Object BelongObject) {
        super(BelongObject);
    }

    @Override
    RoomConstants.EffectToObject getEffectToObject() {
        return RoomConstants.EffectToObject.GET_CARD;
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
            Field field = this.belongObject.getClass().getField(RoomConstants.Key_Effect.deckManager.name());
            try {
                if(!(field.get(this.belongObject) instanceof AbstractQueue)){
                    super.effect(object);
                }else{
                    AbstractQueue<CardRpcDTO> deckManager = (AbstractQueue<CardRpcDTO>)field.get(this.belongObject);
                    //卡组减少1，然后手牌加1，一个抽卡动画

                    if(deckManager.size()<1){
                        //如果没有卡的话,抽取一个疲劳卡，造成不断对旗子增加的伤害
                        Field field_noCardDamage = this.belongObject.getClass().getField(RoomConstants.Key_Effect.noCardDamage.name());
                        int noCardDamage = field_noCardDamage.getInt(this.belongObject);
                        field_noCardDamage.setInt(this.belongObject,noCardDamage+1);
                        //TODO 造成旗子伤害(第一版pass，仅做打印)
                        //TODO 添加和调用群伤伤害effect
                        EffectEvent effectEvent = getDefaultEvent();
                        effectEvent.effectResult =  Protocol.PvpTwoRoomProtocol.SERVER_CARD_GET_NO_CARD;
                        effectEvent.data = noCardDamage;
                        Field field_userId = this.belongObject.getClass().getField(RoomConstants.Key_Effect.userId.name());
                        effectEvent.userId = field_userId.getLong(this.belongObject);
                        object.eventList.add(effectEvent);
                        return object;

                    }else{
                        //如果手牌满了的话,那么抽取的牌会被销毁
                        Field field_handCardsManager = this.belongObject.getClass().getField(RoomConstants.Key_Effect.handCardsManager.name());
                        AbstractQueue<CardRpcDTO> handCardsManager = (AbstractQueue<CardRpcDTO>)field_handCardsManager.get(this.belongObject);
                        if(handCardsManager.size()>=6){
                            //抽取，但是销毁卡
                            CardRpcDTO poll = deckManager.poll();
                            EffectEvent effectEvent = getDefaultEvent();
                            effectEvent.effectResult = Protocol.PvpTwoRoomProtocol.SERVER_CARD_GET_DESTROY_CARD;
                            effectEvent.data = poll.getId();
                            Field field_userId = this.belongObject.getClass().getField(RoomConstants.Key_Effect.userId.name());
                            effectEvent.userId = field_userId.getLong(this.belongObject);
                            object.eventList.add(effectEvent);
                            return object;
                        }else{
                            // 正常的抽卡逻辑，卡组数目-1，卡牌数目+1，暂时不考虑抽到卡时候触发的效果！
                            CardRpcDTO poll = deckManager.poll();
                            handCardsManager.add(poll);

                            EffectEvent effectEvent = getDefaultEvent();
                            effectEvent.effectResult = Protocol.PvpTwoRoomProtocol.SERVER_CARD_GET_SUCCESS;
                            effectEvent.data = poll.getId();
                            Field field_userId = this.belongObject.getClass().getField(RoomConstants.Key_Effect.userId.name());
                            effectEvent.userId = field_userId.getLong(this.belongObject);
                            object.eventList.add(effectEvent);
                            return object;
                        }
                    }

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
        return 2L;
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
