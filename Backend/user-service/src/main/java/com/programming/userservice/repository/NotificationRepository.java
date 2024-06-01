package com.programming.userservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.userservice.domain.persistent.entity.Notification;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends BaseRepository<Notification> {
}
