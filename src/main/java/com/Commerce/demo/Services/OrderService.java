package com.Commerce.demo.Services;

import com.Commerce.demo.Dto.OrderDto;
import com.Commerce.demo.Models.Cart;
import com.Commerce.demo.Models.Order;
import com.Commerce.demo.Models.Product;
import com.Commerce.demo.Repositorys.CartRepository;
import com.Commerce.demo.Repositorys.OrderRepository;
import com.Commerce.demo.Repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private CartRepository cartRepository;
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    @Autowired
    public OrderService(CartRepository cartRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Cart placeOrder(int customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null || cart.getProducts().isEmpty()) {
            // Create a new order
            Order order = new Order();
            order.setTotalPrice(cart.getProducts().stream()
                    .map(Product::getProductPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)); // Calculate total price

            order.setCart(cart);

            // Add all products from the cart to the order
            for (Product product : cart.getProducts()) {
                order.addProduct(product);
            }


            orderRepository.save(order);

            cart.getProducts().clear();
            cart.setTotalPrice(BigDecimal.ZERO);
            cartRepository.save(cart);

            return cart;
        }else{
            throw new IllegalArgumentException("Cart not found .");
        }

    }
}

