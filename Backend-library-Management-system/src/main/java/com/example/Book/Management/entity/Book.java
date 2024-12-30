package com.example.Book.Management.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {

    @Id
    private String bookCode;
    @Column(nullable = false)
    private String bookName;
    @Column(nullable = false)
    private String bookAuthor;
    @Column(nullable = false)
    private Long quantity;
    private Long price;
    private String bookFloor;
    private String bookShulf;
    private String bookRoomCode;

    @Lob
    @Column(name = "book_image", columnDefinition = "LONGBLOB")
    private byte[] bookImage; // Store image as binary data


}
