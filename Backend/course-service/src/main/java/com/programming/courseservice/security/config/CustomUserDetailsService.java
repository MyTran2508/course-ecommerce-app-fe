package com.programming.courseservice.security.config;

import com.main.progamming.common.error.exception.DataNotFoundException;
import com.programming.courseservice.utilities.communication.UserApi;
import com.programming.courseservice.domain.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserApi userApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userApi.getByUsername(username).getData();
        if(userDto != null) {
            return new CustomUserDetails(userDto.getUsername(), userDto.getPassword(), userDto.getRoles());
        } else {
            throw new DataNotFoundException("user not found with user name :" + username);
        }
    }
}
