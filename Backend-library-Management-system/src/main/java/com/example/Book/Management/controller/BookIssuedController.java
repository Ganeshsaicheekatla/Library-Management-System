package com.example.Book.Management.controller;

import com.example.Book.Management.dto.BookIssuedDTO;
import com.example.Book.Management.service.BookIssuedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-issued")
public class BookIssuedController {

    @Autowired
    private BookIssuedService bookIssuedService;

    @GetMapping
    public List<BookIssuedDTO> getAllBookIssuedRecords() {
        return bookIssuedService.getAllBookIssuedRecords();
    }

    @GetMapping("/{id}")
    public BookIssuedDTO getBookIssuedById(@PathVariable Long id) {
        return bookIssuedService.getBookIssuedById(id);
    }

    @PostMapping
    public ResponseEntity<Boolean> createBookIssuedRecord(@RequestBody BookIssuedDTO bookIssuedDTO ) {

        Boolean result= bookIssuedService.createBookIssuedRecord(bookIssuedDTO );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/returnedBooks")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN' , 'STUDENT')")
    public ResponseEntity<List<BookIssuedDTO>> getReturnedBooksByUser(@RequestHeader("Authorization") String jwtToken) {
        List<BookIssuedDTO> issuedBooks = bookIssuedService.getReturnedBooksByUser(jwtToken.substring(7));
        return ResponseEntity.ok(issuedBooks);
    }

    @GetMapping("/issuedBooks")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN' , 'STUDENT')")
    public ResponseEntity<List<BookIssuedDTO>> getIssusedBooksByUser(@RequestHeader("Authorization") String jwtToken) {
        List<BookIssuedDTO> issuedBooks = bookIssuedService.getIssuedBooksByUser(jwtToken.substring(7));
        return ResponseEntity.ok(issuedBooks);
    }

}
