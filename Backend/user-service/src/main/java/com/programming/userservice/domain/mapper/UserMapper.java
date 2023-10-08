package com.programming.userservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.userservice.domain.dto.UserDto;
import com.programming.userservice.domain.persistent.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper extends BaseMapperImpl<User, UserDto> {
    public UserMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
    @Override
    protected Class<UserDto> getDtoClass() {
        return UserDto.class;
    }
}
