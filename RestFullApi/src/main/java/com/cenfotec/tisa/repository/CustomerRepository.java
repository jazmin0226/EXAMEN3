package com.cenfotec.tisa.repository;

import com.cenfotec.tisa.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {
    List<Customer> findByLastnameLike(String like);
    List<Customer> findByBillingAddressLike(String like);



}
