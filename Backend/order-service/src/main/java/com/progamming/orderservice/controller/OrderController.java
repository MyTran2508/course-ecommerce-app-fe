package com.progamming.orderservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.service.BaseService;
import com.progamming.orderservice.domain.dto.OrdersDto;
import jakarta.persistence.criteria.Order;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController extends BaseApiImpl<Order, OrdersDto> {
    @Override
    protected BaseService<Order, OrdersDto> getBaseService() {
        return null;
    }
}
