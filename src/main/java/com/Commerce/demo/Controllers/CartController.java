package com.Commerce.demo.Controllers;

import com.Commerce.demo.Dto.CartDto;
import com.Commerce.demo.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/v1/cart")
public class CartController {


        private CartService cartService;

        @Autowired
        public CartController( CartService cartService ) {
            this.cartService = cartService;
        }


        @GetMapping("/getCart")
        public ResponseEntity<CartDto> getCart(@RequestParam int customerId) {
            CartDto response = cartService.GetCart(customerId);

            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
}
