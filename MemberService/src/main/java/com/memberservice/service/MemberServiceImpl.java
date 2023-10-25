package com.memberservice.service;

import com.memberservice.dto.MemberDto;
import com.memberservice.entity.Member;
import com.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDto createMember(MemberDto memberDto) {
        memberDto.setMemberId(UUID.randomUUID().toString());

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .name(memberDto.getName())
                .memberId(memberDto.getMemberId())
                .encryptedPwd("encrypted_password").build();

        memberRepository.save(member);

        return MemberDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .memberId(member.getMemberId()).build();
    }
}
