package com.example.Book.Management.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookIssued {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_request_id", nullable = false)
    private BookRequest bookRequest;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date issuedDate;
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    private Date returnDate;

}
