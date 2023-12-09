package com.programming.userservice.utilities;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Component
public class OtpGenerator {
    private static final Integer EXPIRE_MIN = 5;
    private LoadingCache<String, Integer> otpCache;

    /**
     * Constructor configuration.
     */
    public OtpGenerator()
    {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    /**
     * Method for generating OTP and put it in cache.
     *
     * @param key - cache key
     * @return cache value (generated OTP number)
     */
    public Integer generateOTP(String key)
    {
        SecureRandom secureRandom = new SecureRandom();
        int OTP = 100000 + secureRandom.nextInt(900000);
        otpCache.put(key, OTP);

        System.out.println(otpCache.getIfPresent(key));

        return OTP;
    }

    /**
     * Method for getting OTP value by key.
     *
     * @param key - target key
     * @return OTP value
     */
    public Integer getOPTByKey(String key){
        return otpCache.getIfPresent(key);
    }

    /**
     * Method for removing key from cache.
     * @param key - target key
     */
    public void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
    }
}
