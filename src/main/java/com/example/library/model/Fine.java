package com.example.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private Loan loan;

    @ManyToOne(optional = false)
    private Member member;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    private LocalDate issuedOn;

    protected Fine() {
    }

    public Fine(Loan loan, BigDecimal amount, LocalDate issuedOn) {
        this.loan = loan;
        this.member = loan.getMember();
        this.amount = amount;
        this.issuedOn = issuedOn;
    }

    public Long getId() {
        return id;
    }

    public Loan getLoan() {
        return loan;
    }

    public Member getMember() {
        return member;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getIssuedOn() {
        return issuedOn;
    }
}
