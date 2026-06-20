package com.Chaitanya.JWT_Authentication.service;

import com.Chaitanya.JWT_Authentication.entity.UserInfo;
import com.Chaitanya.JWT_Authentication.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService
{
    @Autowired
    userRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<UserInfo> byUsername = userRepository.findByName(username);

        if(byUsername.isPresent())
        {
           UserInfo userInfo =  byUsername.get();

           return User.withUsername(userInfo.getName())
                   .password(userInfo.getPassword())
                   .roles(userInfo.getRole())
                   .build();
        }
        throw new UsernameNotFoundException("user not found with username :"+username);
    }
}
