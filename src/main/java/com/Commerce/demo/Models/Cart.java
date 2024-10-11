package com.Commerce.demo.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Carts")
public class Cart extends Base{

    private BigDecimal totalPrice;

    //One cart can have multiple orders . One cart has one customer
    @OneToOne
    @JoinColumn(name = "customer_id",unique = true)
    private Customer customer;


    @OneToMany(mappedBy = "cart" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Order> orders;


    //Remove the cascade for excape to infinite toString error.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();


    //taken from internet to escapa toString error
    @Override
    public String toString() {
        return "Cart{" +
                "customerId=" + (customer != null ? customer.getId() : "null") +
                ", productCount=" + products.size() +
                '}';
    }



    public void calculateTotalPrice() {
        totalPrice = products.stream()
                .map(Product::getProductPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
