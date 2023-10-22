package com.progamming.orderservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.progamming.orderservice.domain.dto.OrdersDto;
import jakarta.persistence.criteria.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper extends BaseMapperImpl<Order, OrdersDto> {
    public OrderMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Order> getEntityClass() {
        return null;
    }

    @Override
    protected Class<OrdersDto> getDtoClass() {
        return null;
    }
}
