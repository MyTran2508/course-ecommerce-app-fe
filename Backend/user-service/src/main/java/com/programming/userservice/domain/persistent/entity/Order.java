package com.programming.userservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.userservice.domain.persistent.enumrate.OrderStatus;
import com.programming.userservice.domain.persistent.enumrate.ShippingMethod;
import com.programming.userservice.utilities.annotation.ExcludeFromComparisonField;
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

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "shipping_methodd")
    private ShippingMethod shippingMethod;

    @OneToMany(targetEntity = OrderItem.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_items_order"))
    List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_order_user"))
    @ExcludeFromComparisonField
    private User user;
}
