package com.progamming.orderservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.model.BaseMapperImpl;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseServiceImpl;
import com.progamming.orderservice.domain.dto.OrderDto;
import com.progamming.orderservice.domain.mapper.OrderMapper;
import com.progamming.orderservice.domain.persistent.entity.Order;
import com.progamming.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService extends BaseServiceImpl<Order, OrderDto> {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    protected BaseRepository<Order> getBaseRepository() {
        return orderRepository;
    }

    @Override
    protected BaseMapperImpl<Order, OrderDto> getBaseMapper() {
        return orderMapper;
    }

    @Override
    protected Page<OrderDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<OrderDto> getListSearchResults(String keyword) {
        return null;
    }
}
