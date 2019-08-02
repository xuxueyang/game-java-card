package itemmanager.domain;

import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "it_item")
public class Item extends BaseEntity {
    @Column(name = "name",length = 40,nullable = false)
    private String name;

    @Column(name = "description",length = 255)
    private String description;

    @Column(name = "type",length = 40)
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
