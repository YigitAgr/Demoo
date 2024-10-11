package com.Commerce.demo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Customers")
public class Customer extends Base{

    private String customerName;
    private String customerPassword;

    @JsonBackReference
    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

}
