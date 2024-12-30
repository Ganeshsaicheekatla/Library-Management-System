package com.example.Book.Management.repository;

import com.example.Book.Management.entity.Book;
import com.example.Book.Management.entity.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book , String> {
    Book findByBookCode(String bookCode);



}
