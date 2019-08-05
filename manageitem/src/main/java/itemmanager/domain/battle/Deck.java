package itemmanager.domain.battle;

import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// 卡组类
@Entity
@Table(name = "it_deck")
public class Deck extends BaseEntity {



    public static enum Type{
        ENVOY,
        CARD
    }
    public static enum likeType{
        ATTACK,
        Defense,
        Blood
    }
    @Column(nullable = false,name = "type",length = 10)
    private String type;//區別棋子還是卡牌,ENVOY是棋子，CARD是卡牌
    @Column(nullable = false,name = "relatedId",length = 30)
    private Long relatedId;//關聯的ID
    @Column(nullable = false,name = "userId",length = 30)
    private Long userId;//

    //有卡牌數量限制，金卡（專屬卡）只能携帶一張
    //有棋子的偏好選擇
    @Column(name = "likeType",length = 10)
    private String likeType;//棋子的偏好選擇
    @Column(name = "area",length = 10)
    private String area;//區服標記

    @Column(name = "deckId",length = 40)
    private String deckId;//區服標記(group by)

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLikeType() {
        return likeType;
    }

    public void setLikeType(String likeType) {
        this.likeType = likeType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }
}
