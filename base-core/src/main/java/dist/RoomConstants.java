package dist;

public class RoomConstants {
    public enum Key_Effect{
        starForce,
        power,
        userId,
        effectId,
        deckManager,
        handCardsManager,
        noCardDamage

    }
    public enum Key_PvpTwoRoom{
        envoys,
        otherEnvoys,
        cards,
        userId,
        handCards,
        otherHandCards,
        isOne,
        mapId,
    }
//    public enum EffectResult{
//        SUCCESS,
//        FAILURE,
//        DESTROY_CARD,
//        NO_CARD
//    }
    public enum EffectTime{
        //作用时间,real_time,stage
        RT_IF_TRUE,//满足条件时
        ST_PRE_END,//结束阶段时触发
        ST_END,//回合结束触发
        ST_PRE_INIT,//预开始
        ST_GET_CARD,//抽卡
    }
    //作用对象
    public enum EffectToObject{
        OtherUser,//其他某个玩家（随机）
        User,//某个玩家
        OtherUsers,//其他所有玩家
        Space,//场地
        Card,//卡牌
        GET_CARD,//抽卡
        Envoy,//棋子
        //Card_Envoy,//都可以
        StarForce,//水晶星魄
    }
}
