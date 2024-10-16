package com.Commerce.demo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "Carts")
public class Cart extends Base {

    private BigDecimal totalPrice;

    @OneToOne
    @JoinColumn(name = "customer_id", unique = true)
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Order> orders;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();


    @Override
    public String toString() {
        return "Cart{" +
                "customerId=" + (customer != null ? customer.getId() : "null") +
                ", productCount=" + cartItems.size() +
                '}';
    }

    public void addProduct(Product product, int quantity) {
        if (product.getProductStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getProductName());
        }

        // Check if the product is already in the cart
        CartItem cartItem = cartItems.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            // Update existing quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Create new CartItem
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(this);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            cartItems.add(newCartItem);
        }

        // Update stock
        product.setProductStock(product.getProductStock() - quantity);
        calculateTotalPrice();
    }

    public void removeProduct(Product product) {
        CartItem cartItem = cartItems.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            // Restore stock
            product.setProductStock(product.getProductStock() + cartItem.getQuantity());
            // Remove from cart
            cartItems.remove(cartItem);
            calculateTotalPrice();
        }
    }

    public void calculateTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();
            totalPrice = totalPrice.add(product.getProductPrice().multiply(BigDecimal.valueOf(quantity)));
        }
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            products.add(cartItem.getProduct());
        }
        return products;
    }
}

