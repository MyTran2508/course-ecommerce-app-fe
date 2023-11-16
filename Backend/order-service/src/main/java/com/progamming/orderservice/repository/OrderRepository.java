package com.progamming.orderservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.progamming.orderservice.domain.persistent.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order> {
}
