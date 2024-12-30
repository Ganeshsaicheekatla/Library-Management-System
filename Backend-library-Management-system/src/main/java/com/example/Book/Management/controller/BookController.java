package com.example.Book.Management.controller;

import com.example.Book.Management.dto.BookResponse;
import com.example.Book.Management.service.BookService;
import com.example.Book.Management.dto.BookAddRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
@PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Handling multipart form data request
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> addNewBookToLibrary(
            @RequestPart("book") String bookRequestJson,
            @RequestPart("file") Optional<MultipartFile> file) {

        BookAddRequest bookAddRequest = bookService.parseBookRequest(bookRequestJson);
        file.ifPresent(bookAddRequest::setBookImage);

        Boolean result = bookService.addNewBook(bookAddRequest);
        return ResponseEntity.ok(result);
    }

    // Get book by bookCode
    @GetMapping("/BookCode/{bookCode}")
    public ResponseEntity<BookResponse> getBookByBookCode(@PathVariable String bookCode) {
        BookResponse bookResponse = bookService.getByBookCode(bookCode);
        return ResponseEntity.ok(bookResponse);
    }

    // Update book by bookCode
    @PutMapping("/updateBook/{bookCode}")
    public ResponseEntity<Boolean> updateBookByBookCode(
            @PathVariable String bookCode,
            @RequestPart("book") String bookRequestJson) {

        BookAddRequest bookAddRequest = bookService.parseBookRequest(bookRequestJson);
        Boolean result = bookService.updateByBookCode(bookCode, bookAddRequest);
        return ResponseEntity.ok(result);
    }

    // Delete book by bookCode
    @DeleteMapping("/delete/{bookCode}")
    public ResponseEntity<Boolean> deleteBookByBookCode(@PathVariable String bookCode) {
        Boolean result = bookService.deleteByBookCode(bookCode);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN' , 'STUDENT')")
    public ResponseEntity<List<BookResponse>> getAllBooks(){
        List<BookResponse> bookRequests= bookService.getAllBooks();
        return ResponseEntity.ok(bookRequests);
    }
}
