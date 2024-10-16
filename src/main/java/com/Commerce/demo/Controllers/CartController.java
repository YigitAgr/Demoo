package com.Commerce.demo.Controllers;

import com.Commerce.demo.Dto.CartDto;
import com.Commerce.demo.Dto.OrderDto;
import com.Commerce.demo.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/getCart")
    public ResponseEntity<CartDto> getCart(@RequestParam int customerId) {
        CartDto response = cartService.getCart(customerId); // Changed GetCart to getCart

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateCart")
    public ResponseEntity<CartDto> updateCart(@RequestParam int customerId, @RequestBody CartDto cartDto) {
        CartDto updatedCartDto = cartService.updateCart(customerId, cartDto);
        return new ResponseEntity<>(updatedCartDto, HttpStatus.OK);
    }

    @DeleteMapping("/emptyCart") // Use DELETE for this action
    public ResponseEntity<CartDto> emptyCart(@RequestParam int customerId) {
        CartDto emptiedCart = cartService.emptyCart(customerId);
        return new ResponseEntity<>(emptiedCart, HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<CartDto> addProductToCart(
            @RequestParam int customerId,
            @RequestParam int productId,
            @RequestParam int quantity) {
        CartDto updatedCart = cartService.addProductToCart(customerId, productId, quantity);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<CartDto> removeProductFromCart(@RequestParam int customerId, @RequestParam int productId) {
        CartDto updatedCart = cartService.removeProductFromCart(customerId, productId);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderDto>> getAllOrdersForCustomer(@RequestParam int customerId) {
        List<OrderDto> orders = cartService.getAllOrdersForCustomer(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
