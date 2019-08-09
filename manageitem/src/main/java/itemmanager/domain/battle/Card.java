package itemmanager.domain.battle;

import core.core.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// 卡牌类
@Entity
@Table(name = "it_card")
@Data
public class Card extends BaseEntity {

    @Column(nullable = false,name = "grade",length = 10)
    private String grade;
    @Column(nullable = false,name = "name",length = 20)
    private String name;
    @Column(nullable = false,name = "imgPath",length = 255)
    private String imgPath;
    @Column(nullable = false,name = "effectDescription",length = 255)
    private String effectDescription;
    @Column(nullable = false,name = "smallDescription",length = 255)
    private String smallDescription;
    @Column(nullable = false,name = "feiyong",length = 10)
    private Integer feiyong;
    @Column(nullable = false,name = "type",length = 10)
    private String type;

    @Column(nullable = false,name = "effectId",length = 10)
    private Long effectId;

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getImgPath() {
//        return imgPath;
//    }
//
//    public void setImgPath(String imgPath) {
//        this.imgPath = imgPath;
//    }
//
//    public String getEffectDescription() {
//        return effectDescription;
//    }
//
//    public void setEffectDescription(String effectDescription) {
//        this.effectDescription = effectDescription;
//    }
//
//    public String getSmallDescription() {
//        return smallDescription;
//    }
//
//    public void setSmallDescription(String smallDescription) {
//        this.smallDescription = smallDescription;
//    }
//
//    public Integer getFeiyong() {
//        return feiyong;
//    }
//
//    public void setFeiyong(Integer feiyong) {
//        this.feiyong = feiyong;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getGrade() {
//        return grade;
//    }
//
//    public void setGrade(String grade) {
//        this.grade = grade;
//    }
//
//
//    public Long getEffectId() {
//        return effectId;
//    }
//
//    public void setEffectId(Long effectId) {
//        this.effectId = effectId;
//    }
}
