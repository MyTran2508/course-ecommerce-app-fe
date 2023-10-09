package com.progamming.orderservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.progamming.orderservice.domain.persistent.enumrate.OrderStatus;
import com.progamming.orderservice.domain.persistent.enumrate.ShippingMethod;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
@Table(
        name = "orders"
)
public class Orders extends BaseModel {
    @Column(name = "total_amount")
    private Double totalAmount;
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "shipping_methodd")
    private ShippingMethod shippingMethod;
    @OneToMany(mappedBy = "orders", fetch = FetchType.EAGER)
    List<OrderItems> orderItemsList;
}
