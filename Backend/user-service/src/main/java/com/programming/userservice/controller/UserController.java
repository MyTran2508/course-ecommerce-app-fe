package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.userservice.communication.OpenFeign.CategoryApi;
import com.programming.userservice.core.dto.CategoryDto;
import com.programming.userservice.core.dto.UserDto;
import com.programming.userservice.core.persistent.entity.User;
import com.programming.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController extends BaseApiImpl<User, UserDto> {
    private final UserService userService;
    private final CategoryApi categoryApi;

    @Override
    protected BaseService<User, UserDto> getBaseService() {
        return userService;
    }

    @Override
    public ListResponse<UserDto> getAll() {
        ListResponse<UserDto> listResponse = super.getAll();
        List<UserDto> userDtos = listResponse.getData();

        // Call API get all category from course service
        CategoryDto categoryDto = categoryApi.getCategoryById("23444987-f630-47f7-bb0f-f4125c47431c").getData();
        userDtos.forEach(userDto -> userDto.setCategoryDto(categoryDto));
        return listResponse;
    }
}
