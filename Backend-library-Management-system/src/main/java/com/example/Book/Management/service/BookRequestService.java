package com.example.Book.Management.service;

import com.example.Book.Management.dto.BookRequestDTO;
import com.example.Book.Management.entity.BookRequest;
import com.example.Book.Management.entity.RequestStatus;
import com.example.Book.Management.entity.User;
import com.example.Book.Management.entity.Book;
import com.example.Book.Management.jwt.JwtAuthenticationHelper;
import com.example.Book.Management.repository.BookRepository;
import com.example.Book.Management.repository.BookRequestRepository;
import com.example.Book.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookRequestService {

    @Autowired
    private BookRequestRepository bookRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JwtAuthenticationHelper jwtAuthenticationHelper;



    public List<BookRequestDTO> getAllBookRequests() {
        return bookRequestRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BookRequestDTO getBookRequestById(Long id) {
        BookRequest bookRequest = bookRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookRequest not found"));
        return toDTO(bookRequest);
    }

    @Transactional
    public BookRequestDTO createBookRequest(BookRequestDTO bookRequestDTO, String jwtToken) {

        String email = jwtAuthenticationHelper.getUsernameFromToken(jwtToken);
        User user = (User) userRepository.findByEmail(email).get();
        Book book = bookRepository.findByBookCode(bookRequestDTO.getBookCode());

        BookRequest bookRequest = BookRequest.builder()
                .requestDate(bookRequestDTO.getRequestDate())
                .user(user)
                .book(book)
                .status(RequestStatus.valueOf(bookRequestDTO.getStatus().toUpperCase()))
                .build();

        return toDTO(bookRequestRepository.save(bookRequest));
    }

    private BookRequestDTO toDTO(BookRequest bookRequest) {
        return BookRequestDTO.builder()
                .bookAuthor(bookRequest.getBook().getBookAuthor())
                .bookName(bookRequest.getBook().getBookName())
                .requestDate(bookRequest.getRequestDate())
                .bookCode(bookRequest.getBook().getBookCode())
                .status(String.valueOf(bookRequest.getStatus()))
                .build();
    }


    public List<BookRequestDTO> getAllBookRequestsUser(String substring) {
        String email = jwtAuthenticationHelper.getUsernameFromToken(substring);
        User user = (User) userRepository.findByEmail(email).get();

        List<BookRequest> bookRequests = bookRequestRepository.findAllByUserId(user.getId());

        List<BookRequestDTO> bookRequestDTOS = new ArrayList<>();
        for (BookRequest bookRequest : bookRequests){
            bookRequestDTOS.add(toDTO(bookRequest));
        }

        return bookRequestDTOS;
    }
}
