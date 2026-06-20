package com.Chaitanya.JWT_Authentication.controller;

import com.Chaitanya.JWT_Authentication.service.CustomUserDetailService;
import com.Chaitanya.JWT_Authentication.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController
{

    private final AuthenticationManager authenticationManager;
    private  final JwtService jwtService;
    private final  UserDetailsService  userDetailService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService)
    {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailService = userDetailsService;
    }

    @PostMapping("/login")
    public  String login(@RequestBody AuthRequest authRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        if(authentication.isAuthenticated())
        {
            return jwtService.generateToken(authRequest.getUsername());
        }
        else
        {
            throw  new RuntimeException(("invalid access"));
        }
    }
}
