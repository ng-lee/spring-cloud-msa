package com.memberservice.service;

import com.memberservice.dto.MemberDto;
import com.memberservice.entity.Member;

import java.util.List;

public interface MemberService {

    MemberDto createMember(MemberDto memberDto);

    MemberDto getMemberByMemberId(String memberId);

    List<Member> getMemberByAll();

}
