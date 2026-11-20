package com.example.library.service;

import com.example.library.dto.RegisterMemberRequest;
import com.example.library.dto.UpdateMemberRequest;
import com.example.library.exception.BusinessRuleException;
import com.example.library.model.Fine;
import com.example.library.model.Member;
import com.example.library.model.PatronStanding;
import com.example.library.repository.FineRepository;
import com.example.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final FineRepository fineRepository;

    public MemberService(MemberRepository memberRepository, FineRepository fineRepository) {
        this.memberRepository = memberRepository;
        this.fineRepository = fineRepository;
    }

    @Transactional
    public Member register(RegisterMemberRequest request) {
        if (memberRepository.existsByEmailIgnoreCase(request.email())) {
            throw BusinessRuleException.conflict("Email " + request.email() + " is already registered");
        }
        return memberRepository.save(new Member(request.fullName(), request.email(), request.phone()));
    }

    @Transactional(readOnly = true)
    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> BusinessRuleException.notFound("Member", id));
    }

    @Transactional
    public Member update(Long id, UpdateMemberRequest request) {
        Member member = getMember(id);
        if (request.fullName() != null) {
            member.setFullName(request.fullName());
        }
        if (request.email() != null) {
            member.setEmail(request.email());
        }
        if (request.phone() != null) {
            member.setPhone(request.phone());
        }
        return memberRepository.save(member);
    }

    @Transactional
    public Member restrict(Long id, String note) {
        Member member = getMember(id);
        member.setStanding(PatronStanding.RESTRICTED);
        member.setStandingNote(note);
        return memberRepository.save(member);
    }

    @Transactional
    public Member clearStanding(Long id) {
        Member member = getMember(id);
        member.setStanding(PatronStanding.GOOD);
        member.setStandingNote(null);
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<Fine> finesFor(Long memberId) {
        getMember(memberId);
        return fineRepository.findByMemberId(memberId);
    }
}
