package itemmanager.domain.battle;

import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// 卡牌类
@Entity
@Table(name = "it_related_card")
public class RelatedCard extends BaseEntity {
    @Column(nullable = false,name = "userId",length = 30)
    private Long userId;
    @Column(nullable = false,name = "cardId",length = 30)
    private Long cardId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
