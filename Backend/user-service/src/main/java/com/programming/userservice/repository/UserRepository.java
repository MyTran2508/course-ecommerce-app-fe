package com.programming.userservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.userservice.domain.persistent.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

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
                u.email LIKE %:keyword%
            """)
    Page<User> searchUser(@Param("keyword") String keyword, Pageable pageable);
}
