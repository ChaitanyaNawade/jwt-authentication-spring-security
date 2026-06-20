package com.Chaitanya.JWT_Authentication.repository;

import com.Chaitanya.JWT_Authentication.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepository extends JpaRepository<UserInfo,Integer>
{
    Optional<UserInfo> findByName(String name);
}