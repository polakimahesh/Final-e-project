package com.javaproject.Ecommerce.Cart;

import com.javaproject.Ecommerce.DTO.*;
import com.javaproject.Ecommerce.Products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductService productService;

    //get all the carts
    @GetMapping("")
    public ResponseEntity<List<Cart>> getAllCarts(){
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
    }
    //create cart
    @PostMapping("/create-cart")
    public ResponseEntity<Object> createCart(@RequestBody CartDto cartDto){
        var cart = cartService.createCart(cartDto);
        if(Boolean.TRUE.equals(cart.get("isSuccess"))){
            return ResponseEntity.ok(cart.get("message"));
        }else
            return ResponseEntity.badRequest().body(cart.get("message"));
    }
    //create cart items
    @PostMapping("/create-cart-items")
    public ResponseEntity<Object> createCartItem(@RequestBody CartItemDto cartItemDto){
        var cartItems=cartService.createCartItems(cartItemDto);
        if(Boolean.TRUE.equals(cartItems.get("isSuccess"))){
            return ResponseEntity.ok(cartItems.get("message"));
        }else
            return ResponseEntity.badRequest().body(cartItems.get("message"));

    }
    @PostMapping ("/get-all-cart-items")
    public ResponseEntity<Object> getAllCartItems(@RequestBody GetCartDto getCartDto){
       var cartItem =cartService.getAllCartItemsWithID(getCartDto);
        if(Boolean.TRUE.equals(cartItem.get("isSuccess"))){
            return ResponseEntity.ok(cartItem.get("message"));
        }else
            return ResponseEntity.badRequest().body(cartItem.get("message"));
    }
    @PostMapping("/update-cart-items")
    public ResponseEntity<Object> updateCartItems(@RequestBody CartUpdateItemDto cartUpdateItemDto){
        var cartItem=cartService.updateCartItems(cartUpdateItemDto);
        if(Boolean.TRUE.equals(cartItem.get("isSuccess"))){
            return ResponseEntity.ok(cartItem.get("message"));
        }else
            return ResponseEntity.badRequest().body(cartItem.get("message"));
    }
    @PostMapping("/cart-quantity")
    public ResponseEntity<Object> productsAvailability(@RequestBody GetAvailabilityDto getAvailabilityDto){
        return new ResponseEntity<>(productService.productsAvailabilityCheck(getAvailabilityDto),HttpStatus.OK);
    }
}
