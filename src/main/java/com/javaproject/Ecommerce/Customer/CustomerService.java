package com.javaproject.Ecommerce.Customer;

import com.javaproject.Ecommerce.DTO.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
     private  CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

   public Customer createCustomer(CustomerDto customerDto){
        Customer customer =new Customer();
        customer.setName(customerDto.getName());
        customer.setPhoneNo(customerDto.getPhoneNo());
        customer.setLocation((customerDto.getLocation()));
        customerRepository.save(customer);
        return customer;
   }

   public Customer getSingleCustomer(int id){
        return  customerRepository.findById(id).orElse(null);
   }

   public HashMap<String,Object> updateCustomer(CustomerDto customerDto){
       HashMap<String,Object> response = new HashMap<>();
       HashMap<String,Object> response1= new HashMap<>();
       response1.put("message","incorrect customer id "+customerDto.getCustomerId()+", please enter the valid id!");
       var customer = customerRepository.findById(customerDto.getCustomerId()).orElse(null);
       if(customer==null){
           response.put("isSuccess",false);
           response.put("message",response1);
           return  response;
       }else{
           customer.setName(customerDto.getName());
           customer.setPhoneNo(customerDto.getPhoneNo());
           customer.setLocation(customerDto.getLocation());
           customerRepository.save(customer);
           response.put("isSuccess",true);
           response.put("message",customer);
           return  response;
       }

   }
    public HashMap<String,Object>  deleteCustomer(CustomerDeleteDto customerDto ){
        HashMap<String,Object> response = new HashMap<>();
        HashMap<String,Object> response1= new HashMap<>();

        var customer = customerRepository.findById(customerDto.getCustomerId()).orElse(null);
        if(customer==null){
            response1.put("message","incorrect customer id "+customerDto.getCustomerId()+", please enter the valid id!");
            response.put("isSuccess",false);
            response.put("message",response1);
            return  response;
        }else{
            customerRepository.deleteById(customerDto.getCustomerId());
            response1.put("message","customer deleted successfully! "+customerDto.getCustomerId());
            response.put("isSuccess",true);
            response.put("message",response1);
            return  response;
        }
    }

}
