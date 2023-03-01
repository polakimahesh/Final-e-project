package com.javaproject.Ecommerce.Products;


import com.javaproject.Ecommerce.DTO.GetAvailabilityDto;
import com.javaproject.Ecommerce.DTO.ProductDto;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
@Service
public class ProductService {
    @Autowired
     private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public HashMap<String,Object> createProduct(ProductDto productDto){
        Product product = new Product();
        HashMap<String,Object> response = new HashMap<>();
        HashMap<String,Object> response1= new HashMap<>();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        productRepository.save(product);
        response1.put("message","Created product Successfully!");
        response.put("isSuccess",true);
        response.put("message",response1);
        return  response;
    }
    public HashMap<String, Object> getSingleProduct(int id){
        var product= productRepository.findById(id);
        HashMap<String,Object> response = new HashMap<>();
        HashMap<String,Object> response1= new HashMap<>();
        if(!product.isPresent()){
            response1.put("message","incorrect product id "+id+", please enter the valid id!");
            response.put("isSuccess",false);
            response.put("message",response1);
            return  response;
        }
        response.put("message",product);
        return response;
    }

    public HashMap<String,Object> updateProduct(ProductDto productDto){
        HashMap<String,Object> response = new HashMap<>();
        var product = productRepository.findById(productDto.getProductId()).orElse(null);
        if(product==null){
            HashMap<String,Object> response1= new HashMap<>();
            response1.put("message","incorrect customer id "+productDto.getProductId()+", please enter the valid id!");
            response.put("isSuccess",false);
            response.put("message",response1);
            return  response;
        }else{
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            product.setDescription(productDto.getDescription());
            response.put("isSuccess",true);
            response.put("message",product);
            productRepository.save(product);
            return  response;
        }
    }
    public HashMap<String,Object> deleteProduct(ProductDeleteId productDto){
        HashMap<String,Object> response = new HashMap<>();
        HashMap<String,Object> response1= new HashMap<>();
        var product= productRepository.findById(productDto.getProductId()).orElse(null);
        if(product==null){
            response1.put("message","incorrect product id "+productDto.getProductId()+", please enter the valid id!");
            response.put("isSuccess",false);
            response.put("message",response1);
            return  response;
        }
        response1.put("message","product deleted successfully! "+productDto.getProductId());
        productRepository.deleteById(productDto.getProductId());
        response.put("isSuccess",true);
        response.put("message",response1);
        return  response;
    }

    public Boolean productsAvailabilityCheck(GetAvailabilityDto getAvailabilityDto ) {
        Product product = productRepository.findById(getAvailabilityDto.getProductId()).orElse(null);
        if (product == null) {
            return false;
        }
        else {
            if (product.getQuantity() == 0 || product.getQuantity() < getAvailabilityDto.getQuantity()) {
                return false;
            } else {
                return  true;
            }
        }
    }

}
