package com.programming.userservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.domain.persistent.entity.UserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface UserLogRepository extends BaseRepository<UserLog> {

    @Query("""
                SELECT ul FROM UserLog ul
                WHERE (:startTime IS NULL OR (ul.created >= :startTime AND ul.created <= :endTime))
                AND (:userName IS NULL OR ul.userName LIKE %:userName%)
            """)
    Page<UserLog> filterUserLog(Long startTime, Long endTime, String userName, Pageable pageable);
}
