package com.memberservice.controller;

import com.memberservice.dto.MemberDto;
import com.memberservice.service.MemberService;
import com.memberservice.vo.Greeting;
import com.memberservice.vo.RequestMember;
import com.memberservice.vo.ResponseMember;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/member-service")
@RequiredArgsConstructor
public class MemberController {

    private final Greeting greeting;
    private final MemberService memberService;
    private final Environment env;

    @GetMapping("/health-check")
    public String status() {
        StringBuilder sb = new StringBuilder();
        sb.append("It's working in Member Service").append("\n")
                .append("port(local.server.port)=").append(env.getProperty("local.server.port")).append("\n")
                .append("port(server.port)=").append(env.getProperty("server.port")).append("\n")
                .append("token secret=").append(env.getProperty("token.secret")).append("\n")
                .append("token expiration time=").append(env.getProperty("token.expiration_time"));

        return sb.toString();
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseMember> createUser(@RequestBody RequestMember requestMember) {

        MemberDto memberDto = MemberDto.builder()
                .email(requestMember.getEmail())
                .name(requestMember.getName())
                .pwd(requestMember.getPwd()).build();

        memberService.createMember(memberDto);

        ResponseMember responseMember = ResponseMember.builder()
                .email(memberDto.getEmail())
                .name(memberDto.getName())
                .memberId(memberDto.getMemberId())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMember);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseMember>> getUsers() {
        List<ResponseMember> result = memberService.getMemberByAll().stream().map(member -> ResponseMember.builder()
                .email(member.getEmail())
                .name(member.getName())
                .memberId(member.getMemberId())
                .orders(new ArrayList<>()).build()).toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{user-id}")
    public ResponseEntity<ResponseMember> getUser(@PathVariable("user-id") String memberId) {
        MemberDto memberDto = memberService.getMemberByMemberId(memberId);
        ResponseMember responseMember = ResponseMember.builder()
                .email(memberDto.getEmail())
                .name(memberDto.getName())
                .memberId(memberDto.getMemberId())
                .orders(new ArrayList<>()).build();

        return ResponseEntity.ok(responseMember);
    }
}
