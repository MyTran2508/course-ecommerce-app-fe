package com.programming.userservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.userservice.utilities.annotation.ExcludeFromComparisonField;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.awt.print.Book;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
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
@ToString(callSuper = true)
public class User extends BaseModel {
    @Column(nullable = false)
    @ExcludeFromComparisonField
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

    @Lob
    @Column(name = "file_bytes", columnDefinition = "MEDIUMBLOB")
    @ExcludeFromComparisonField
    private byte[] avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id"),
        foreignKey = @ForeignKey(name = "fk_users_roles_users"),
        inverseForeignKey = @ForeignKey(name = "fk_users_roles_roles")
    )
    private List<Role> roles;

    @OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_address_user"))
    private List<Address> addresses;
}
