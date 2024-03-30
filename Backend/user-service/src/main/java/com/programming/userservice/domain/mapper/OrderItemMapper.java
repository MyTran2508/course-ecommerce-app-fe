package com.programming.userservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.userservice.domain.dto.OrderItemDto;
import com.programming.userservice.domain.persistent.entity.OrderItem;
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
