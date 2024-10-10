package com.Commerce.demo.Repositorys;

import com.Commerce.demo.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
