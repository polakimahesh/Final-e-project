package com.javaproject.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemResponseDto {
//    private int orderItemId;
    private String itemName;
    private  int itemQuantity;
    private  double itemPrice;
    private String description;
    private double totalPrice;
}
