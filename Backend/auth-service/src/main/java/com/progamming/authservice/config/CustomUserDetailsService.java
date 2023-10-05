package com.progamming.authservice.config;

import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.response.DataResponse;
import com.progamming.authservice.communication.UserApi;
import com.progamming.authservice.entity.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserApi userApi;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DataResponse<UserCredential> response = userApi.getUserByUserName(username);
        if(response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {
            return new CustomUserDetails(response.getData());
        } else {
            throw  new UsernameNotFoundException("user not found with name :" + username);
        }
    }
}
