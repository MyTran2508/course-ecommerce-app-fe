package com.programming.userservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataAlreadyExistException;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.userservice.domain.persistent.entity.Role;
import com.programming.userservice.domain.persistent.enumrate.RoleUser;
import com.programming.userservice.security.jwt.JwtService;
import com.programming.userservice.domain.dto.UserDto;
import com.programming.userservice.domain.mapper.UserMapper;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.repository.UserRepository;
import com.programming.userservice.util.EmailUtil;
import com.programming.userservice.util.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService extends BaseServiceImpl<User, UserDto> {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
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
        return null;
    }
    @Override
    protected List<UserDto> getListSearchResults(String keyword) {
        return null;
    }
    public DataResponse<UserDto> getUserByUsername(String username) {
        UserDto userDto = userMapper.entityToDto(userRepository.findByUserName(username));
        return ResponseMapper.toDataResponseSuccess(userDto);
    }
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }
    public DataResponse<String> register(UserDto userDto) {
        if(userRepository.findByUserName(userDto.getUsername()) == null) {
            throw new DataAlreadyExistException("User already exists");
        }
        try {
            String otp = otpUtil.generateOtp();
            emailUtil.sendOtpEmail(userDto.getEmail(), otp);
            User user = userMapper.dtoToEntity(userDto);
            Role role = new Role(RoleUser.USER.getValue());
            user.setRoles(Set.of(role));
            userRepository.save(user);
            return ResponseMapper.toDataResponseSuccess("Enroll in user successfully");
        } catch (MessagingException e) {
            throw new DataAlreadyExistException("Unable to send otp please try again");
        }
    }
}
