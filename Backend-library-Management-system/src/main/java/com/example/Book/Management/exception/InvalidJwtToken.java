package com.example.Book.Management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidJwtToken extends RuntimeException{
    public  InvalidJwtToken(String msg){
        super(msg);
    }
}
