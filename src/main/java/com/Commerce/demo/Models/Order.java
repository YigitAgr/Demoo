package com.Commerce.demo.Models;

import com.Commerce.demo.Models.Enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "Orders")
public class Order extends Base {

    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonManagedReference
    private Cart cart;

    @ElementCollection
    @CollectionTable(name = "order_product_quantities", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> productQuantities = new HashMap<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        // Update product quantity
        productQuantities.merge(product, quantity, Integer::sum); // Increment the quantity
        calculateTotalPrice(); // Recalculate total price
    }

    public void calculateTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : productQuantities.entrySet()) {
            totalPrice = totalPrice.add(entry.getKey().getProductPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
    }
}
