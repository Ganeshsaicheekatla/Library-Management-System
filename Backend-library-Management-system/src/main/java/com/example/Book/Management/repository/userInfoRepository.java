package com.example.Book.Management.repository;


import com.example.Book.Management.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userInfoRepository extends JpaRepository<UserInfo , Long> {
}
