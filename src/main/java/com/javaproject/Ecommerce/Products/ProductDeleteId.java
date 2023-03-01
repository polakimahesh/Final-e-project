package com.javaproject.Ecommerce.Products;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ProductDeleteId {
    @NotNull(message = "Product id is required")
    private int productId;
}
