package com.javaproject.Ecommerce.Customer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CustomerDeleteDto {
    @NotNull(message = "Customer id is required")
    private int CustomerId;
}
