package com.Commerce.demo.Controllers;

import com.Commerce.demo.Dto.CustomerDto;
import com.Commerce.demo.Models.Customer;
import com.Commerce.demo.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }



    @PostMapping("/addCustomer")
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto response = customerService.AddCustomer(customerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
