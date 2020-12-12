package com.tisa.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.tisa.graphql.entities.Customer;
import com.tisa.graphql.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMutation implements GraphQLMutationResolver {

    @Autowired
    private CustomerService customerService;

    public Customer createCustomer(String name, String lastname, String address, String billingAddress, String cardNumber, int month, int year) {
        return this.customerService.createCustomer(name, lastname, address, billingAddress, cardNumber, month, year);
    }


    public boolean deleteCustomer(int id) {
        return this.customerService.deleteCustomer(id);
    }


    public Customer updateCustomer(int id, String name, String lastname, String address, String billingAddress, String cardNumber, int month, int year) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setLastname(lastname);
        customer.setAddress(address);
        customer.setBillingAddress(billingAddress);
        customer.setCardNumber(cardNumber);
        customer.setMonth(month);
        customer.setYear(year);

        return this.customerService.updateCustomer(customer);
    }
}
