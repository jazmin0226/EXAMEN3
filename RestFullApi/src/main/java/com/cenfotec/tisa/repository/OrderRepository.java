package com.cenfotec.tisa.repository;

import com.cenfotec.tisa.model.Customer;
import com.cenfotec.tisa.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query(value = "SELECT * FROM ORDERS INNER JOIN PRODUCT ON ORDERS.PRODUCT_ID = PRODUCT.ID WHERE PRODUCT.product = :item", nativeQuery = true)
    public List<Orders> findByItem(String item);

    @Query(value= "SELECT * FROM ORDERS INNER JOIN CUSTOMER ON ORDERS.CUSTOMER_ID = CUSTOMER.ID WHERE  CUSTOMER.ID = :customer AND ORDERS.STATE = TRUE", nativeQuery = true)
    public List<Orders> findByCustomerForDelete (long customer);
}
