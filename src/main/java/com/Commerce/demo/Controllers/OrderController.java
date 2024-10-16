package com.Commerce.demo.Controllers;

import com.Commerce.demo.Dto.OrderDto;
import com.Commerce.demo.Models.Cart;
import com.Commerce.demo.Models.Order;
import com.Commerce.demo.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Cart> placeOrder(@RequestParam int customerId) {
            Cart updatedCart = orderService.placeOrder(customerId);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);

    }

    @GetMapping("/getOrderById")
    public ResponseEntity<Order> getOrder(@RequestParam Integer orderId) {
        Order order = orderService.getOrderForCode(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
