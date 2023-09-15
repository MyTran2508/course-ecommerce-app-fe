package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.userservice.communication.OpenFeign.CategoryApi;
import com.programming.userservice.core.dto.CategoryDto;
import com.programming.userservice.core.dto.UserDto;
import com.programming.userservice.core.persistent.entity.User;
import com.programming.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController extends BaseApiImpl<User, UserDto> {
    private final UserService userService;
    private final CategoryApi categoryApi;
    @Override
    protected BaseService<User, UserDto> getBaseService() {
        return userService;
    }

    @Override
    public ListResponse<UserDto> getAll() {
        return super.getAll();
    }
}
