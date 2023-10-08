package com.programming.userservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "addresses",
        indexes = {
                @Index(columnList = "user_id", name = "idx_addresses_user_id"),
                @Index(columnList = "location_id", name = "idx_addresses_location_id")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address extends BaseModel {
    @Column(name = "address_line", length = 255)
    private String addressLine;
    @Column(name = "postal_code", length = 16)
    private String postalCode;
    @Column(name = "default_address", columnDefinition = "bit default 0 not null")
    private boolean defaultAddress;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_addresses_users"))
    private User user;
    @ManyToOne(targetEntity = Location.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "fk_addresses_location"))
    private Location location;
}
