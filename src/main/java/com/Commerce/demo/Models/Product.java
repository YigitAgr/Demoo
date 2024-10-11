package com.Commerce.demo.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Products")
public class Product extends Base{


    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private int productStock;

    //one product can have multiple orders but order can only have one product
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();


    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Cart> carts = new ArrayList<>();


    //taken from internet to escapa toString error
    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }

}
