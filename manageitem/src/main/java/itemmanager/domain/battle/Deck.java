package itemmanager.domain.battle;

import core.core.BaseEntity;
import core.protocol.PvpTwoRoomProtocol;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// 卡组类
@Entity
@Table(name = "it_deck")
@Data
public class Deck extends BaseEntity {



    @Column(nullable = false,name = "type",length = 10)
    private PvpTwoRoomProtocol.DeckType type;//區別棋子還是卡牌,ENVOY是棋子，CARD是卡牌,还有职业！
    @Column(nullable = false,name = "relatedId",length = 30)
    private Long relatedId;//關聯的ID
    @Column(nullable = false,name = "userId",length = 30)
    private Long userId;//

    //有卡牌數量限制，金卡（專屬卡）只能携帶一張
    //有棋子的偏好選擇
//    @Column(name = "likeType",length = 10)
//    private String likeType;//棋子的偏好選擇
//    @Column(name = "area",length = 10)
//    private String area;//區服標記

    @Column(name = "deckId",length = 40)
    private String deckId;//區服標記(group by)

    @Column(name = "actived",length = 2)
    private Integer actived = 1;//選中的卡組
 }
