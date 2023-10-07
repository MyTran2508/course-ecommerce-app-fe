package com.programming.userservice.util.constant;

import java.util.UUID;

public class SecurityConstrant {
    public static final String secretKey = UUID.randomUUID().toString();
    public static final String tokenPrefix = "Bearer ";
    public static final int tokenExpirationAfterDays = 10;
}
