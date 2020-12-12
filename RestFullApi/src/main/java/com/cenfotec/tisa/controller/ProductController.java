package com.cenfotec.tisa.controller;

import com.cenfotec.tisa.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/products"})
public class ProductController {
    private ProductRepository repository;

    ProductController(ProductRepository productRepository) {
        this.repository = productRepository;
    }
    @GetMapping
    public List findAll(){
        return repository.findAll();
    }

}
