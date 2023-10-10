package com.programming.userservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email", name = "uq_users_email"),
                @UniqueConstraint(columnNames = "username", name = "uq_users_username")
        }
)
@DynamicInsert
@DynamicUpdate
public class User extends BaseModel {
    @Column(nullable = false)
    private String password;
    @Column(length = 200)
    private String email;
    @Column(length = 150)
    private String username;
    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;
    @Column(name = "telephone")
    private String telephone;
    @Column(length = 512)
    private String photos;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id"),
        foreignKey = @ForeignKey(name = "fk_users_roles_users"),
        inverseForeignKey = @ForeignKey(name = "fk_users_roles_roles")
    )
    private Set<Role> roles;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Address> addresses;
}
