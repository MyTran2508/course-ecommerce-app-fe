package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.userservice.domain.dto.MonthlyStatisticDto;
import com.programming.userservice.domain.dto.OrderDto;
import com.programming.userservice.domain.dto.StatictisSamePeriodDto;
import com.programming.userservice.domain.persistent.entity.Order;
import com.programming.userservice.service.OrderService;
import com.programming.userservice.utilities.annotation.ShowOpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @ShowOpenAPI
    public DataResponse<String> add(OrderDto objectDTO) {
        return super.add(objectDTO);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<OrderDto> update(OrderDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @Override
    @ShowOpenAPI
    public ListResponse<OrderDto> getAll() {
        return super.getAll();
    }

    @Override
    @ShowOpenAPI
    public DataResponse<OrderDto> getById(String id) {
        return super.getById(id);
    }

    @GetMapping("/monthly-sales")
    @ShowOpenAPI
    public DataResponse<List<MonthlyStatisticDto>> getMonthlySales(@Param("targetYear") int targetYear) {
        return orderService.getMonthlySales(targetYear);
    }

    @GetMapping("/sales-same-period")
    @ShowOpenAPI
    public DataResponse<List<StatictisSamePeriodDto>> getSalesSamePeriod(@Param("targetYear") int targetYear) {
        return orderService.getSalesInSamePeriod(targetYear);
    }
}
