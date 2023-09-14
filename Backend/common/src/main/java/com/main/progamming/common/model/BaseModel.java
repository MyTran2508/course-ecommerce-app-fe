package com.main.progamming.common.model;

import com.main.progamming.common.util.SystemUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
@DynamicUpdate
@DynamicInsert
public class BaseModel {
    @Id
    private String id;
    private Long created;
    private String creator;
    private Long updated;
    private String modifier;
    private Boolean removed;
    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
        this.setCreated(System.currentTimeMillis());
        this.setUpdated(System.currentTimeMillis());
        this.setCreator(SystemUtil.getCurrentUsername());
        this.setRemoved(false);
    }

    @PreUpdate
    private void setUpdated() {
        this.setModifier(SystemUtil.getCurrentUsername());
        this.setUpdated(System.currentTimeMillis());
    }

    protected boolean equal(BaseModel baseModel) {
        return this.id == baseModel.getId();
    }

    protected String getKeyDesc() {
        return this.id;
    }
}
