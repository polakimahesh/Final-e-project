package com.javaproject.Ecommerce.Customer;

import com.javaproject.Ecommerce.DTO.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

    @RestController
    @RequestMapping("/customers")
    public class CustomerController {

    @Autowired
    private CustomerService customerService;
    // get all the customers
    @GetMapping("/users")
    public ResponseEntity<List<Customer>> getAllCustomers(){
         return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }
    //create customer
    @PostMapping("/register-customer")
    public  ResponseEntity<Customer> createCustomer(@Valid @RequestBody  CustomerDto customerDto){
        return  new ResponseEntity<>(customerService.createCustomer(customerDto),HttpStatus.CREATED);
    }
    //get single customer
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getSingleCustomer(@PathVariable int id){
        return  new ResponseEntity<>(customerService.getSingleCustomer(id),HttpStatus.OK);
    }
    //update customer by using postmapping
    @PostMapping("/update-customer")
    public ResponseEntity<Object> updateCustomer(@Valid @RequestBody CustomerDto customerDto){
        HashMap<String,Object> customer =customerService.updateCustomer(customerDto);
        if(Boolean.TRUE.equals(customer.get("isSuccess"))){
            return ResponseEntity.ok(customer.get("message"));
        }else
            return ResponseEntity.badRequest().body(customer.get("message"));
    }
    @PostMapping("/delete-customer")
    public ResponseEntity<Object> deleteCustomer(@Valid @RequestBody CustomerDeleteDto customerDeleteDto){
        var customer = customerService.deleteCustomer(customerDeleteDto);
        if(Boolean.TRUE.equals(customer.get("isSuccess"))){
            return ResponseEntity.ok(customer.get("message"));
        }else
            return ResponseEntity.badRequest().body(customer.get("message"));
    }

}
