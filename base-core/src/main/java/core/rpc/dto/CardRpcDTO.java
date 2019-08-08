package core.rpc.dto;

import dist.ItemConstants;

import java.io.Serializable;

public class CardRpcDTO implements Serializable{
    private String id;
    private Long metaCardId;//关联的卡牌，以获取资源，房间没必要存储没用的
    private Integer feiyong;
    private ItemConstants.CardType cardType;
    //TODO 差个特效
    private Long effectId;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMetaCardId() {
        return metaCardId;
    }

    public void setMetaCardId(Long metaCardId) {
        this.metaCardId = metaCardId;
    }

    public Integer getFeiyong() {
        return feiyong;
    }

    public void setFeiyong(Integer feiyong) {
        this.feiyong = feiyong;
    }

    public ItemConstants.CardType getCardType() {
        return cardType;
    }

    public void setCardType(ItemConstants.CardType cardType) {
        this.cardType = cardType;
    }

    public Long getEffectId() {
        return effectId;
    }

    public void setEffectId(Long effectId) {
        this.effectId = effectId;
    }
}
