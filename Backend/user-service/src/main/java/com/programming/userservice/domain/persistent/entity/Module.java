package com.programming.userservice.domain.persistent.entity;

import com.programming.userservice.domain.persistent.enumrate.ModuleName;
import com.programming.userservice.utilities.annotation.ExcludeFromComparisonField;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

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
public class Module implements Serializable {

    @Serial
    @ExcludeFromComparisonField
    private static final long serialVersionUID = 1L;

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
