package com.programming.userservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataAlreadyExistException;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.userservice.core.dto.RegisterRequest;
import com.programming.userservice.core.persistent.entity.Role;
import com.programming.userservice.core.persistent.enumrate.RoleUser;
import com.programming.userservice.security.jwt.JwtService;
import com.programming.userservice.core.dto.UserDto;
import com.programming.userservice.core.mapper.UserMapper;
import com.programming.userservice.core.persistent.entity.User;
import com.programming.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService extends BaseServiceImpl<User, UserDto> {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
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
        try {
            User user = userMapper.dtoToEntity(userDto);
            Role role = new Role(RoleUser.USER.getValue());
            user.setRoles(Set.of(role));
            userRepository.save(user);
            return ResponseMapper.toDataResponseSuccess("Enroll in user successfully");
        } catch (Exception e) {
            throw new DataAlreadyExistException("Email or usernamee already exists");
        }
    }
}
