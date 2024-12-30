package com.example.Book.Management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long userId;
    private String email;
    private String name;
    private String password;
    private String role;
    private String institutionId;

    private String education;

    private Date dob;

    private Long yearofgraduation;

    private Long phoneNo;

    private String address;

    private String institution;

}
