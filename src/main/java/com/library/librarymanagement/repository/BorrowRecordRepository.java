package com.library.librarymanagement.repository;

import com.library.librarymanagement.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByUsername(String username);
    List<BorrowRecord> findByStatus(String status);
}