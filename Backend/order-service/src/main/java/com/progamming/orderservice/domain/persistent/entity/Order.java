package com.progamming.orderservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.progamming.orderservice.domain.persistent.enumrate.OrderStatus;
import com.progamming.orderservice.domain.persistent.enumrate.ShippingMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@Table(
        name = "orders"
)
public class Order extends BaseModel {
    @Column(name = "total_amount")
    private Double totalAmount;
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "shipping_methodd")
    private ShippingMethod shippingMethod;
    @OneToMany(targetEntity = OrderItem.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_items_order"))
    List<OrderItem> orderItems;
}
