package com.programming.userservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataAlreadyExistException;
import com.main.progamming.common.error.exception.DataNotFoundException;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.userservice.domain.dto.*;
import com.programming.userservice.domain.persistent.entity.Role;
import com.programming.userservice.domain.persistent.enumrate.RoleUser;
import com.programming.userservice.security.jwt.JwtService;
import com.programming.userservice.domain.mapper.UserMapper;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.repository.UserRepository;
import com.programming.userservice.utilities.OtpUtil;
import com.programming.userservice.utilities.communication.CourseAPI;
import com.programming.userservice.utilities.constant.TypeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService extends BaseServiceImpl<User, UserDto> {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final OtpUtil otpUtil;

    private final PasswordEncoder passwordEncoder;

    private final StorageService storageService;

    private final OrderService orderService;

    private final CourseAPI courseAPI;

    @Override
    protected BaseRepository<User> getBaseRepository() {
        return userRepository;
    }

    @Override
    protected BaseMapper<User, UserDto> getBaseMapper() {
        return userMapper;
    }

    @Override
    protected Page<UserDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        String name = searchKeywordDto.getKeyword().get(0) == null ? null : searchKeywordDto.getKeyword().get(0).trim();

        return userRepository.searchUser(name, pageable)
                .map(user -> userMapper.entityToDto(user));
    }

    @Override
    protected List<UserDto> getListSearchResults(String keyword) {
        return null;
    }

    public DataResponse<UserDto> getUserByUsername(String username) {
        UserDto userDto = userMapper.entityToDto(userRepository.findByUserName(username));
        return ResponseMapper.toDataResponseSuccess(userDto);
    }

    public DataResponse<String> generateToken(String username) {
        String accessToken = jwtService.generateToken(username);
        return ResponseMapper.toDataResponseSuccess(accessToken);
    }

    public DataResponse<String> verifyAndSaveRegister(UserDto userDto, String email, Integer otp) {
        if (!otpUtil.validateOTP(email, otp)) {
            return ResponseMapper.toDataResponse("Otp is not correct", StatusCode.NOT_IMPLEMENTED, StatusMessage.NOT_IMPLEMENTED);
        }

        try {
            User user = userMapper.dtoToEntity(userDto);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            Role role = new Role(RoleUser.USER.getValue());
            user.setRoles(List.of(role));

            userRepository.save(user);
            return ResponseMapper.toDataResponseSuccess("Enroll in user successfully");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DataAlreadyExistException("User or email already exists");
        }
    }

    public DataResponse<String> sendOtpRegister(String email, UserDto userDto) {
        if(userRepository.findByUserName(userDto.getUsername()) != null) {
            throw new DataAlreadyExistException("User already exists");
        }

        if(otpUtil.generateOtp(email, TypeMessage.REGISTER)) {
            return ResponseMapper.toDataResponseSuccess("Send otp to " + email + " successfully");

        } else {
            String messageError = "An error occurred while generating and sending OTP";
            return ResponseMapper.toDataResponse(messageError, StatusCode.NOT_IMPLEMENTED, StatusMessage.NOT_IMPLEMENTED);

        }
    }

    public DataResponse<String> changePassword(String id, ChangePasswordRequest changePasswordRequest) {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {
            throw new DataNotFoundException(id + " doesn't exist in DB");
        }

        if(passwordEncoder.matches(changePasswordRequest.getOldPassword(), userOptional.get().getPassword())) {
            String newPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
            userRepository.changePassword(id, newPassword);
        } else {
            throw new DataNotFoundException(id + " password not correct");
        }

        return ResponseMapper.toDataResponseSuccess("Update password successfully");
    }

    public DataResponse<String> sendOtpForgetPass(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()) {
            throw new DataNotFoundException("Email is not valid");
        }

        if(otpUtil.generateOtp(email, TypeMessage.FORGET_PASSWORD)) {
            return ResponseMapper.toDataResponseSuccess("Send otp to " + email + " successfully");

        } else {
            String messageError = "An error occurred while generating and sending OTP";
            return ResponseMapper.toDataResponse(messageError, StatusCode.NOT_IMPLEMENTED, StatusMessage.NOT_IMPLEMENTED);
        }
    }

    public DataResponse<String> verifyAndSaveForgetPass(ForgetPasswordRequest forgetPasswordRequest) {
        if (!otpUtil.validateOTP(forgetPasswordRequest.getEmail(), forgetPasswordRequest.getOtp())) {
            return ResponseMapper.toDataResponse("Otp is not correct", StatusCode.NOT_IMPLEMENTED, StatusMessage.NOT_IMPLEMENTED);
        }

        Optional<User> userOptional = userRepository.findByEmail(forgetPasswordRequest.getEmail());
        if(userOptional.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(forgetPasswordRequest.getNewPassword()));

        userRepository.save(user);
        return ResponseMapper.toDataResponseSuccess("Update password successfully");
    }

    @Override
    public DataResponse<UserDto> update(String id, UserDto dto) {
        return super.update(id, dto);
    }

    public ResponseEntity<AvatarDto> getAvatar(String username) {
        User user = userRepository.findByUserName(username);
        if(user == null) {
            throw new DataNotFoundException("User doesn't exists");
        }

        byte[] avatar = user.getAvatar();
        if(avatar == null) {
            throw new ResourceNotFoundException("Avatar doesn't exists");
        }

        String imageBase64 = Base64.getEncoder().encodeToString(avatar);

        AvatarDto avatarDto = new AvatarDto();
        avatarDto.setRawAvatar(imageBase64);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(avatarDto);
    }

    public DataResponse<String> uploadAvatar(String username, MultipartFile file) {

        User user = userRepository.findByUserName(username);
        if(user == null) {
            throw new DataNotFoundException(username + " doesn't exists in db");
        }

        if(!storageService.isFileImage(file)) {
            return ResponseMapper.toDataResponse("File is invalid", StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
        }

        try {
            user.setAvatar(file.getBytes());
        } catch (IOException e) {
            return ResponseMapper.toDataResponse("File is invalid", StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
        }

        userRepository.save(user);
        return ResponseMapper.toDataResponseSuccess("Upload avatar succcessfully");
    }

    public DataResponse<UserDto> updateAdminUser(UserDto userDto, String id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()) {

            User savedUser = optionalUser.get();
            savedUser.setUsername(userDto.getUsername());
            savedUser.setEmail(userDto.getEmail());

            List<Role> roles = new ArrayList<>();
            roles.add(new Role(userDto.getRoles().get(0).getId()));

            savedUser.setRoles(roles);

            User updatedUser = userRepository.save(savedUser);
            return ResponseMapper.toDataResponseSuccess(userMapper.entityToDto(updatedUser));

        } else {
            throw new ResourceNotFoundException("Data doesn't exists");
        }
    }

    public Integer getTotalRegisteredUsers(int targetYear, Integer targetMonth) {

        return userRepository.countByYearAnhMonth(targetYear, targetMonth);
    }

    public DataResponse<Map<String, Integer>> getStatisticsByYearAndMonth(StatisticsRequest statisticsRequest) {

        Map<String, Double> statisticsMap = new HashMap<>();

        statisticsMap.put("totalRegisteredUser", Double.valueOf(getTotalRegisteredUsers(statisticsRequest.getTargetYear(), statisticsRequest.getTargetMonth())));
        Double totalRevenue = orderService.getTotalRenevueByYearAndMonth(statisticsRequest.getTargetYear(), statisticsRequest.getTargetMonth());

        statisticsMap.put("totalRevenue", totalRevenue == null ? 0.0 : totalRevenue);

        DataResponse<Integer> response = courseAPI.getTotalRegisteredCourseByYearAndMonth(statisticsRequest);
        statisticsMap.put("totalRegisteredCourse", Double.valueOf(response.getData()));

        response = courseAPI.getTotalApprovedCourseByYearAndMonth(statisticsRequest);
        statisticsMap.put("totalApprovedCourse", Double.valueOf(response.getData()));

        return ResponseMapper.toDataResponseSuccess(statisticsMap);
    }
}
