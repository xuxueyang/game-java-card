package acct.domain;



import core.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mg_graph")
public class Graph extends BaseEntity implements Serializable{


    @Column(name = "VALUE",length = 255)
    private String value;

    @Column(name = "TYPE",length = 255)
    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
