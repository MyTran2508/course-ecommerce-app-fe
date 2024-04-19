package com.programming.userservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.userservice.domain.persistent.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    @Query("select u from User u where u.username = ?1 and u.email = ?2")
    User findByUserNameAndPassword(String username, String password);

    @Query("select u from User u where u.username = ?1")
    User findByUserName(String username);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("Update User u set u.password = :newPassword WHERE u.id = :id")
    void changePassword(String id, String newPassword);

    @Query("""
                select u from User u where u.username LIKE %:keyword% or 
                u.email LIKE %:keyword% OR :keyword IS NULL
            """)
    Page<User> searchUser(@Param("keyword") String keyword, Pageable pageable);


    /*

    @Query(value = "SELECT MONTH(FROM_UNIXTIME(created / 1000)) as month, SUM(total_price) as total " +
            "FROM orders " +
            "WHERE YEAR(FROM_UNIXTIME(created / 1000)) = :targetYear " +
            "GROUP BY MONTH(FROM_UNIXTIME(created / 1000))", nativeQuery = true)
     */
    @Query(value = "SELECT COUNT(*) FROM user WHERE YEAR(FROM_UNIXTIME(created / 1000)) = :targetYear " +
            "and (MONTH(FROM_UNIXTIME(created / 1000)) = :targetMonth OR :targetMonth IS NULL)", nativeQuery = true)
    Integer countByYearAnhMonth(@Param("targetYear") Integer targetYear, @Param("targetMonth") Integer targetMonth);
}
