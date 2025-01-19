package com.zaheer.userservice.service;


import com.zaheer.userservice.entities.UserInfo;
import com.zaheer.userservice.entities.UserInfoDto;
import com.zaheer.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
public class UserService {




    @Autowired
    private UserRepository userRepository;

    /*
    public Optional<UserInfo> findByUserId(UserInfoDto userInfo) {
        return userRepository.findByUserId(userInfo.getUserId());
    }

    public void createUser(UserInfoDto userInfoDto) {
       userRepository.save(userInfoDto.transformToUserInfo());
    }

    public void updateUser(UserInfo existingUser, UserInfoDto userInfoDto) {
        existingUser.setFirstName(userInfoDto.getFirstName());
        existingUser.setLastName(userInfoDto.getLastName());
        existingUser.setEmail(userInfoDto.getEmail());
        existingUser.setPhoneNumber(userInfoDto.getPhoneNumber());
        existingUser.setProfilePic(userInfoDto.getProfilePic());
        userRepository.save(existingUser);
    }
    */

    public UserInfoDto createOrUpdateUser(UserInfoDto userInfoDto) {

        // updating an user details
        UnaryOperator<UserInfo> updatingUser = user -> {
            user.setFirstName(userInfoDto.getFirstName());
            user.setLastName(userInfoDto.getLastName());
            user.setEmail(userInfoDto.getEmail());
            user.setPhoneNumber(userInfoDto.getPhoneNumber());
            user.setProfilePic(userInfoDto.getProfilePic());

            return userRepository.save(user);
            // UserInfo userUpdate = userRepository.save(user);
            // return userRepository.save(userUpdate);
        };

        // creating a new user
        Supplier<UserInfo> createUser = () -> {
            return userRepository.save(userInfoDto.transformToUserInfo());
        };

        UserInfo user = userRepository.findByUserId(userInfoDto.getUserId())
                .map(updatingUser)
                .orElseGet(createUser);

        return new UserInfoDto(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getProfilePic()
        );

    }

    public UserInfoDto getUser(UserInfoDto userInfoDto) throws Exception{
        Optional<UserInfo> userInfoDtoOpt = userRepository.findByUserId(userInfoDto.getUserId());
        if(userInfoDtoOpt.isEmpty()){
            throw new Exception("User not found");
        }
        UserInfo userInfo = userInfoDtoOpt.get();
        return new UserInfoDto(
                userInfo.getUserId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhoneNumber(),
                userInfo.getEmail(),
                userInfo.getProfilePic()
        );
    }




}
