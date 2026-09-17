package com.library.librarymanagement.service;

import com.library.librarymanagement.model.*;
import com.library.librarymanagement.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public LibraryService(BookRepository bookRepository, UserRepository userRepository, BorrowRecordRepository borrowRecordRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    // Authenticate user login
    public User authenticate(String username, String password) {
        return userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Save a new book with stock setup
    public void saveBook(Book book) {
        if (book.getId() == null) {
            book.setAvailableQuantity(book.getTotalQuantity());
        }
        bookRepository.save(book);
    }

    // Borrow a book (reduces available quantity and creates a record)
    public boolean borrowBook(Long bookId, String username) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null && book.getAvailableQuantity() > 0) {
            book.setAvailableQuantity(book.getAvailableQuantity() - 1);
            bookRepository.save(book);

            BorrowRecord record = new BorrowRecord();
            record.setUsername(username);
            record.setBookTitle(book.getTitle());
            record.setBorrowDate(LocalDate.now().toString());
            record.setStatus("BORROWED");
            borrowRecordRepository.save(record);
            return true;
        }
        return false;
    }

    // Return a book (increases available quantity and updates record)
    public void returnBook(Long recordId) {
        BorrowRecord record = borrowRecordRepository.findById(recordId).orElse(null);
        if (record != null && "BORROWED".equals(record.getStatus())) {
            record.setStatus("RETURNED");
            borrowRecordRepository.save(record);

            // Restore book stock
            Book book = bookRepository.findAll().stream()
                    .filter(b -> b.getTitle().equals(record.getBookTitle()))
                    .findFirst()
                    .orElse(null);
            if (book != null) {
                book.setAvailableQuantity(book.getAvailableQuantity() + 1);
                bookRepository.save(book);
            }
        }
    }

    // Get all active borrow records for admin view
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAll();
    }

    // Get borrow records for a specific user
    public List<BorrowRecord> getUserBorrowRecords(String username) {
        return borrowRecordRepository.findByUsername(username);
    }

    // Search books
    public List<Book> searchBooks(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
        }
        return bookRepository.findAll();
    }
}