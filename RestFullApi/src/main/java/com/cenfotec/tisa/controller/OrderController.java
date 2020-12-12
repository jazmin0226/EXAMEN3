package com.cenfotec.tisa.controller;

import com.cenfotec.tisa.model.Customer;
import com.cenfotec.tisa.model.Orders;
import com.cenfotec.tisa.model.Product;
import com.cenfotec.tisa.repository.CustomerRepository;
import com.cenfotec.tisa.repository.OrderRepository;
import com.cenfotec.tisa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/orders"})
public class OrderController {
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    OrderController(OrderRepository orderRepository ) {
        this.repository = orderRepository;
    }

    @GetMapping
    public List findAll() {
        List<Orders> orders = repository.findAll();
        try {
            return orders;
        }catch (Exception e){
           System.out.println("\u001B[31m"+ e + "\u001B[31m");
        }
        return orders;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Orders> findById(@PathVariable long id) {
        return repository.findById(id)
                .map(record ->
                        ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/item",  method = RequestMethod.GET)
    public List findByItem(@RequestBody String item) {
        return repository.findByItem(item);
    }

    @PostMapping
    public Orders create(@RequestBody Orders orders){
        Orders createdOrder = repository.save(orders);
        Orders order = updateOrderGeneration(createdOrder);
        order.setState(true);

        return repository.save(order);
    }

    public Orders updateOrderGeneration(Orders orders){
        long productId = orders.getProduct().getId();
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()){
            int price = product.get().getPrice();
            orders.setTotal(price * orders.getQuantity());
        }
        return orders;
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Orders>
    update(@PathVariable("id") long id, @RequestBody Orders orders){

        return repository.findById(id)
                .map(record -> {
                    if (orders.getProduct() == null){
                        record.setQuantity(orders.getQuantity());

                    }else if(orders.getQuantity() == 0){
                        record.setProduct(orders.getProduct());

                    }else{
                        record.setProduct(orders.getProduct());
                        record.setQuantity(orders.getQuantity());
                    }

                    Orders updated =  updateOrderGeneration(record);
                    Orders finalUpdate = repository.save(updated);

                    return ResponseEntity.ok().body(finalUpdate);
                }).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/state/{id}",  method = RequestMethod.PUT)
    public ResponseEntity<?> updateState(@PathVariable("id") int id) {
        return repository.findById(new Long (id))
                .map(record -> {
                    record.setState(false);
                    repository.save(record);
                    return ResponseEntity.ok().body(record);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id")long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
