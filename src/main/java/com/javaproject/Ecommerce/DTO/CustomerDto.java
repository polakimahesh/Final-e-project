package com.javaproject.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CustomerDto {
    @NotNull(message = "Customer id is required")
    @Column(unique = true)
    private int customerId;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message="Phone no is mandatory")
    private long phoneNo;
    @NotBlank(message ="Location is Mandatory")
    private String location;
}
