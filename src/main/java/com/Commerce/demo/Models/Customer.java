package com.Commerce.demo.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Customers")
public class Customer extends Base{

    private String customerName;
    private String customerPassword;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

}
