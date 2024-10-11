package com.Commerce.demo.Dto;

import com.Commerce.demo.Models.Cart;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartDto {
    private int id;
    private int customerId;
    private List<Integer> orderIds;
    private List<Integer> productIds;

    public Cart toEntity() {
        Cart cart = new Cart();
        cart.setId(this.id);
        return cart;
    }

    public static CartDto fromEntity(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        if (cart.getCustomer() != null) {
            cartDto.setCustomerId(cart.getCustomer().getId());
        }
        if (cart.getProducts() != null) { // Change to products
            cartDto.setProductIds(cart.getProducts().stream()
                    .map(product -> product.getId())
                    .collect(Collectors.toList()));
        }
        return cartDto;
    }

    public void validate() {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Valid customer ID is required");
        }
    }
}
