package com.Commerce.demo.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Products")
public class Product extends Base{


    private String productName;
    private String productDescription;
    private String productPrice;
    private String productStock;

    //one product can have multiple orders but order can only have one product
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Order> order;
}
