package com.programming.userservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.userservice.core.persistent.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends BaseRepository<User> {
    @Query("select u from User u where u.username = ?1 and u.email = ?2")
    User findByUserNameAndPassword(String username, String password);
}
