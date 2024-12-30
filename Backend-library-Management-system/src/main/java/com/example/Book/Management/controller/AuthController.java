package com.example.Book.Management.controller;

import com.example.Book.Management.dto.JwtRequest;
import com.example.Book.Management.dto.JwtResponse;
import com.example.Book.Management.dto.UserDto;
import com.example.Book.Management.service.AuthService;
import com.example.Book.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService ;

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
        return new ResponseEntity<>( authService.login(jwtRequest) , HttpStatus.OK);
    }



}
