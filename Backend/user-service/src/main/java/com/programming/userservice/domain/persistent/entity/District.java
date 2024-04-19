package com.programming.userservice.domain.persistent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(
        name = "districts",
        indexes = {
                @Index (name = "idx_districts_province_code", columnList = "province_code")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class District {
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
    @JoinColumn(name = "province_code", foreignKey = @ForeignKey(name = "fk_districts_provinces"))
    private Province province;

    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    private List<Ward> wardList;
}
