package core.core;


import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by UKi_Hi on 2019/7/15.
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 20)
    private Long id;


    @Column(name = "space_code",length = 32)
    private String spaceCode;

    @Column(name = "is_deleted",length = 2)
    private Integer isDeleted = 0;

    @Column(name = "created_date")
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate = ZonedDateTime.now();
    @Column(name = "created_by",length = 20)
    private Long createdBy;
    @Column(name = "updated_by",length = 20)
    private Long updatedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpaceCode() {
        return spaceCode;
    }

    public void setSpaceCode(String spaceCode) {
        this.spaceCode = spaceCode;
    }


    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
