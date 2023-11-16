package com.progamming.orderservice.domain.persistent.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.progamming.orderservice.domain.dto.OrderDto;
import com.progamming.orderservice.domain.dto.OrderItemDto;
import com.progamming.orderservice.domain.persistent.entity.Order;
import com.progamming.orderservice.domain.persistent.entity.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderItemMapper extends BaseMapperImpl<OrderItem, OrderItemDto> {
    public OrderItemMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<OrderItem> getEntityClass() {
        return OrderItem.class;
    }

    @Override
    protected Class<OrderItemDto> getDtoClass() {
        return OrderItemDto.class;
    }
}
