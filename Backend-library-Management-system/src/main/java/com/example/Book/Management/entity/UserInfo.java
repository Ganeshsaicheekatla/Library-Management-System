package com.example.Book.Management.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userInfoId;
    @Column(unique = true, nullable = false)
    private String institutionId;


    private String education;

    private Long yearOfGraduation;

    @Temporal(TemporalType.DATE)
    private Date dob;

    private Long PhoneNO;

    private String Address;

    private String institutionName;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private User user;

}
