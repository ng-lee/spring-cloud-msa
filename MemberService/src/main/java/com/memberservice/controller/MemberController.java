package com.memberservice.controller;

import com.memberservice.vo.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MemberController {

    @Autowired
    private Greeting greeting;

    @GetMapping("/health-check")
    public String status() {
        return "It's working in Member Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }
}
