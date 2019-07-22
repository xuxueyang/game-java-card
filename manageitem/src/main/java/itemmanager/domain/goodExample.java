package itemmanager.domain;

import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "item_good_example")
public class goodExample extends BaseEntity {
    @Column(name = "name",length = 40,nullable = false)
    private String name;

    @Column(name = "good_id",length = 25)
    private Long goodId;

    @Column(name = "num",length = 10)
    private Integer num;

    @Column(name = "position_x",length = 5)
    private Integer positionX;

    @Column(name = "position_y",length = 5)
    private Integer positionY;

    @Column(name = "type",length = 10)
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
