package com.example.Book.Management.repository;

import com.example.Book.Management.entity.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRequestRepository extends JpaRepository<BookRequest , Long> {

    @Query("SELECT b FROM BookRequest b WHERE b.user.id = :userId")
    List<BookRequest> findAllByUserId(@Param("userId") Long userId);
}
