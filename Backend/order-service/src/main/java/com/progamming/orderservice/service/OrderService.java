package com.progamming.orderservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseServiceImpl;
import com.progamming.orderservice.domain.dto.OrdersDto;
import com.progamming.orderservice.domain.mapper.OrderMapper;
import com.progamming.orderservice.domain.persistent.entity.Orders;
import com.progamming.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService extends BaseServiceImpl<Orders, OrdersDto> {
    private final BaseRepository orderRepository;
    private final BaseMapper orderMapper;

    @Override
    protected BaseRepository<Orders> getBaseRepository() {
        return orderRepository;
    }

    @Override
    protected BaseMapper<Orders, OrdersDto> getBaseMapper() {
        return orderMapper;
    }

    @Override
    protected Page<OrdersDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<OrdersDto> getListSearchResults(String keyword) {
        return null;
    }
}
