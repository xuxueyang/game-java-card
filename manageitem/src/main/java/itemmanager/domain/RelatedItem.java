package itemmanager.domain;

import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "it_related_item")
public class RelatedItem extends BaseEntity {
    @Column(name = "related_id",length = 20)
    private Long relatedId;

    @Column(name = "num",length = 5,nullable = false)
    private Integer num;

    @Column(name = "item_id",length = 20,nullable = false)
    private Long item_id;

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }
}
