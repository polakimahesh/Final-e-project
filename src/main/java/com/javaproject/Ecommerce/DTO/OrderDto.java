package com.javaproject.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDto {

    private String address;
    private int customerId;
    private  double grandTotal;

}
