package com.memberservice.dto;

import com.memberservice.vo.ResponseOrder;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class MemberDto {

    private String email;
    private String name;
    private String pwd;
    private String memberId;
    private Date createdAt;
    private String encryptedPwd;

    private List<ResponseOrder> orders;
}
