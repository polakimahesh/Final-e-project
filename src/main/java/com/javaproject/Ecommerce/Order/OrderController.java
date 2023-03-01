package com.javaproject.Ecommerce.Order;

import com.javaproject.Ecommerce.DTO.*;
import com.javaproject.Ecommerce.Products.Product;
import com.javaproject.Ecommerce.Products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderservice;
    @Autowired
    private ProductRepository productRepository;

    //get all the orders
    @GetMapping("")
    public ResponseEntity<List<Orders>> getAllOrders(){
        return  new ResponseEntity<>(orderservice.getAllOrders(), HttpStatus.OK);
    }
    //create the orders
    @PostMapping("")
    public ResponseEntity<Object> createOrder(@RequestBody OrderDto orderDto){
        var order=orderservice.createOrder(orderDto);
        if(Boolean.TRUE.equals(order.get("isSuccess"))){
            return ResponseEntity.ok(order.get("message"));
        }else
            return ResponseEntity.badRequest().body(order.get("message"));
    }
//    @PostMapping("/create-order-items")
//    public ResponseEntity<Object> createOrderItems(@RequestBody OrderItemDto orderItemDto){
//        return  new ResponseEntity<>(orderservice.createOrderItems(orderItemDto),HttpStatus.CREATED);
//    }
    //get all the order items with customer id
    @PostMapping("/get-order-items")
    public ResponseEntity<Object> getAllOrderItemsWithCustomerId(@RequestBody GetOrderDto getOrderDto){
        var orderItem=orderservice.getAllOrderItemsWithCustomerId(getOrderDto);
        if(Boolean.TRUE.equals(orderItem.get("isSuccess"))){
            return ResponseEntity.ok(orderItem.get("message"));
        }else
            return ResponseEntity.badRequest().body(orderItem.get("message"));
    }
    @PostMapping("/get-single-order")
    public ResponseEntity<Object> getSingleOrderItem(@RequestBody GetSingleItemDto getSingleItemDto){
        var orderItem=orderservice.getSingleOrderItems(getSingleItemDto);
        if(Boolean.TRUE.equals(orderItem.get("isSuccess"))){
            return ResponseEntity.ok(orderItem.get("message"));
        }else
            return ResponseEntity.badRequest().body(orderItem.get("message"));
    }
    @PostMapping("/orders-item")
    public ResponseEntity<Object> itemQuantityCheck(@RequestBody GetQuantityDto getQuantityDto){
        var quantity=orderservice.itemQuantityCheck(getQuantityDto);
       return  new ResponseEntity<>(quantity,HttpStatus.OK);
    }

}
