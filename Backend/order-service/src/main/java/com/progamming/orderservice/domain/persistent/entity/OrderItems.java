package com.progamming.orderservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
@Table(
        name = "order_items"
)
public class OrderItems extends BaseModel {
    @Column(name = "course_id")
    private String courseId;
    private Integer quantity;
    private Double price;
    @ManyToOne(targetEntity = Orders.class)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_items_orders"))
    private Orders orders;

}
