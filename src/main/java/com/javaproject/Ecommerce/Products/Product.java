package com.javaproject.Ecommerce.Products;

import com.javaproject.Ecommerce.Customer.Customer;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name is must be required")
    private String name;
    private int quantity;
    private double price;
    @NotNull(message = "Description is mandatory")
    private String description;


//    public Product(int id){
//        this.id=id;
//    }

}
