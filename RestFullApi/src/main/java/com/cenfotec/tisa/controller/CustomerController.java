package com.cenfotec.tisa.controller;

import com.cenfotec.tisa.model.Customer;
import com.cenfotec.tisa.model.Orders;
import com.cenfotec.tisa.repository.CustomerRepository;
import com.cenfotec.tisa.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/customers"})
public class CustomerController {
    private CustomerRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    CustomerController(CustomerRepository contactRepository) {
        this.repository = contactRepository;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Customer> findById(@PathVariable long id){
        return repository.findById(id)
                .map(record ->
                        ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/lastname",  method = RequestMethod.GET)
    public List findByLastname(@RequestBody String lastname) {
        String filter = "%"+ lastname + "%";
        return repository.findByLastnameLike(filter);
    }

    @RequestMapping(value = "/billing",  method = RequestMethod.GET)
    public List findByBillingAddress(@RequestBody String billingAddress) {
        String filter = "%"+billingAddress+"%";
        return repository.findByBillingAddressLike(filter);
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer){
        return repository.save(customer);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Customer>
    update(@PathVariable("id") long id,
           @RequestBody Customer customer){
        return repository.findById(id)
                .map(record -> {
                    record.setName(customer.getName());
                    record.setLastname(customer.getLastname());
                    record.setAddress(customer.getAddress());
                    record.setBillingAddress(customer.getBillingAddress());
                    record.setCardNumber(customer.getCardNumber());
                    record.setMonth(customer.getMonth());
                    record.setYear(customer.getYear());

                    Customer updated = repository.save(record);

                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id")long id) {
        List<Orders> ordersList = orderRepository.findByCustomerForDelete(id);

        if (ordersList.size() == 0){
            repository.findById(id).map(record -> {
                repository.deleteById(id);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
        }else{
            return ResponseEntity.ok().body("El cliente que desea eliminar tiene ordenes activas. No se puede eliminar.");
        }
        return ResponseEntity.ok().body("El cliente que desea eliminar tiene ordenes activas. No se puede eliminar.");
    }
}
