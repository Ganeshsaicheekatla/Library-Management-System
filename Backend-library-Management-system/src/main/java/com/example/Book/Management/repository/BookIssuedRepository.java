package com.example.Book.Management.repository;

import com.example.Book.Management.entity.BookIssued;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookIssuedRepository extends JpaRepository<BookIssued , Long> {

    @Query("SELECT  b.bookRequest.book.bookName, b.issuedDate, b.dueDate, b.returnDate,b.bookRequest.id " +
            " FROM BookIssued b WHERE b.returnDate IS NOT NULL " +
            "AND b.bookRequest.user.id = :userId")
    List<Object[]> findAllReturnBooksByUser(@Param("userId") Long id);

    @Query("SELECT  b.bookRequest.book.bookName,b.bookRequest.book.bookAuthor, b.issuedDate, b.dueDate,b.bookRequest.id " +
            " FROM BookIssued b WHERE b.returnDate IS NULL " +
            "AND b.bookRequest.user.id = :userId")
    List<Object[]> findAllIssuedBooksByUser(@Param("userId") Long id);
}
