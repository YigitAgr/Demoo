package com.Commerce.demo.Dto;

import com.Commerce.demo.Models.Customer;
import lombok.Data;

@Data
public class CustomerDto {
    private int id;
    private String customerName;
    private String customerPassword;  // Include password


    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setCustomerName(this.customerName);
        customer.setCustomerPassword(this.customerPassword);
        return customer;
    }


    public static CustomerDto fromEntity(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setCustomerName(customer.getCustomerName());
        //only added for postman test
        customerDto.setCustomerPassword(customer.getCustomerPassword());
        return customerDto;
    }

    public void validate() {
        if (customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (customerPassword == null || customerPassword.isEmpty()) {
            throw new IllegalArgumentException("Customer password is required");
        }
    }

}
