package com.javaproject.Ecommerce.Order;

import com.javaproject.Ecommerce.Cart.Cart;
import com.javaproject.Ecommerce.Customer.Customer;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
     @CreationTimestamp
     @Column(nullable = false, updatable = false)
     private Date createdAt;
     private String address;
     @ManyToOne
     private Customer customer;

     private  double grandTotal;


}
