package com.javaproject.Ecommerce.Cart;

import com.javaproject.Ecommerce.Customer.Customer;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(unique = true)
    private Customer customer;


}
