package com.cenfotec.tisa.repository;

import com.cenfotec.tisa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

