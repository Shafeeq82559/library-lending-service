package com.example.library.service;

import com.example.library.exception.BusinessRuleException;
import com.example.library.model.Book;
import com.example.library.model.Fine;
import com.example.library.model.Loan;
import com.example.library.model.Member;
import com.example.library.repository.BookRepository;
import com.example.library.repository.FineRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    static final int LOAN_PERIOD_DAYS = 14;
    static final int MAX_OPEN_LOANS = 5;

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final FineRepository fineRepository;
    private final FineCalculator fineCalculator;
    private final Clock clock;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository,
                       MemberRepository memberRepository, FineRepository fineRepository,
                       FineCalculator fineCalculator, Clock clock) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.fineRepository = fineRepository;
        this.fineCalculator = fineCalculator;
        this.clock = clock;
    }

    @Transactional
    public Loan borrow(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BusinessRuleException.notFound("Member", memberId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> BusinessRuleException.notFound("Book", bookId));

        if (!member.isInGoodStanding()) {
            throw BusinessRuleException.rejected("Member account is restricted");
        }
        if (loanRepository.countByMemberIdAndReturnedOnIsNull(memberId) >= MAX_OPEN_LOANS) {
            throw BusinessRuleException.rejected("Member already has " + MAX_OPEN_LOANS + " books checked out");
        }
        if (!book.hasAvailableCopy()) {
            throw BusinessRuleException.rejected("No copies of '" + book.getTitle() + "' are available");
        }

        LocalDate today = LocalDate.now(clock);
        book.checkOutCopy();
        bookRepository.save(book);
        return loanRepository.save(new Loan(book, member, today, today.plusDays(LOAN_PERIOD_DAYS)));
    }

    @Transactional
    public Loan returnBook(Long loanId) {
        Loan loan = getOpenLoan(loanId);
        LocalDate today = LocalDate.now(clock);
        loan.setReturnedOn(today);

        Book book = loan.getBook();
        book.checkInCopy();
        bookRepository.save(book);

        BigDecimal amount = fineCalculator.calculate(loan.getDueOn(), today);
        if (amount.signum() > 0) {
            fineRepository.save(new Fine(loan, amount, today));
        }
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan renew(Long loanId) {
        Loan loan = getOpenLoan(loanId);
        if (loan.isOverdueOn(LocalDate.now(clock))) {
            throw BusinessRuleException.rejected("Overdue loans cannot be renewed");
        }
        loan.setDueOn(loan.getDueOn().plusDays(LOAN_PERIOD_DAYS));
        return loanRepository.save(loan);
    }

    @Transactional(readOnly = true)
    public List<Loan> openLoansFor(Long memberId) {
        return loanRepository.findByMemberIdAndReturnedOnIsNull(memberId);
    }

    private Loan getOpenLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> BusinessRuleException.notFound("Loan", loanId));
        if (!loan.isOpen()) {
            throw BusinessRuleException.rejected("Loan " + loanId + " is already closed");
        }
        return loan;
    }
}
