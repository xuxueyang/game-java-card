package core.rpc.dto;

import core.dto.item.dto.CardDTO;
import core.dto.item.dto.EnvoyDTO;

import java.util.List;

public class DeckRpcDTO {
    private Long userId;
    private String deckId;
    //棋子，配置——棋子的屬性，本身的和玩家特有的屬性結合
    private List<EnvoyRpcDTO> envoyDTOs;
    //卡牌，配置
    private List<CardRpcDTO> cardDTOS;


    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }


    public List<EnvoyRpcDTO> getEnvoyDTOs() {
        return envoyDTOs;
    }

    public void setEnvoyDTOs(List<EnvoyRpcDTO> envoyDTOs) {
        this.envoyDTOs = envoyDTOs;
    }

    public List<CardRpcDTO> getCardDTOS() {
        return cardDTOS;
    }

    public void setCardDTOS(List<CardRpcDTO> cardDTOS) {
        this.cardDTOS = cardDTOS;
    }
}
