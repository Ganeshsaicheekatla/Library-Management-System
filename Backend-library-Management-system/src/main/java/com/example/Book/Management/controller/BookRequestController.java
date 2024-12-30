package com.example.Book.Management.controller;

import com.example.Book.Management.dto.BookRequestDTO;
import com.example.Book.Management.service.BookRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-requests")
public class BookRequestController {

    @Autowired
    private BookRequestService bookRequestService;

    @GetMapping("/Admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookRequestDTO> getAllBookRequests() {
        return bookRequestService.getAllBookRequests();
    }

    @GetMapping("/{id}")
    public BookRequestDTO getBookRequestById(@PathVariable Long id) {
        return bookRequestService.getBookRequestById(id);
    }

    //create book request by the user
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN' , 'STUDENT')")
    public BookRequestDTO createBookRequest(@RequestBody BookRequestDTO bookRequestDTO , @RequestHeader("Authorization") String jwtToken) {
        return bookRequestService.createBookRequest(bookRequestDTO,jwtToken.substring(7));
    }

    //get all requested books by user
    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<BookRequestDTO>> getAllRequestBooksByUser(@RequestHeader("Authorization") String jwtToken){
        List<BookRequestDTO> bookRequestDTOS = bookRequestService.getAllBookRequestsUser(jwtToken.substring(7));
       return ResponseEntity.ok(bookRequestDTOS);
    }


}
