package com.programming.userservice.core.persistent.entity;

import com.main.ocean.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "role",
        uniqueConstraints ={
                @UniqueConstraint(columnNames = "name", name = "uq_role_name")
        }
)
public class Role extends BaseModel {
    @Column(nullable = false, length = 64)
    private String name;
    @Column(length = 512)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_users_roles_roles"),
            inverseForeignKey = @ForeignKey(name = "fk_users_roles_users")
    )
    private Set<User> users;
}
