package com.programming.userservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.userservice.core.dto.UserDto;
import com.programming.userservice.core.mapper.UserMapper;
import com.programming.userservice.core.persistent.entity.User;
import com.programming.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService extends BaseServiceImpl<User, UserDto> {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    protected BaseRepository<User> getBaseRepository() {
        return userRepository;
    }

    @Override
    protected BaseMapper<User, UserDto> getBaseMapper() {
        return userMapper;
    }

    @Override
    protected Page<User> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<User> getListSearchResults(String keyword) {
        return null;
    }


}
