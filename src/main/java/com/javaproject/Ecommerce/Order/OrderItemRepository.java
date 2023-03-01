package com.javaproject.Ecommerce.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer>{
    List<OrderItem> findByOrders_Id(int id);

    OrderItem findByOrders_IdAndOrders_Customer_IdAndId(int ordersId, int ordersCustomerId,int orderItemId);



}
