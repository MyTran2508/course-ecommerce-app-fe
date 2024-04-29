package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.NotPermissionException;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.SystemUtil;
import com.programming.userservice.domain.dto.*;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.domain.persistent.entity.UserLog;
import com.programming.userservice.domain.persistent.enumrate.ActionName;
import com.programming.userservice.domain.persistent.enumrate.ActionObject;
import com.programming.userservice.domain.persistent.enumrate.RoleUser;
import com.programming.userservice.repository.UserRepository;
import com.programming.userservice.service.UserLogService;
import com.programming.userservice.service.UserService;
import com.programming.userservice.utilities.annotation.ShowOpenAPI;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserLogService userLogService;

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
    public DataResponse<String> add(@Valid UserDto objectDTO) {
        objectDTO.setPassword(passwordEncoder.encode(objectDTO.getPassword()));

        DataResponse<String> response = super.add(objectDTO);

        String stResult = response.getData();
        String userId = stResult.split(": ")[1].trim();
        User entity = userRepository.findById(userId).orElse(null);

        // Add log
        UserLog userLog = UserLog.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey(userId)
                .actionObject(ActionObject.USER)
                .actionName(ActionName.CREATE)
                .description(userLogService.writePersistLog(User.class, entity, true, 0))
                .build();
        userLogService.addLog(userLog);

        return response;

    }

    @Override
    @ShowOpenAPI
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER')")
    public DataResponse<UserDto> update(@Valid UserDto objectDTO, String id) {

//        RoleDto role = new RoleDto(RoleUser.USER.getValue());
//        objectDTO.setRoles(List.of(role));

        return super.update(objectDTO, id);
    }

    @ShowOpenAPI
    @PutMapping("/update-admin/{id}")
    public DataResponse<UserDto> updateAdminUser(@Valid @RequestBody UserDto userDto,
                                                    @PathVariable("id") String id) {

        return userService.updateAdminUser(userDto, id);
    }

    @PostMapping("/login")
    @ShowOpenAPI
    public DataResponse<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if(authentication.isAuthenticated()) {

            // Add log
            UserLog userLog = UserLog.builder()
                    .actionObject(ActionObject.USER)
                    .actionName(ActionName.LOGIN)
                    .ip(SystemUtil.getUserIP())
                    .userName(loginRequest.getUsername())
                    .description(userLogService.writeLoginLog())
                    .build();
            userLogService.addLog(userLog);

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

        DataResponse<String> response = userService.verifyAndSaveRegister(userDto, email, otp);

        if (response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {
            //  Add log
            UserLog userLog = UserLog.builder()
                    .actionObject(ActionObject.USER)
                    .actionName(ActionName.SIGNUP)
                    .ip(SystemUtil.getUserIP())
                    .userName(userDto.getUsername())
                    .description(userLogService.writeSignUpLog())
                    .build();
            userLogService.addLog(userLog);
        }

        return response;
    }

    @Override
    @ShowOpenAPI
    public DataResponse<UserDto> setRemoved(String id) {
        DataResponse<UserDto> response = super.setRemoved(id);

        if (response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {

            // Add log
            UserLog userLog = UserLog.builder()
                    .userName(SystemUtil.getCurrentUsername())
                    .ip(SystemUtil.getUserIP())
                    .actionKey(id)
                    .actionObject(ActionObject.USER)
                    .actionName(ActionName.REMOVED)
                    .description(userLogService.writeRemoveLog(true))
                    .build();
            userLogService.addLog(userLog);
        }

        return response;
    }

    @ShowOpenAPI
    @PutMapping("/change-password/{id}")
    public DataResponse<String> changePassword(@PathVariable("id") String id,
                                               @RequestBody ChangePasswordRequest changePasswordRequest) {

        DataResponse<String> response = userService.changePassword(id, changePasswordRequest);
        if (response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {

            // Add log
            UserLog userLog = UserLog.builder()
                    .userName(SystemUtil.getCurrentUsername())
                    .ip(SystemUtil.getUserIP())
                    .actionKey(id)
                    .actionObject(ActionObject.USER)
                    .actionName(ActionName.RESET_PASSWORD)
                    .description(userLogService.writeResetPassword(true))
                    .build();
            userLogService.addLog(userLog);
        }

        return response;
    }

    @ShowOpenAPI
    @PostMapping("/forget-password/send-otp")
    public DataResponse<String> sendOtpForgetPass(@RequestParam("email") String email) {
        return userService.sendOtpForgetPass(email);
    }

    @ShowOpenAPI
    @PostMapping("/forget-password/verify")
    public DataResponse<String> verifyAndSaveForgetPass(@RequestBody ForgetPasswordRequest forgetPasswordRequest) {

        DataResponse<String> response = userService.verifyAndSaveForgetPass(forgetPasswordRequest);
        if (response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {
            // Add log
            UserLog userLog = UserLog.builder()
                    .userName(SystemUtil.getCurrentUsername())
                    .ip(SystemUtil.getUserIP())
                    .actionObject(ActionObject.USER)
                    .actionName(ActionName.FORGET_PASSWORD)
                    .description(userLogService.writeForgetPassword(true))
                    .build();
            userLogService.addLog(userLog);
        }

        return response;
    }

    @Override
    @ShowOpenAPI
    public ListResponse<UserDto> searchByKeyword(SearchKeywordDto searchKeywordDto) {

        /*
         * List<String> keyword:
         * index 1: key of username or email
         */
        return super.searchByKeyword(searchKeywordDto);
    }

    @ShowOpenAPI
    @GetMapping("/photos/{username}")
    public ResponseEntity<AvatarDto> getAvatar(@PathVariable("username") String username)  {

        return userService.getAvatar(username);
    }

    @ShowOpenAPI
    @PostMapping("/photos/{username}")
    public DataResponse<String> uploadAvatar(@PathVariable("username") String username,
                                            @RequestParam("image")MultipartFile file) {

        return userService.uploadAvatar(username, file);
    }

    @ShowOpenAPI
    @PostMapping("/get-statistics")
    public DataResponse<Map<String, Integer>> getStatisticsByYearAndMonth(@Valid @RequestBody StatisticsRequest statisticsRequest) {
        return userService.getStatisticsByYearAndMonth(statisticsRequest);
    }
}
