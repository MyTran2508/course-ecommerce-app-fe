package com.programming.userservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.userservice.domain.dto.MonthlyStatisticDto;
import com.programming.userservice.domain.persistent.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order> {
    @Query(value = "SELECT MONTH(FROM_UNIXTIME(created / 1000)) as month, SUM(total_price) as total " +
            "FROM orders " +
            "WHERE YEAR(FROM_UNIXTIME(created / 1000)) = :targetYear " +
            "GROUP BY MONTH(FROM_UNIXTIME(created / 1000))", nativeQuery = true)
    List<Object[]> getTotalPriceByMonth(@Param("targetYear") int targetYear);

//    @Query(value = "SELECT TOTAL(totalPrice) FROM orders WHERE YEAR(FROM_UNIXTIME(created / 1000)) = :targetYear " +
//            "AND (MONTH(FROM_UNIXTIME(created / 1000)) = :targetMonth OR :targetMonth IS NULL)", nativeQuery = true)
//    Integer getTotalRenevueByYearAndMonth(@Param("targetYear") int targetYear,
//                                          @Param("targetMonth") Integer targetMonth);
}
