package com.programming.userservice.domain.persistent.entity;

import com.programming.userservice.domain.persistent.enumrate.ModuleName;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(
        name = "module",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "module_name", name = "uq_module_name")
        }
)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Module {

    @Id
    private Integer id;

    @Column(name = "module_name")
    private ModuleName moduleName;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @PrePersist
    protected void ensureId() {
        this.isDeleted = false;
    }
}
