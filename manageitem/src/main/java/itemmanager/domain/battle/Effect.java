package itemmanager.domain.battle;

import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// 卡牌类
@Entity
@Table(name = "it_effect")
public class Effect extends BaseEntity {

    @Column(nullable = false,name = "description",length = 255)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @Column(nullable = false,name = "effectCode",length = 30)
//    private String effectCode;
//    @Column(nullable = false,name = "cardId",length = 30)
//    private Long cardId;


//
//    public Long getCardId() {
//        return cardId;
//    }
//
//    public void setCardId(Long cardId) {
//        this.cardId = cardId;
//    }
//
//    public String getEffectCode() {
//        return effectCode;
//    }
//
//    public void setEffectCode(String effectCode) {
//        this.effectCode = effectCode;
//    }
}
