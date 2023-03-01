package com.javaproject.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetOrderDto {
    private int orderId;
    private List<OrderItemResponseDto> orderItems;
    private double grantTotal;

}
