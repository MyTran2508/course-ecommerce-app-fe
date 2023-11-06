package com.programming.userservice.util;

import com.programming.userservice.util.constant.TypeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender emailSender;
    public static final String FORMATTED_TIME = DateUtil.getTimeExpTime5Minutes();

    @Value("${spring.mail.username}")
    private String sender;

    public String getMsgBody(Integer OTP, TypeMessage typeMessage) {
        return typeMessage == TypeMessage.REGISTER ? MessageFormat.format(
                "We have received a request to register an account.\nYour OTP is: {0}\n\nPlease enter this code on the password reset page to proceed. This code will be valid until {1}\nIf you did not request a password reset, please ignore this message and take appropriate measures to secure your account.\n\nThank you for using our service.\n\nBest regards\nOceanTelecom"
                , OTP.toString() , FORMATTED_TIME) : MessageFormat.format(
                "We have received a request to forget the password for your account.\nYour OTP is: {0}\n\nPlease enter this code on the password reset page to proceed. This code will be valid until {1}\nIf you did not request a password reset, please ignore this message and take appropriate measures to secure your account.\n\nThank you for using our service.\n\nBest regards\nOceanTelecom"
                , OTP.toString() , FORMATTED_TIME);
    }

    public boolean sendSimpleEmail(String email, Integer OTP, TypeMessage typeMessage)
    {
        /* Create email message*/
        String message = getMsgBody(OTP, typeMessage);

        /* Create simpleMail*/
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(email);
        mailMessage.setSubject(typeMessage.getValue());
        mailMessage.setText(message);
        try
        {
            emailSender.send(mailMessage);
            return true;
        }
        catch (Exception e) {
            System.out.print("Error:  "+ e);
            return false;
        }
    }
}
