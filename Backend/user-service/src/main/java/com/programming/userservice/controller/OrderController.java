package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.userservice.domain.dto.OrderDto;
import com.programming.userservice.domain.persistent.entity.Order;
import com.programming.userservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/order")
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
