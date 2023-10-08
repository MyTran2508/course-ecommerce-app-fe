package com.programming.userservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "locations"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Location extends BaseModel {
    @Column(nullable = false)
    private String province;
    private String district;
    private String ward;
}
