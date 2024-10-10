package com.Commerce.demo.Repositorys;

import com.Commerce.demo.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByCustomerId(int customerId);
}
