package com.programming.userservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.userservice.utilities.annotation.ExcludeFromComparisonField;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
    @ExcludeFromComparisonField
    private String description;

    @ManyToMany()
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_users_roles_roles"),
            inverseForeignKey = @ForeignKey(name = "fk_users_roles_users")
    )
    @ToString.Exclude
    @ExcludeFromComparisonField
    private List<User> users;

    @OneToMany(targetEntity = RoleDetail.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_role_details_role"))
    @OrderBy("module.id DESC ")
    private List<RoleDetail> roleDetails;

    public Role(String id) {
        super(id);
    }
}
