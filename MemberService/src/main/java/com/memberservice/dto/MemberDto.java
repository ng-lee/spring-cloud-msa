package com.memberservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MemberDto {

    private String email;
    private String name;
    private String pwd;
    private String memberId;
    private Date createdAt;
    private String encryptedPwd;
}
