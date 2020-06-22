package core.rpc.dto;

import core.dto.item.dto.CardDTO;
import core.dto.item.dto.EnvoyDTO;
import lombok.Data;

import java.util.List;

@Data
public class DeckRpcDTO {
    private Long userId;
    private String deckId;
    //棋子，配置——棋子的屬性，本身的和玩家特有的屬性結合
    private List<EnvoyRpcDTO> envoyDTOs;
    //卡牌，配置
    private List<CardRpcDTO> cardDTOS;
    private ProfessionRpcDTO professionRpcDTO;

}
