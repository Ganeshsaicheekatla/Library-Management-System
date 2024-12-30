package com.example.Book.Management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExist extends RuntimeException{

    public UserAlreadyExist(String msg){
        super(msg);
    }
}
