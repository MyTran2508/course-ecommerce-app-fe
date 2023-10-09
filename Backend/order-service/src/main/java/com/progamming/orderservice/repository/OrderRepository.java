package com.progamming.orderservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.progamming.orderservice.domain.dto.OrdersDto;
import com.progamming.orderservice.domain.persistent.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Orders> {
}
