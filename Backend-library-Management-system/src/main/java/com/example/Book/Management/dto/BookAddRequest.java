package com.example.Book.Management.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookAddRequest {

    private String bookCode;
    private String bookName;
    private String bookAuthor;
    private Long quantity;
    private String bookFloor;
    private String bookShulf;
    private Long price;
    private String bookRoomCode;
    private MultipartFile bookImage; // For receiving the image file


}

