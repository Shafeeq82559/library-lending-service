package com.example.library.repository;

import com.example.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    long countByMemberIdAndReturnedOnIsNull(Long memberId);

    List<Loan> findByMemberIdAndReturnedOnIsNull(Long memberId);
}
