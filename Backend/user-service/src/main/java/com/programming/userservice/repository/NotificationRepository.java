package com.programming.userservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.userservice.domain.persistent.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends BaseRepository<Notification> {

    @Query("""
                SELECT n
                    FROM Notification n
                    WHERE n.username = :username
                    ORDER BY n.created DESC
            """)
    Page<Notification> getByUsername(String username, Pageable pageable);
}
