package com.javaproject.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetQuantityDto {
    private int productId;
    private  int orderItemId;
    private int quantity;

}
