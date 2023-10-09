package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.error.exception.NotPermissionException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.userservice.communication.OpenFeign.CategoryApi;
import com.programming.userservice.domain.dto.LoginRequest;
import com.programming.userservice.domain.dto.UserDto;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.service.UserService;
import com.programming.userservice.util.annotation.ShowOpenAPI;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "User Service - User Controller",
        description = "User Controller Exposes Rest APIs for User-Service"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/user")
public class UserController extends BaseApiImpl<User, UserDto> {
    private final UserService userService;
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
    public DataResponse<UserDto> add(@Valid UserDto objectDTO) {
        objectDTO.setPassword(passwordEncoder.encode(objectDTO.getPassword()));
        return super.add(objectDTO);
    }

    @Override
    public DataResponse<UserDto> update(@Valid UserDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            return userService.generateToken(loginRequest.getUsername());
        } else {
            throw new NotPermissionException("invalid access");
        }
    }

    @PostMapping("/register")
    public DataResponse<String> register(@RequestBody @Valid UserDto userDto) {
        return userService.register(userDto);
    }

    @Override
    public DataResponse<UserDto> setRemoved(String id) {
        return super.setRemoved(id);
    }
}
