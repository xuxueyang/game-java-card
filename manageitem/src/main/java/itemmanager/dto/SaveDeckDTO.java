package itemmanager.dto;

import core.protocol.PvpTwoRoomProtocol;
import lombok.Data;

@Data
public class SaveDeckDTO  {
    //保存卡組,棋子，卡牌
    private PvpTwoRoomProtocol.DeckType type;
    private Long relatedId;
//    private String likeType;


}
