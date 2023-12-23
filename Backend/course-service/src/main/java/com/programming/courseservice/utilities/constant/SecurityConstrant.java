package com.programming.courseservice.utilities.constant;

import java.util.UUID;

public class SecurityConstrant {
    public static final String secretKey = UUID.randomUUID().toString();
    public static final String tokenPrefix = "Bearer ";
    public static final int tokenExpirationAfterDays = 1 * 24 * 60 * 60 * 1000; // one day
}
