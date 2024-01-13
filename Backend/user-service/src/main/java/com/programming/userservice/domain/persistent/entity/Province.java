package com.programming.userservice.domain.persistent.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "provinces"
)
public class Province {
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

    @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
    private List<District> districtList;
}
