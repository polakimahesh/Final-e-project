package com.javaproject.Ecommerce.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
//    Orders findByCustomer_Id(int id);

}
