package com.library.librarymanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // Username of the user who borrowed
    private String bookTitle; // Title of the borrowed book
    private String borrowDate;
    private String status; // "BORROWED" or "RETURNED"
}