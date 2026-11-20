package com.example.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private LocalDate joinedOn;

    @Enumerated(EnumType.STRING)
    private PatronStanding standing = PatronStanding.GOOD;

    private String standingNote;

    protected Member() {
    }

    public Member(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.joinedOn = LocalDate.now();
    }

    public boolean isInGoodStanding() {
        return standing == PatronStanding.GOOD;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getJoinedOn() {
        return joinedOn;
    }

    public PatronStanding getStanding() {
        return standing;
    }

    public void setStanding(PatronStanding standing) {
        this.standing = standing;
    }

    public String getStandingNote() {
        return standingNote;
    }

    public void setStandingNote(String standingNote) {
        this.standingNote = standingNote;
    }
}
