package com.programming.userservice.util;

import com.programming.userservice.repository.UserRepository;
import com.programming.userservice.util.constant.TypeMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public boolean generateOtp(String email, TypeMessage typeMessage) {

        // Create a message to return the status
        String message;
        try {
            // generate otp
            Integer otpValue = otpGenerator.generateOTP(email);

            // send generated e-mail
            return emailUtil.sendSimpleEmail(email, otpValue, typeMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method for validating provided OTP
     *
     * @param key       - provided key
     * @param otpNumber - provided OTP number
     * @return String message
     */
    @Transactional
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
