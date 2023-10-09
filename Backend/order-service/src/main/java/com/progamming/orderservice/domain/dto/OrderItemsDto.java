package com.progamming.orderservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto {
    private String Id;
    private String courseId;
    private Integer quantity;
    private Double price;
}
