package com.progamming.orderservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.progamming.orderservice.domain.dto.OrderDto;
import com.progamming.orderservice.domain.persistent.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper extends BaseMapperImpl<Order, OrderDto> {
    public OrderMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Order> getEntityClass() {
        return Order.class;
    }

    @Override
    protected Class<OrderDto> getDtoClass() {
        return OrderDto.class;
    }
}
