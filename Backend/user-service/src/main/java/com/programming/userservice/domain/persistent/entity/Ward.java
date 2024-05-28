package com.programming.userservice.domain.persistent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "wards",
        indexes = {
                @Index (name = "idx_wards_district_code", columnList = "district_code")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ward {

    @Id
    @Column(length = 16)
    private String code;

    @Column(length = 32)
    private String name;

    @Column(name = "name_en", length = 32)
    private String nameEn;

    @Column(name = "full_name", length = 64)
    private String fullName;

    @Column(name = "full_name_en", length = 64)
    private String fullNameEn;

    @Column(name = "code_name", length = 32)
    private String codeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_code", foreignKey = @ForeignKey(name = "fk_wards_districts"))
    private District district;
}
