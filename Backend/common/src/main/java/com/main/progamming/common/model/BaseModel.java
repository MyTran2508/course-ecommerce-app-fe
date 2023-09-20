package com.main.progamming.common.model;

import com.main.progamming.common.util.SystemUtil;
import lombok.*;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private Long created;
    private String creator;
    private Long updated;
    private String modifier;
    private Boolean removed;
    @PrePersist
    protected void ensureId() {
        this.setId(UUID.randomUUID().toString());
        this.setCreated(System.currentTimeMillis());
        this.setUpdated(System.currentTimeMillis());
        this.setCreator(SystemUtil.getCurrentUsername());
        this.setRemoved(false);
    }

    @PreUpdate
    protected void setUpdated() {
        this.setModifier(SystemUtil.getCurrentUsername());
        this.setUpdated(System.currentTimeMillis());
    }
    protected String getKeyDesc() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id='" + id + '\'' +
                ", created=" + created +
                ", creator='" + creator + '\'' +
                ", updated=" + updated +
                ", modifier='" + modifier + '\'' +
                ", removed=" + removed +
                '}';
    }

    public BaseModel(String id) {
        this.id = id;
    }
}
