package com.programming.userservice.security.config;

import com.main.progamming.common.error.exception.DataNotFoundException;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user != null) {
            return new CustomUserDetails(user);
        } else {
            throw new DataNotFoundException("user not found with user name :" + username);
        }
    }
}
