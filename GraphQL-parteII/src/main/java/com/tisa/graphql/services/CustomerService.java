package com.tisa.graphql.services;


import com.tisa.graphql.entities.Customer;
import com.tisa.graphql.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepo;

    //GET ALL
    public List<Customer> getAllCustomers(int count){
        return this.customerRepo.findAll().stream().limit(count).collect(Collectors.toList());
    }

    //GET BY ID
    public Optional<Customer> getCustomer(int id){
        return this.customerRepo.findById(id);
    }

    //POST
    public Customer createCustomer(String name, String lastname, String address, String billingAddress, String cardNumber, int month, int year){
        Customer customer = new Customer();
        customer.setName(name);
        customer.setLastname(lastname);
        customer.setAddress(address);
        customer.setBillingAddress(billingAddress);
        customer.setCardNumber(cardNumber);
        customer.setMonth(month);
        customer.setYear(year);

        return this.customerRepo.save(customer);
    }

    //PUT
    public Customer updateCustomer(Customer customer){
        return this.customerRepo.save(customer);
    }



    //DELETE
    public boolean deleteCustomer(int id){
        this.customerRepo.deleteById(id);
        return true;
    }
}
