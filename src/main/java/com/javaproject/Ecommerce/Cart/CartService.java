package com.javaproject.Ecommerce.Cart;

import com.javaproject.Ecommerce.Customer.Customer;
import com.javaproject.Ecommerce.Customer.CustomerRepository;
import com.javaproject.Ecommerce.DTO.*;
import com.javaproject.Ecommerce.Products.Product;
import com.javaproject.Ecommerce.Products.ProductRepository;
import com.javaproject.Ecommerce.Products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CartService {

    @Autowired
     private CartRepository cartRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;


    public List<Cart> getAllCarts() {
       return cartRepository.findAll();
    }

    public HashMap<String,Object> createCart(CartDto cartDto){
        HashMap<String,Object> response = new HashMap<>();
        HashMap<String,Object> response1= new HashMap<>();
        Cart cart ;
        cart=cartRepository.findById(cartDto.getCustomerId()).orElse(null);

        if(cart==null){
            Customer customer =customerRepository.findById(cartDto.getCustomerId()).orElse(null);
            if(customer==null){
                response1.put("message","incorrect customer id "+cartDto.getCustomerId());
                response.put("isSuccess",false);
                response.put("message",response1);
                return  response;
            }
//            response1.put("message","customer id"+cartDto.getCustomerId()+" is already exists!");
//            response.put("isSuccess",true);
//            response.put("message",response1);
            cart = new Cart();
            cart.setCustomer(customer);
            cart= cartRepository.save(cart);
            response.put("isSuccess",true);
            response.put("message",cart);
            return  response;
        }
        response.put("isSuccess",true);
        response.put("message",cart);
        return  response;
    }

    public HashMap<String,Object> createCartItems(CartItemDto cartItemDto){
        HashMap<String,Object> response = new HashMap<>();
        HashMap<String,Object> response1= new HashMap<>();
        Cart cart = cartRepository.findById((cartItemDto.getCartId())).orElse(null);
        Product product =productRepository.findById(cartItemDto.getProductId()).orElse(null);
        if(cart==null){
            response1.put("message","incorrect customer cart id "+cartItemDto.getCartId());
            response.put("isSuccess",false);
            response.put("message",response1);
            return  response;
        }else if(product==null) {
            response1.put("message", "incorrect product id " + cartItemDto.getProductId());
            response.put("isSuccess", false);
            response.put("message", response1);
            return response;
        }else {
            GetAvailabilityDto getAvailabilityDto = new GetAvailabilityDto();
            getAvailabilityDto.setProductId(cartItemDto.getProductId());
            getAvailabilityDto.setQuantity(cartItemDto.getItemQuantity());
            var isProductAvailable = productService.productsAvailabilityCheck(getAvailabilityDto);
            if (!isProductAvailable) {
                response1.put("message", product.getName() + " is not available, please wait for some time");
                response.put("isSuccess", false);
                response.put("message", response1);
                return response;
            }
        }
        CartItem  cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setItemName(product.getName());
        cartItem.setItemQuantity(cartItemDto.getItemQuantity());
        cartItem.setItemPrice(product.getPrice());
        cartItem.setDescription(product.getDescription());
        cartItem.setTotalPrice((cartItem.getItemQuantity()*cartItem.getItemPrice()));
        cartItemRepository.save(cartItem);
        response1.put("message",product.getName()+" added to cart successfully!");
        response.put("isSuccess",true);
        response.put("message",response1);
        return  response;
    }





    public HashMap<String,Object> getAllCartItemsWithID(GetCartDto getCartDto){
        HashMap<String,Object> response = new HashMap<>();
        HashMap<String,Object> response1= new HashMap<>();
       Cart cart = cartRepository.findById(getCartDto.getCartId()).orElse(null);
        if(cart==null){
            response1.put("message","incorrect customer cart id "+getCartDto.getCartId());
            response.put("isSuccess",false);
            response.put("message",response1);
            return  response;
        }else {
            List<CartItem> cartItem = cartItemRepository.findByCart_Id(cart.getId());
            List<CartItemResponseDto> cartItemsList = new ArrayList<>();
            double grandTotal=0.0;
            for(CartItem cartItem1:cartItem){
                CartItemResponseDto cartItemResponseDto =new CartItemResponseDto();
//                cartItemResponseDto.setItemId(cartItem1.getId());
                cartItemResponseDto.setItemName(cartItem1.getItemName());
                cartItemResponseDto.setItemQuantity(cartItem1.getItemQuantity());
                cartItemResponseDto.setItemPrice(cartItem1.getItemPrice());
                cartItemResponseDto.setItemDescription(cartItem1.getDescription());
                cartItemResponseDto.setItemTotal(cartItem1.getTotalPrice());
                cartItemsList.add(cartItemResponseDto);
                grandTotal+=cartItemResponseDto.getItemTotal();
            }
//            getCartDto= new GetCartDto();
            getCartDto.setCartId(cart.getId());
            getCartDto.setCartItems(cartItemsList);
            getCartDto.setCartTotal(grandTotal);
            response.put("isSuccess",true);
            response.put("message",getCartDto);
            return response;
        }
    }

    public HashMap<String,Object> updateCartItems(CartUpdateItemDto cartUpdateItemDto){
        HashMap<String,Object> response = new HashMap<>();
        HashMap<String,Object> response1= new HashMap<>();
        Cart cart = cartRepository.findById(cartUpdateItemDto.getCartId()).orElse(null);
        var cartItem=cartItemRepository.findById(cartUpdateItemDto.getCartItemId()).orElse(null);
        if(cart==null){
            response1.put("message","you're cart id "+cartUpdateItemDto.getCartId()+", is empty!");
            response.put("isSuccess",false);
            response.put("message",response1);
            return response;
        }else{
            response1.put("message","Item added successfully in id "+cartUpdateItemDto.getCartId());
             cartItem.setItemQuantity(cartUpdateItemDto.getQuantity());
             cartItem.setTotalPrice(cartUpdateItemDto.getQuantity()*cartItem.getProduct().getPrice());
             cartItemRepository.save(cartItem);
            response.put("isSuccess",true);
            response.put("message",cartItem);
            return response;
        }
    }

}
