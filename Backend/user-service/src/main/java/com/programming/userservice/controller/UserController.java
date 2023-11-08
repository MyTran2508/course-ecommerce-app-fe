package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.NotPermissionException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.userservice.domain.dto.*;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.domain.persistent.enumrate.RoleUser;
import com.programming.userservice.service.StorageService;
import com.programming.userservice.service.UserService;
import com.programming.userservice.util.annotation.ShowOpenAPI;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Tag(
        name = "User Service - User Controller",
        description = "User Controller Exposes Rest APIs for User-Service"
)
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/users/user")
public class UserController extends BaseApiImpl<User, UserDto> {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final StorageService storageService;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DataResponse<UserDto> add(@Valid UserDto objectDTO) {
        objectDTO.setPassword(passwordEncoder.encode(objectDTO.getPassword()));
        return super.add(objectDTO);
    }

    @Override
    @ShowOpenAPI
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER')")
    public DataResponse<UserDto> update(@Valid UserDto objectDTO, String id) {
        RoleDto role = new RoleDto(RoleUser.USER.getValue());
        objectDTO.setRoles(List.of(role));
        return super.update(objectDTO, id);
    }

    @ShowOpenAPI
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-admin")
    public DataResponse<UserDto> updateUserForAdmin(@Valid UserDto userDto, String id) {
        return userService.update(id, userDto);
    }

    @PostMapping("/login")
    @ShowOpenAPI
    public DataResponse<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            return userService.generateToken(loginRequest.getUsername());
        } else {
            throw new NotPermissionException("invalid access");
        }
    }

    @PostMapping("/register/send-otp")
    @ShowOpenAPI
    public DataResponse<String> sendOtpRegister(@RequestParam String email,
                                        @RequestBody @Valid UserDto userDto) {
        return userService.sendOtpRegister(email, userDto);
    }

    @PostMapping("/register/verify-save")
    @ShowOpenAPI
    public DataResponse<String> verifyAndSaveRegister(@RequestBody @Valid UserDto userDto,
                                         @RequestParam String email,
                                         @RequestParam Integer otp) {
        return userService.verifyAndSaveRegister(userDto, email, otp);
    }

    @Override
    @ShowOpenAPI
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DataResponse<UserDto> setRemoved(String id) {
        return super.setRemoved(id);
    }

    @ShowOpenAPI
    @PutMapping("/change-password/{id}")
    public DataResponse<String> changePassword(@PathVariable("id") String id,
                                               @RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(id, changePasswordRequest);
    }

    @ShowOpenAPI
    @PostMapping("/forget-password/send-otp")
    public DataResponse<String> sendOtpForgetPass(@RequestParam("email") String email) {
        return userService.sendOtpForgetPass(email);
    }

    @ShowOpenAPI
    @PostMapping("/forget-password/verify")
    public DataResponse<String> verifyAndSaveForgetPass(@RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        return userService.verifyAndSaveForgetPass(forgetPasswordRequest);
    }

    @Override
    @ShowOpenAPI
    public ListResponse<UserDto> searchByKeyword(SearchKeywordDto searchKeywordDto) {
        return super.searchByKeyword(searchKeywordDto);
    }

    @ShowOpenAPI
    @GetMapping("/photos/{username}")
    public ResponseEntity<?> getAvatar(@PathVariable("username") String username)  {
        return userService.getAvatar(username);
    }

    @ShowOpenAPI
    @PostMapping("/photos/{username}")
    public DataResponse<String> uploadAvatar(@PathVariable("username") String username,
                                          @RequestParam("image")MultipartFile file) {
        return userService.uploadAvatar(username, file);
    }
}
