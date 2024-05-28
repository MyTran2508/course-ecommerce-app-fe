package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.SystemUtil;
import com.programming.userservice.domain.dto.MonthlyStatisticDto;
import com.programming.userservice.domain.dto.OrderDto;
import com.programming.userservice.domain.dto.SearchOrderDto;
import com.programming.userservice.domain.dto.StatictisSamePeriodDto;
import com.programming.userservice.domain.persistent.entity.Order;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.domain.persistent.entity.UserLog;
import com.programming.userservice.domain.persistent.enumrate.ActionName;
import com.programming.userservice.domain.persistent.enumrate.ActionObject;
import com.programming.userservice.repository.OrdersRepository;
import com.programming.userservice.service.OrderService;
import com.programming.userservice.service.UserLogService;
import com.programming.userservice.utilities.annotation.ShowOpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/order")
public class OrderController extends BaseApiImpl<Order, OrderDto> {

    private final OrderService orderService;

    private final OrdersRepository ordersRepository;

    private final UserLogService userLogService;

    @Override
    protected BaseService<Order, OrderDto> getBaseService() {
        return orderService;
    }

    @Override
    @ShowOpenAPI
    public DataResponse<String> add(OrderDto objectDTO) {
        DataResponse<String> response = super.add(objectDTO);

        if (response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {
            String stResult = response.getData();
            String orderId = stResult.split(": ")[1].trim();
            Order order = ordersRepository.findById(orderId).orElse(null);

            // Add log
            UserLog userLog = UserLog.builder()
                    .userName(SystemUtil.getCurrentUsername())
                    .ip(SystemUtil.getUserIP())
                    .actionKey(orderId)
                    .actionObject(ActionObject.ORDER)
                    .actionName(ActionName.CREATE)
                    .description(userLogService.writePersistLog(Order.class, order, true, 0))
                    .build();
            userLogService.addLog(userLog);
        }

        return response;
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

    @PostMapping("/filter-order")
    @ShowOpenAPI
    public ListResponse<OrderDto> filterOrder(
            @RequestBody SearchOrderDto searchOrderDto
    ) {
        return orderService.searchOrderByCondition(searchOrderDto);
    }

    //    @GetMapping("/get-search-orders")
//    @ShowOpenAPI
//    public ListResponse<OrderDto> getSearchOrders(
//            @RequestParam("type-search") Integer typeSearch,
//            @RequestParam("keyword") String keyword
//    ) {
//        return orderService.getSearchOrders(typeSearch, keyword);
//    }
}
