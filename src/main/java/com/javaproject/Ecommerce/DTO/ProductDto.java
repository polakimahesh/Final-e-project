package com.javaproject.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ProductDto {
    @NotNull(message = "Product id is required")
    private int productId;
    @NotBlank(message = "Name is must be required")
    private String name;
    private int quantity;
    private double price;
    @NotNull(message = "Description is mandatory")
    private String description;
    private  double totalPrice;
}
