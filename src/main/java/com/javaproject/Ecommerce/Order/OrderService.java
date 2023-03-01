package com.javaproject.Ecommerce.Order;

import com.javaproject.Ecommerce.Cart.*;
import com.javaproject.Ecommerce.Customer.Customer;
import com.javaproject.Ecommerce.Customer.CustomerRepository;
import com.javaproject.Ecommerce.DTO.*;
import com.javaproject.Ecommerce.Products.Product;
import com.javaproject.Ecommerce.Products.ProductRepository;
import com.javaproject.Ecommerce.Products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class OrderService {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public List<Orders> getAllOrders(){
        return  orderRepository.findAll();
    }
    @Transactional
    public HashMap<String,Object> createOrder(OrderDto orderDto){
        HashMap<String,Object> response = new HashMap<>();
        HashMap<String,Object> response1= new HashMap<>();
        Customer customer = customerRepository.findById(orderDto.getCustomerId()).orElse(null);
        Cart cart = cartRepository.findByCustomer_Id(orderDto.getCustomerId());
        Orders orders = new Orders();
        List<CartItem> cartItemsList = cartItemRepository.findByCart_Id(cart.getId());
        for(CartItem cartItem:cartItemsList){
            GetAvailabilityDto getAvailabilityDto = new GetAvailabilityDto();
            getAvailabilityDto.setProductId(cartItem.getProduct().getId());
            getAvailabilityDto.setQuantity(cartItem.getItemQuantity());
        var isProductAvailable= productService.productsAvailabilityCheck(getAvailabilityDto);
        if(!isProductAvailable){
            response1.put("message",cartItem.getItemName()+" is not available, please wait for some time");
            response.put("isSuccess",false);
            response.put("message",response1);
            return  response;
        }

        }
        double grantTotal=0.0;
        if(cart==null){
            response1.put("message","Cart not found with id "+orderDto.getCustomerId());
            response.put("isSuccess",false);
            response.put("message",response1);
            return  response;
        }else {
            for (CartItem cartItem : cartItemsList) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrders(orders);
                orderItem.setItemName(cartItem.getItemName());
                orderItem.setItemPrice(cartItem.getItemPrice());
                orderItem.setItemQuantity(cartItem.getItemQuantity());
                orderItem.setDescription(cartItem.getDescription());
                orderItem.setTotalPrice(cartItem.getTotalPrice());
                orderItemRepository.save(orderItem);
                var product= productRepository.findById(cartItem.getProduct().getId()).orElse(null);
                product.setQuantity(product.getQuantity()- orderItem.getItemQuantity());
                productRepository.save(product);
                grantTotal += orderItem.getTotalPrice();
            }
            orders.setAddress(orderDto.getAddress());
            orders.setCreatedAt(orders.getCreatedAt());
            orders.setCustomer(customer);
            orders.setGrandTotal(grantTotal);
            orderRepository.save(orders);
            cartItemRepository.deleteByCart_Id(cart.getId());
//            return orders;
            response1.put("message","Created with id "+orders.getId());
            response.put("isSuccess",false);
            response.put("message",response1);
            return  response;
        }
    }
//    public OrderItem createOrderItems(OrderItemDto orderItemDto){
//
//        Orders orders=orderRepository.findById(orderItemDto.getOrderId()).orElse(null);
//        Product product=productRepository.findById(orderItemDto.getProductId()).orElse(null);
//        OrderItem orderItem = new OrderItem();
//        orderItem.setOrders(orders);
//        orderItem.setItemName(product.getName());
//        orderItem.setItemPrice(product.getPrice());
//        orderItem.setItemQuantity(orderItemDto.getQuantity());
//        orderItem.setDescription(product.getDescription());
//        orderItem.setTotalPrice(orderItem.getItemPrice()*orderItem.getItemQuantity());
//        orderItemRepository.save(orderItem);
//        return orderItem;
//
//    }



    public HashMap<String,Object> getAllOrderItemsWithCustomerId(GetOrderDto getOrderDto){
        HashMap<String,Object> response = new HashMap<>();
        HashMap<String,Object> response1= new HashMap<>();
        Orders orders =orderRepository.findById(getOrderDto.getOrderId()).orElse(null);
        if(orders==null){
            response1.put("message","order not found with id "+getOrderDto.getOrderId());
            response.put("isSuccess",false);
            response.put("message",response1);
            return  response;
        }else {
            List<OrderItem> orderItems=orderItemRepository.findByOrders_Id(orders.getId());
            List<OrderItemResponseDto> orderItemRList =new ArrayList<>();
            double grandTotal=0.0;
            for(OrderItem orderItem:orderItems){
                OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
//                orderItemResponseDto.setOrderItemId(orderItem.getId());
                orderItemResponseDto.setItemName(orderItem.getItemName());
                orderItemResponseDto.setItemPrice(orderItem.getItemPrice());
                orderItemResponseDto.setItemQuantity(orderItem.getItemQuantity());
                orderItemResponseDto.setDescription(orderItem.getDescription());
                orderItemResponseDto.setTotalPrice(orderItem.getTotalPrice());
                orderItemRList.add(orderItemResponseDto);
                grandTotal+=orderItemResponseDto.getTotalPrice();
            }
            getOrderDto.setOrderId(orders.getId());
            getOrderDto.setOrderItems(orderItemRList);
            getOrderDto.setGrantTotal(grandTotal);
            response.put("isSuccess",true);
            response.put("message",getOrderDto);
            return response;
        }
    }
    @Transactional
    public HashMap<String,Object> getSingleOrderItems(GetSingleItemDto getSingleItemDto){
        HashMap<String,Object> response = new HashMap<>();
        HashMap<String,Object> response1= new HashMap<>();
//        Customer customer=customerRepository.findByCustomerIdAndOrdersIdAndOderItemId(getSingleItemDto.getCustomerId());
//        Orders orders =orderRepository.findById(getSingleItemDto.getOrderId()).orElse(null);
        var orderItem=orderItemRepository.findByOrders_IdAndOrders_Customer_IdAndId(getSingleItemDto.getOrderId(),getSingleItemDto.getCustomerId(),getSingleItemDto.getOrderItemId());

        if(orderItem==null){
            response1.put("message", "order item not found with id " + getSingleItemDto.getOrderItemId());
            response.put("isSuccess", false);
            response.put("message", response1);
            return response;
        }else {
            getSingleItemDto.setOrderItemId(orderItem.getId());
            getSingleItemDto.setCustomerId(orderItem.getOrders().getCustomer().getId());
            getSingleItemDto.setItemName(orderItem.getItemName());
            getSingleItemDto.setItemPrice(orderItem.getItemPrice());
            getSingleItemDto.setItemQuantity(orderItem.getItemQuantity());
            getSingleItemDto.setDescription(orderItem.getDescription());
            getSingleItemDto.setTotalPrice(orderItem.getTotalPrice());
            response.put("isSuccess", true);
            response.put("message", getSingleItemDto);
            return response;
        }
    }

    public  String itemQuantityCheck(GetQuantityDto getQuantityDto) {
        Product product = productRepository.findById(getQuantityDto.getProductId()).orElse(null);
        if(product==null){
           return "product not found with id "+getQuantityDto.getProductId();
        }
        else{
            if (product.getQuantity() == 0 || product.getQuantity() < getQuantityDto.getQuantity()) {
                return "product quantity is low, we have only " + product.getQuantity() + ", products available";
            }
            else {
                product.setQuantity(product.getQuantity()-getQuantityDto.getQuantity());
                productRepository.save(product);
                return  "Remaining quantity " +product.getQuantity();
            }
        }

    }
}
