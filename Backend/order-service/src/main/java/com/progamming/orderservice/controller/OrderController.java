package com.progamming.orderservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.progamming.orderservice.domain.dto.OrderDto;
import com.progamming.orderservice.domain.persistent.entity.Order;
import com.progamming.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController extends BaseApiImpl<Order, OrderDto> {
    private final OrderService orderService;
    @Override
    protected BaseService<Order, OrderDto> getBaseService() {
        return orderService;
    }

    @Override
    public DataResponse<OrderDto> add(OrderDto objectDTO) {
        DataResponse<OrderDto> response = super.add(objectDTO);
        if(response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {
            // call api add course access
        }
        return response;
    }
}
