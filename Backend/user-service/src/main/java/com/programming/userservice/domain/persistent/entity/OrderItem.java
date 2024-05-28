package com.programming.userservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Table(
        name = "order_items"
)
public class OrderItem extends BaseModel {

    @Column(name = "course_id")
    private String courseId;

    private Double price;
}
