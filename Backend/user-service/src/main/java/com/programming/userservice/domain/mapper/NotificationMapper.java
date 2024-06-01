package com.programming.userservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.userservice.domain.dto.NotificationDto;
import com.programming.userservice.domain.persistent.entity.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class NotificationMapper extends BaseMapperImpl<Notification, NotificationDto> {
    public NotificationMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Notification> getEntityClass() {
        return Notification.class;
    }

    @Override
    protected Class<NotificationDto> getDtoClass() {
        return NotificationDto.class;
    }
}
