package itemmanager.dto;

import java.util.Map;

public class AdminUpdateCardDTO {
    private Long id;//如果有ID的话视为更新，不然为创建
    private String grade;

    private String name;

    private String imgPath;
    private String effectDescription;
    private String smallDescription;
    private Integer feiyong;
    private String type;
    private String effectCode;//TODO 卡牌和特效的绑定
    private Map jsonMap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getEffectDescription() {
        return effectDescription;
    }

    public void setEffectDescription(String effectDescription) {
        this.effectDescription = effectDescription;
    }

    public String getSmallDescription() {
        return smallDescription;
    }

    public void setSmallDescription(String smallDescription) {
        this.smallDescription = smallDescription;
    }

    public Integer getFeiyong() {
        return feiyong;
    }

    public void setFeiyong(Integer feiyong) {
        this.feiyong = feiyong;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEffectCode() {
        return effectCode;
    }

    public void setEffectCode(String effectCode) {
        this.effectCode = effectCode;
    }

    public Map getJsonMap() {
        return jsonMap;
    }

    public void setJsonMap(Map jsonMap) {
        this.jsonMap = jsonMap;
    }
}
