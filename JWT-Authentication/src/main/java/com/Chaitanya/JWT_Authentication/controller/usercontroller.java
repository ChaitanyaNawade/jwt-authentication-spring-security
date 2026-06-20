package com.Chaitanya.JWT_Authentication.controller;

import com.Chaitanya.JWT_Authentication.entity.UserInfo;
import com.Chaitanya.JWT_Authentication.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class usercontroller
{
    @Autowired
    userRepository userRepository;

    @GetMapping("/welcome")
    public String welcome()
    {
        return "Welcome users";
    }

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/save")
    public ResponseEntity<UserInfo> createUser(@RequestBody UserInfo userInfo)
    {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfo.setRole("USER");
        UserInfo saveduser = userRepository.save(userInfo);

        return new ResponseEntity<UserInfo>(saveduser, HttpStatus.CREATED);
    }

}
