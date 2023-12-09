package com.programming.userservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
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
@ToString(callSuper = true)
public class Role extends BaseModel {
    @Column(length = 64)
    private String name;
    @Column(length = 512)
    private String description;
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_users_roles_roles"),
            inverseForeignKey = @ForeignKey(name = "fk_users_roles_users")
    )
    @ToString.Exclude
    private List<User> users;

    public Role(String id) {
        super(id);
    }
}
