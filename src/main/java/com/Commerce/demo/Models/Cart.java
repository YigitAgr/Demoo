package com.Commerce.demo.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Carts")
public class Cart extends Base{


    //One cart can have multiple orders . One cart has one customer
    @OneToOne
    @JoinColumn(name = "customer_id",unique = true)
    private Customer customer;


    @OneToMany(mappedBy = "cart" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Order> orders;
}
