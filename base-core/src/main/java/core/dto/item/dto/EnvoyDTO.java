package core.dto.item.dto;

import lombok.Data;


@Data
public class EnvoyDTO {
    private Long id;
    private String attribute;//属性(netty.code)
    private String attributeName;//属性(netty.code)

//    private String race;//种族名字(netty.code)

    private String name;//名字

//    private String icon;//头像
    private String grade;//品级
    private String gradeName;//品级

    private Integer starForce;//星辰值

//    private Integer maxPlusStarForce=20;//最大的星辰值

    private Long attack;//攻击力
//    private Integer incrAttack;//成长攻击力

    private Long defense;//防御
//    private Integer incrDefense;//成长防御
    private Long hp;//血量
//    private Integer incrHp;//成长血量
    private Integer move;//移动力
    private Integer attackDistance;//攻击距离
    private Integer criticalRate = 10;//暴击率
    private String description;



}
