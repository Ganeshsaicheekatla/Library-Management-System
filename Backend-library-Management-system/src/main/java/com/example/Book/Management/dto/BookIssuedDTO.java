package com.example.Book.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookIssuedDTO {

    private String bookName;
    private Long bookRequestId;
    private String bookAuthor;
    private Date issuedDate;
    private Date dueDate;
    private Date returnDate;
    private Long fine;
}
