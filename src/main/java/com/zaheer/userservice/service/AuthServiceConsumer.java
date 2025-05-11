package com.zaheer.userservice.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaheer.userservice.entities.UserInfo;
import com.zaheer.userservice.entities.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthServiceConsumer {

    @Autowired
    private UserService userService;

    @Autowired
//    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(UserInfoDto eventData) {
        try{

            // Validate email and phone number
            validateUserInfo(eventData);

            // Todo: Make it transactional, to handle idempotency and validate email, phoneNumber etc
            userService.createOrUpdateUser(eventData);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("AuthServiceConsumer: Exception is thrown while consuming kafka event");
        }
    }






    private void validateUserInfo(UserInfoDto userInfoDto) throws Exception {
        if (userInfoDto.getEmail() == null || !userInfoDto.getEmail().contains("@")) {
            throw new Exception("Invalid email address");
        }

        if (userInfoDto.getPhoneNumber() == null || !isValidPhoneNumber(userInfoDto.getPhoneNumber())) {
            throw new Exception("Invalid phone number");
        }
    }

    private boolean isValidPhoneNumber(Long phoneNumber) {
        // Convert the phone number to a string for pattern matching
        String phoneNumberStr = phoneNumber.toString();
        // Define the pattern for a valid phone number (10 digits)
        Pattern pattern = Pattern.compile("\\d{10}");
        Matcher matcher = pattern.matcher(phoneNumberStr);
        return matcher.matches();
    }





}
