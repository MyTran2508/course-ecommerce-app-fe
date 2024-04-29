package com.programming.userservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
        name = "role_details"
)
public class RoleDetail extends BaseModel {

    @Column(name = "can_view")
    private Boolean canView;

    @Column(name = "can_create")
    private Boolean canCreate;

    @Column(name = "can_update")
    private Boolean canUpdate;

    @Column(name = "can_remove")
    private Boolean canRemove;

    @Column(name = "can_statistics")
    private Boolean canStatistics;

    @ManyToOne(targetEntity = Module.class)
    @JoinColumn(name = "module_id", foreignKey = @ForeignKey(name = "fk_role_details_module"))
    private Module module;
}
