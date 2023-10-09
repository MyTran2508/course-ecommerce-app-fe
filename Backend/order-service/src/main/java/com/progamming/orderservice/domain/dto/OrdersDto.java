package com.progamming.orderservice.domain.dto;

import com.progamming.orderservice.domain.persistent.enumrate.OrderStatus;
import com.progamming.orderservice.domain.persistent.enumrate.ShippingMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {
    private Double totalAmount;
    private OrderStatus orderStatus;
    private ShippingMethod shippingMethod;
    List<OrderItemsDto> orderItemsList;
}
