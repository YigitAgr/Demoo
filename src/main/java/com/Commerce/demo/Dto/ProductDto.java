package com.Commerce.demo.Dto;

import com.Commerce.demo.Models.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private int id;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private int productStock;

    public Product toEntity() {
        Product product = new Product();
        product.setProductName(this.productName);
        product.setProductDescription(this.productDescription);
        product.setProductPrice(this.productPrice);
        product.setProductStock(this.productStock);
        return product;
    }

    public static ProductDto fromEntity(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setProductDescription(product.getProductDescription());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setProductStock(product.getProductStock());
        return productDto;
    }


    public void validate() {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (productPrice == null || productPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be a positive number");
        }
        if (productStock < 0) {
            throw new IllegalArgumentException("Product stock must be a non-negative integer");
        }
    }
}
