package com.javaproject.Ecommerce.DTO;

import com.javaproject.Ecommerce.Cart.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetCartDto {
    private int cartId;
    private double cartTotal;
    private List<CartItemResponseDto> cartItems;
}
