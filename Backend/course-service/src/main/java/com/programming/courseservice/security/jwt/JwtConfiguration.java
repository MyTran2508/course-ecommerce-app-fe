package com.programming.courseservice.security.jwt;

import com.programming.courseservice.util.constant.SecurityConstrant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static com.google.common.net.HttpHeaders.USER_AGENT;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
public class JwtConfiguration {
    private final String secretKey = SecurityConstrant.secretKey;
    private final String tokenPrefix = SecurityConstrant.tokenPrefix;
    private final Integer tokenExpirationAfterDays = SecurityConstrant.tokenExpirationAfterDays;
    public String getAuthorizationHeader() {
        return AUTHORIZATION;
    }
    public String getUserAgent() {
        return USER_AGENT;
    }
}
