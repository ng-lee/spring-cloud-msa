package com.memberservice.controller;

import com.memberservice.dto.MemberDto;
import com.memberservice.service.MemberService;
import com.memberservice.vo.Greeting;
import com.memberservice.vo.RequestMember;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private Greeting greeting;

    private final MemberService memberService;

    @GetMapping("/health-check")
    public String status() {
        return "It's working in Member Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public String createUser(@RequestBody RequestMember requestMember) {

        MemberDto memberDto = MemberDto.builder()
                .email(requestMember.getEmail())
                .name(requestMember.getName())
                .pwd(requestMember.getPwd()).build();

        memberService.createMember(memberDto);

        return "Create member method is called";
    }
}
