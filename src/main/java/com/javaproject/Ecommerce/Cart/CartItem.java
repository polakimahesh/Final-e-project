package com.javaproject.Ecommerce.Cart;

import com.javaproject.Ecommerce.Customer.Customer;
import com.javaproject.Ecommerce.Products.Product;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Cart cart;
    @OneToOne
    private Product product;
    private String itemName;
    private  int itemQuantity;
    private  double itemPrice;
    private String description;
    private double totalPrice;

}
