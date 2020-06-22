package core.rpc.dto;

import dist.ItemConstants;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProfessionRpcDTO implements Serializable{
    private String id;
    private Long metaEnvoyId;
    private String roomId;
    private ItemConstants.Attribute attribute;//属性(netty.code)
    private ItemConstants.Race race;//种族(netty.code)
    private ItemConstants.Grade grade;//品级

    private Integer attack;//攻击力
    private Integer incrAttack;//成长攻击力
    private Integer defense;//防御
    private Integer incrDefense;//成长防御
    private Integer hp;//血量
    private Integer incrHp;//成长血量
    private Integer move;//移动力
    private Integer attackDistance;//攻击距离
    private Integer criticalRate;//暴击率
    private Integer x;//坐标
    private Integer y;//坐标
}
