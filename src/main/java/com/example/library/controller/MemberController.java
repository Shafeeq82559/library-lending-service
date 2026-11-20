package com.example.library.controller;

import com.example.library.dto.RegisterMemberRequest;
import com.example.library.dto.StandingChangeRequest;
import com.example.library.dto.UpdateMemberRequest;
import com.example.library.model.Fine;
import com.example.library.model.Loan;
import com.example.library.model.Member;
import com.example.library.model.PatronStanding;
import com.example.library.service.LoanService;
import com.example.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final LoanService loanService;

    public MemberController(MemberService memberService, LoanService loanService) {
        this.memberService = memberService;
        this.loanService = loanService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member register(@Valid @RequestBody RegisterMemberRequest request) {
        return memberService.register(request);
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.getMember(id);
    }

    @PutMapping("/{id}")
    public Member update(@PathVariable Long id, @RequestBody UpdateMemberRequest request) {
        return memberService.update(id, request);
    }

    @PutMapping("/{id}/standing")
    public Member changeStanding(@PathVariable Long id, @Valid @RequestBody StandingChangeRequest request) {
        if (request.standing() == PatronStanding.RESTRICTED) {
            return memberService.restrict(id, request.note());
        }
        return memberService.clearStanding(id);
    }

    @GetMapping("/{id}/loans")
    public List<Loan> openLoans(@PathVariable Long id) {
        return loanService.openLoansFor(id);
    }

    @GetMapping("/{id}/fines")
    public List<Fine> fines(@PathVariable Long id) {
        return memberService.finesFor(id);
    }
}
