package com.example.Book.Management.mapper;

import com.example.Book.Management.dto.BookAddRequest;
import com.example.Book.Management.entity.Book;
import org.modelmapper.ModelMapper;

public class BookMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static BookAddRequest toBookRequest(Book book) {
        return modelMapper.map(book, BookAddRequest.class);
    }

    public static Book toBook(BookAddRequest bookAddRequest) {
        Book book = new Book();
        book.setBookCode(bookAddRequest.getBookCode());
        book.setBookName(bookAddRequest.getBookName());
        book.setBookAuthor(bookAddRequest.getBookAuthor());
        book.setQuantity(bookAddRequest.getQuantity());
        book.setPrice(bookAddRequest.getPrice());
        book.setBookFloor(bookAddRequest.getBookFloor());
        book.setBookShulf(bookAddRequest.getBookShulf());
        book.setBookRoomCode(bookAddRequest.getBookRoomCode());
        try {
            // Convert MultipartFile to byte[] for storing in the entity
            book.setBookImage(bookAddRequest.getBookImage().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }
}
