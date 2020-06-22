package core.rpc.dto;

import dist.ItemConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CardRpcDTO implements Serializable{
    private String id;
    private Long metaCardId;//关联的卡牌，以获取资源，房间没必要存储没用的
    private Integer startForce;
    private ItemConstants.CardType cardType;
//    private Long effectId;
    private List<String> skillIds;

}
