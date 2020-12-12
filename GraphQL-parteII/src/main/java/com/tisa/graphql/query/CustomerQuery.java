package com.tisa.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.tisa.graphql.entities.Customer;
import com.tisa.graphql.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class CustomerQuery implements GraphQLQueryResolver {

    @Autowired
    private CustomerService customerService;

    public List<Customer> getCustomers(int count) {
        return this.customerService.getAllCustomers(count);
    }

    public Optional<Customer> getCustomer(int id) {
        return this.customerService.getCustomer(id);
    }
}