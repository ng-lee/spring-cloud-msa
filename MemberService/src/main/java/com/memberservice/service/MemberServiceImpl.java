package com.memberservice.service;

import com.memberservice.dto.MemberDto;
import com.memberservice.entity.Member;
import com.memberservice.repository.MemberRepository;
import com.memberservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public MemberDto createMember(MemberDto memberDto) {
        memberDto.setMemberId(UUID.randomUUID().toString());

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .name(memberDto.getName())
                .memberId(memberDto.getMemberId())
                .encryptedPwd(passwordEncoder.encode(memberDto.getPwd())).build();

        memberRepository.save(member);

        return MemberDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .memberId(member.getMemberId()).build();
    }

    @Override
    public MemberDto getMemberByMemberId(String memberId) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new UsernameNotFoundException("Member Not Found"));

        MemberDto memberDto = MemberDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .memberId(member.getMemberId()).build();

        List<ResponseOrder> orders = new ArrayList<>();
        memberDto.setOrders(orders);

        return memberDto;
    }

    @Override
    public List<Member> getMemberByAll() {
        return memberRepository.findAll();
    }
}
