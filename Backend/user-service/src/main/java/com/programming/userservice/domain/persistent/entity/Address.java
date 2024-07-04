package com.programming.userservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.userservice.utilities.annotation.ExcludeFromComparisonField;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(
        name = "address"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@SuperBuilder(toBuilder = true)
public class Address extends BaseModel implements Serializable {

    @Serial
    @ExcludeFromComparisonField
    private static final long serialVersionUID = 1L;

    @Column(name = "address_line", length = 255)
    private String addressLine;

    @Column(name = "postal_code", length = 16)
    private String postalCode;

    @Column(name = "default_address", columnDefinition = "bit default 0 not null")
    private boolean defaultAddress;

    @Column(name = "province_name", length = 32)
    private String provinceName;

    @Column(name = "province_name_en", length = 32)
    private String provinceNameEn;

    @Column(name = "district_name", length = 32)
    private String districtName;

    @Column(name = "district_name_en", length = 32)
    private String districtNameEn;

    @Column(name = "ward_name", length = 32)
    private String wardName;

    @Column(name = "ward_name_en", length = 32)
    private String wardNameEn;
}
