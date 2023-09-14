package com.programming.userservice.core.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.userservice.core.dto.UserDto;
import com.programming.userservice.core.persistent.entity.User;
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
