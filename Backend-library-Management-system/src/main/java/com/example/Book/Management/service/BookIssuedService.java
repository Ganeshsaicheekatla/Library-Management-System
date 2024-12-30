package com.example.Book.Management.service;

import com.example.Book.Management.dto.BookIssuedDTO;
import com.example.Book.Management.entity.BookIssued;
import com.example.Book.Management.entity.BookRequest;
import com.example.Book.Management.entity.User;
import com.example.Book.Management.jwt.JwtAuthenticationHelper;
import com.example.Book.Management.repository.BookIssuedRepository;
import com.example.Book.Management.repository.BookRequestRepository;
import com.example.Book.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookIssuedService {

    @Autowired
    private BookIssuedRepository bookIssuedRepository;

    @Autowired
    private BookRequestRepository bookRequestRepository;

    @Autowired
    private  JwtAuthenticationHelper jwtAuthenticationHelper;

    @Autowired
    private UserRepository userRepository;


    public List<BookIssuedDTO> getAllBookIssuedRecords() {
        return bookIssuedRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BookIssuedDTO getBookIssuedById(Long id) {
        BookIssued bookIssued = bookIssuedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookIssued not found"));
        return toDTO(bookIssued);
    }


    public Boolean createBookIssuedRecord(BookIssuedDTO bookIssuedDTO) {

        BookRequest bookRequest = bookRequestRepository.findById(bookIssuedDTO.getBookRequestId())
                .orElseThrow(() -> new RuntimeException("BookRequest not found"));

        BookIssued bookIssued = BookIssued.builder()
                .bookRequest(bookRequest)
                .issuedDate(bookIssuedDTO.getIssuedDate())
                .dueDate(bookIssuedDTO.getDueDate())
                .returnDate(bookIssuedDTO.getReturnDate())
                .build();

        return true;
    }


    private BookIssuedDTO toDTO(BookIssued bookIssued) {
        return BookIssuedDTO.builder()
                .bookRequestId(bookIssued.getBookRequest().getId())
                .issuedDate(bookIssued.getIssuedDate())
                .dueDate(bookIssued.getDueDate())
                .returnDate(bookIssued.getReturnDate())
                .build();
    }

    // selecte all the books returned by the user
    public List<BookIssuedDTO> getReturnedBooksByUser(String jwtToken) {
        String email = jwtAuthenticationHelper.getUsernameFromToken(jwtToken);
        User user = (User)userRepository.findByEmail(email).get();
        List<Object[]> issuedBooks = bookIssuedRepository.findAllReturnBooksByUser(user.getId());

        List<BookIssuedDTO> bookIssuedDTOS = new ArrayList<>();

        for (Object[] row : issuedBooks) {
            String bookName = (String) row[0]; // bookName
            Long bookRequestId = (Long) row[4];
            Date issuedDate = (Date) row[1]; // issuedDate
            Date dueDate = (Date) row[2]; // dueDate
            Date returnDate = (Date) row[3]; // returnDate

            bookIssuedDTOS.add(
                    BookIssuedDTO.builder()
                            .bookRequestId(bookRequestId)
                            .bookName(bookName)
                            .issuedDate(issuedDate)
                            .dueDate(dueDate)
                            .returnDate(returnDate)
                            .fine(calculateFine(dueDate,returnDate))
                            .build()
            );
        }
        return bookIssuedDTOS;
    }

    //calculate the fine for the book
    private long calculateFine(java.util.Date dueDate, java.util.Date returnDate) {
        if (returnDate == null || !returnDate.after(dueDate)) {
            return 0; // No fine if returned on or before due date
        }

        long daysLate = ChronoUnit.DAYS.between(
                dueDate.toInstant(),
                returnDate.toInstant()
        );
        return daysLate * 10; // Assuming ₹10 fine per day
    }

    //get all books issued to user
    public List<BookIssuedDTO> getIssuedBooksByUser(String jwtToken) {

        String email = jwtAuthenticationHelper.getUsernameFromToken(jwtToken);
        User user = (User)userRepository.findByEmail(email).get();
        List<Object[]> issuedBooks = bookIssuedRepository.findAllIssuedBooksByUser(user.getId());
        List<BookIssuedDTO> bookIssuedDTOS = new ArrayList<>();

        for (Object[] row : issuedBooks) {
            String bookName = (String) row[0]; // bookName
            Long bookRequestId = (Long) row[4];
            Date issuedDate = (Date) row[2]; // issuedDate
            Date dueDate = (Date) row[3]; // dueDate
            String bookAuthor = (String) row[1]; // returnDate

            bookIssuedDTOS.add(
                    BookIssuedDTO.builder()
                            .bookRequestId(bookRequestId)
                            .bookName(bookName)
                            .issuedDate(issuedDate)
                            .dueDate(dueDate)
                            .bookAuthor(bookAuthor)
                            .build()
            );
        }
        return bookIssuedDTOS;
    }
}
