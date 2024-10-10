package com.Commerce.demo.Repositorys;

import com.Commerce.demo.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
