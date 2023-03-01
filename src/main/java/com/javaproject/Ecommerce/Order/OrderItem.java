package com.javaproject.Ecommerce.Order;

import com.javaproject.Ecommerce.Products.Product;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue()
    private int id;
    @ManyToOne
    @Cascade({CascadeType.ALL})
    private Orders orders;
    private String itemName;
    private  int itemQuantity;
    private  double itemPrice;
    private String description;
    private double totalPrice;

}
