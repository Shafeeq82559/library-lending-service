package com.example.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Book book;

    @ManyToOne(optional = false)
    private Member member;

    private LocalDate borrowedOn;

    private LocalDate dueOn;

    private LocalDate returnedOn;

    protected Loan() {
    }

    public Loan(Book book, Member member, LocalDate borrowedOn, LocalDate dueOn) {
        this.book = book;
        this.member = member;
        this.borrowedOn = borrowedOn;
        this.dueOn = dueOn;
    }

    public boolean isOpen() {
        return returnedOn == null;
    }

    public boolean isOverdueOn(LocalDate date) {
        return isOpen() && date.isAfter(dueOn);
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getBorrowedOn() {
        return borrowedOn;
    }

    public LocalDate getDueOn() {
        return dueOn;
    }

    public void setDueOn(LocalDate dueOn) {
        this.dueOn = dueOn;
    }

    public LocalDate getReturnedOn() {
        return returnedOn;
    }

    public void setReturnedOn(LocalDate returnedOn) {
        this.returnedOn = returnedOn;
    }
}
