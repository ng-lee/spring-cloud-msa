package com.memberservice.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMember {

    private String email;
    private String name;
    private String memberId;
}
