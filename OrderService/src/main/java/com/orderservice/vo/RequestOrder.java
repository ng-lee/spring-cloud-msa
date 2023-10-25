package com.orderservice.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RequestOrder {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
}
