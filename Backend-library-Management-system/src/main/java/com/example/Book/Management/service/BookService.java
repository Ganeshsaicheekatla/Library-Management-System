package com.example.Book.Management.service;

import com.example.Book.Management.dto.BookAddRequest;
import com.example.Book.Management.dto.BookResponse;
import com.example.Book.Management.entity.Book;
import com.example.Book.Management.mapper.BookMapper;
import com.example.Book.Management.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();

    public BookAddRequest parseBookRequest(String bookRequestJson) {
        try {
            return objectMapper.readValue(bookRequestJson, BookAddRequest.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid book request format");
        }
    }

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    public Boolean addNewBook(BookAddRequest bookAddRequest) {

        Book book = BookMapper.toBook(bookAddRequest);
        Book book1 = bookRepository.save(book);

        if(book1 != null){
            return true;
        }
        return false;
    }

    public BookResponse getByBookCode(String bookCode) {
        Book book = bookRepository.findByBookCode(bookCode);
        BookAddRequest bookAddRequest= BookMapper.toBookRequest(book);
        byte[] imageBytes = book.getBookImage();

        // Create the response DTO
        BookResponse bookResponse = new BookResponse();
        bookResponse.setBookAddRequest(bookAddRequest);
        bookResponse.setImageBase64(imageBytes);
        return bookResponse;

    }

    public Boolean updateByBookCode(String bookCode, BookAddRequest bookAddRequest) {
        Book book = bookRepository.findByBookCode(bookCode);
        if(book != null ){
            if(bookAddRequest.getBookName()!=null && !bookAddRequest.getBookName().equals(""))
                book.setBookName(bookAddRequest.getBookName());
            if(bookAddRequest.getBookAuthor()!=null &&!bookAddRequest.getBookAuthor().equals(""))
                book.setBookAuthor(bookAddRequest.getBookAuthor());
            if( bookAddRequest.getBookFloor()!=null &&!bookAddRequest.getBookFloor().equals("") )
                book.setBookFloor(bookAddRequest.getBookFloor());
            if( bookAddRequest.getBookShulf()!=null &&!bookAddRequest.getBookShulf().equals("") )
                book.setBookShulf(bookAddRequest.getBookShulf());
            if(bookAddRequest.getPrice()!=null && bookAddRequest.getPrice() > -1 )
                 book.setPrice(bookAddRequest.getPrice());
            if(bookAddRequest.getQuantity()!=null && bookAddRequest.getQuantity() > -1 )
                book.setQuantity(bookAddRequest.getQuantity());
            if(bookAddRequest.getBookRoomCode()!=null && !bookAddRequest.getBookRoomCode().equals("") )
                book.setBookRoomCode(bookAddRequest.getBookRoomCode());
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    public Boolean deleteByBookCode(String bookCode) {
        Book book = bookRepository.findByBookCode(bookCode);
        bookRepository.delete(book);
        if(book != null) return true;
        return false;
    }

    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookResponse> bookAddRespons = new ArrayList<>();

        for (Book book : books){
            if(book.getQuantity() > 0) {
                BookAddRequest bookAddRequest = BookMapper.toBookRequest(book);
                byte[] imageBytes = book.getBookImage();

                // Create the response DTO
                BookResponse bookResponse = new BookResponse();
                bookResponse.setBookAddRequest(bookAddRequest);
                bookResponse.setImageBase64(imageBytes);

                bookAddRespons.add(bookResponse);
            }
        }

        return bookAddRespons;
    }
}
