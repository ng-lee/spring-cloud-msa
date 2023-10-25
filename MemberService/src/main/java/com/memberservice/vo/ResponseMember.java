package com.memberservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMember {

    private String email;
    private String name;
    private String memberId;

    private List<ResponseOrder> orders;
}
