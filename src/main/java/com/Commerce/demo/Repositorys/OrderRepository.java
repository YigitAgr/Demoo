package com.Commerce.demo.Repositorys;

import com.Commerce.demo.Models.Cart;
import com.Commerce.demo.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCart(Cart cart);
    Optional<Order> findById(Integer id);
}
