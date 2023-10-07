package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.error.exception.NotPermissionException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.userservice.communication.OpenFeign.CategoryApi;
import com.programming.userservice.core.dto.CategoryDto;
import com.programming.userservice.core.dto.LoginRequest;
import com.programming.userservice.core.dto.RegisterRequest;
import com.programming.userservice.core.dto.UserDto;
import com.programming.userservice.core.persistent.entity.User;
import com.programming.userservice.service.UserService;
import com.programming.userservice.util.annotation.ShowOpenAPI;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "User Service - User Controller",
        description = "User Controller Exposes Rest APIs for User-Service"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/user")
public class UserController extends BaseApiImpl<User, UserDto> {
    private final UserService userService;
    private final CategoryApi categoryApi;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    protected BaseService<User, UserDto> getBaseService() {
        return userService;
    }

    @Override
    @ShowOpenAPI
    public ListResponse<UserDto> getAll() {
        return super.getAll();
    }

    @GetMapping("/get-by-username/{username}")
    @ShowOpenAPI
    public DataResponse<UserDto> getByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<UserDto> add(UserDto objectDTO) {
        objectDTO.setPassword(passwordEncoder.encode(objectDTO.getPassword()));
        return super.add(objectDTO);
    }

    @Override
    public DataResponse<UserDto> update(UserDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if(authentication.isAuthenticated()) {
            return userService.generateToken(loginRequest.getUsername());
        } else {
            throw new NotPermissionException("invalid access");
        }
    }

    @PostMapping("/register")
    public DataResponse<String> register(@RequestBody UserDto userDto) {
        return userService.register(userDto);
    }
}
