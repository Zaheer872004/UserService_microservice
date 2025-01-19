package com.zaheer.userservice.repository;

import com.zaheer.userservice.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserInfo, String> {

    Optional<UserInfo> findByUserId(String userId);
}
