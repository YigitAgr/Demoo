package com.Commerce.demo.Models;


import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
public class Base {

    private int id;
    private Date createdAt;
    private Date updatedAt;
}
