package com.Commerce.demo.Services;

import com.Commerce.demo.Dto.CartDto;
import com.Commerce.demo.Models.Cart;
import com.Commerce.demo.Repositorys.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartDto GetCart(int customerId) {

        Cart cart = cartRepository.findByCustomerId(customerId);
        if(cart != null) {
            return CartDto.fromEntity(cart);
        }else {
            return null;
        }

    }
}
