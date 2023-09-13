package com.main.ocean.common.model;

import com.main.ocean.common.util.SystemUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
public class BaseModel {
    @Id
    private String id;
    private Long created;
    private String creator;
    private Long updated;
    private String modifier;
    private Short status;
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
}
