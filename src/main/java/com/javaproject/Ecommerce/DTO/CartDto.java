package com.javaproject.Ecommerce.DTO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;

@Setter
@Getter
public class CartDto {
    private int customerId;

}
