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
public class BookRequestDTO {

    private String bookName;
    private String bookAuthor;
    private Date requestDate;
    private String bookCode;
    private String status;

}
