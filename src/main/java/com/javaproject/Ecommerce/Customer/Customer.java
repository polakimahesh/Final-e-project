package com.javaproject.Ecommerce.Customer;

import com.javaproject.Ecommerce.Cart.Cart;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message="Phone no is mandatory")
    @Column(unique = true)
    private long phoneNo;
    @NotBlank(message ="Location is Mandatory")
    private String location;


}
