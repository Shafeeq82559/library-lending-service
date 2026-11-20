package com.example.library;

import com.example.library.dto.CreateBookRequest;
import com.example.library.dto.RegisterMemberRequest;
import com.example.library.exception.BusinessRuleException;
import com.example.library.model.Book;
import com.example.library.model.Loan;
import com.example.library.model.Member;
import com.example.library.service.BookService;
import com.example.library.service.LoanService;
import com.example.library.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class LoanServiceTest {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    private Member member;
    private Book book;

    @BeforeEach
    void setUp() {
        member = memberService.register(new RegisterMemberRequest("Ada Reader", "ada@example.com", "555-0100"));
        book = bookService.addBook(new CreateBookRequest("9780000000001", "Dune", "Frank Herbert", "Sci-Fi", 1965, 2));
    }

    @Test
    void borrowingTakesACopyOffTheShelf() {
        Loan loan = loanService.borrow(member.getId(), book.getId());

        assertThat(loan.getDueOn()).isEqualTo(loan.getBorrowedOn().plusDays(14));
        assertThat(bookService.getBook(book.getId()).getAvailableCopies()).isEqualTo(1);
    }

    @Test
    void returningPutsTheCopyBack() {
        Loan loan = loanService.borrow(member.getId(), book.getId());

        Loan closed = loanService.returnBook(loan.getId());

        assertThat(closed.getReturnedOn()).isNotNull();
        assertThat(bookService.getBook(book.getId()).getAvailableCopies()).isEqualTo(2);
    }

    @Test
    void cannotBorrowWhenNoCopiesLeft() {
        Member other = memberService.register(new RegisterMemberRequest("Bo Page", "bo@example.com", null));
        loanService.borrow(member.getId(), book.getId());
        loanService.borrow(other.getId(), book.getId());

        Member third = memberService.register(new RegisterMemberRequest("Cy Spine", "cy@example.com", null));
        assertThatThrownBy(() -> loanService.borrow(third.getId(), book.getId()))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    void sixthOpenLoanIsRejected() {
        for (int i = 0; i < 5; i++) {
            Book b = bookService.addBook(new CreateBookRequest("978000000010" + i, "Title " + i, "Author", null, null, 1));
            loanService.borrow(member.getId(), b.getId());
        }

        assertThatThrownBy(() -> loanService.borrow(member.getId(), book.getId()))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("5 books");
    }

    @Test
    void restrictedMemberCannotBorrowUntilCleared() {
        memberService.restrict(member.getId(), "Damaged item");

        assertThatThrownBy(() -> loanService.borrow(member.getId(), book.getId()))
                .isInstanceOf(BusinessRuleException.class);

        memberService.clearStanding(member.getId());
        assertThat(loanService.borrow(member.getId(), book.getId())).isNotNull();
    }

    @Test
    void renewingPushesTheDueDateOut() {
        Loan loan = loanService.borrow(member.getId(), book.getId());

        Loan renewed = loanService.renew(loan.getId());

        assertThat(renewed.getDueOn()).isEqualTo(loan.getBorrowedOn().plusDays(28));
    }
}
