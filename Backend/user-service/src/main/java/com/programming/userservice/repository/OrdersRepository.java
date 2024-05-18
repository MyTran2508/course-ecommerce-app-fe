package com.programming.userservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.userservice.domain.persistent.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends BaseRepository<Order> {

    @Query(value = "SELECT MONTH(FROM_UNIXTIME(created / 1000)) as month, SUM(total_price) as total " +
            "FROM orders " +
            "WHERE YEAR(FROM_UNIXTIME(created / 1000)) = :targetYear " +
            "GROUP BY MONTH(FROM_UNIXTIME(created / 1000))", nativeQuery = true)
    List<Object[]> getTotalPriceByMonth(@Param("targetYear") int targetYear);

    @Query(value = "SELECT SUM(total_price) FROM orders WHERE YEAR(FROM_UNIXTIME(created / 1000)) = :targetYear " +
            "AND (MONTH(FROM_UNIXTIME(created / 1000)) = :targetMonth OR :targetMonth IS NULL)", nativeQuery = true)
    Double getTotalRenevueByYearAndMonth(@Param("targetYear") int targetYear,
                                          @Param("targetMonth") Integer targetMonth);

    @Query("""
                SELECT o FROM Order o
                WHERE ((:isEmptyUsernameList = true OR o.user.username IN :keywordUsers)
                OR (
                    (o.user.username LIKE %:likeUsername% OR :likeUsername IS NULL)
                ))
                AND (
                    (:totalPrice IS NULL OR o.totalPrice = :totalPrice)
                    AND (
                        (:minTotalPrice IS NULL OR o.totalPrice >= :minTotalPrice)
                        AND (:maxTotalPrice IS NULL OR o.totalPrice <= :maxTotalPrice)
                    )
                )
                AND (
                    (:startDate IS NULL OR o.created >= :startDate)
                    AND (:endDate IS NULL OR o.created <= :endDate)
                )
            """)
    Page<Order> searchOrderByCondition(@Param("isEmptyUsernameList") Boolean isEmptyUsernameList,
                                        @Param("keywordUsers") List<String> keywordUsers,
                                        @Param("likeUsername") String likeUsername,
                                        @Param("minTotalPrice") Double minTotalPrice,
                                        @Param("maxTotalPrice") Double maxTotalPrice,
                                        @Param("totalPrice") Double totalPrice,
                                        @Param("startDate") Long startDate,
                                        @Param("endDate") Long endDate,
                                        Pageable pageable);
}
