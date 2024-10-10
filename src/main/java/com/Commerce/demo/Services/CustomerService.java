package com.Commerce.demo.Services;

import com.Commerce.demo.Dto.CustomerDto;
import com.Commerce.demo.Models.Cart;
import com.Commerce.demo.Models.Customer;
import com.Commerce.demo.Repositorys.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;


    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto AddCustomer(CustomerDto customerDto) {
        customerDto.validate();
        Customer customer = customerDto.toEntity();
        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);

        customerRepository.save(customer);

        return CustomerDto.fromEntity(customer);
    }
}
