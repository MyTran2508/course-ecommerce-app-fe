package com.programming.userservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.userservice.domain.dto.RoleDto;
import com.programming.userservice.domain.persistent.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper extends BaseMapperImpl<Role, RoleDto> {

    public RoleMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    protected Class<RoleDto> getDtoClass() {
        return RoleDto.class;
    }
}
