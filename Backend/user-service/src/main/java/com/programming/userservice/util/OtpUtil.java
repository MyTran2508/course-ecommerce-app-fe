package com.programming.userservice.util;

import com.programming.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OtpUtil {
    private final OtpGenerator otpGenerator;
    private final EmailUtil emailUtil;
    private final UserRepository userRepository;


    public OtpUtil(OtpGenerator otpGenerator, EmailUtil emailUtil, UserRepository userRepository) {
        this.otpGenerator = otpGenerator;
        this.emailUtil = emailUtil;
        this.userRepository = userRepository;
    }


    public boolean generateOtp(String email) {

        // Create a message to return the status
        String message;

        try {
            // generate otp
            Integer otpValue = otpGenerator.generateOTP(email);

            // send generated e-mail
            return emailUtil.sendSimpleMessage(email, otpValue);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Method for validating provided OTP
     *
     * @param key       - provided key
     * @param otpNumber - provided OTP number
     * @return String message
     */
    public boolean validateOTP(String key, Integer otpNumber) {
        // Create a message to return the status
        boolean isValid = false;

        try {
            // get OTP from cache
            Integer cacheOTP = otpGenerator.getOPTByKey(key);
            if (cacheOTP != null && cacheOTP.equals(otpNumber)) {
                otpGenerator.clearOTPFromCache(key);
                isValid = true;
            }
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }
}
