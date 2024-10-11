package com.Commerce.demo.Services;

import com.Commerce.demo.Dto.CartDto;
import com.Commerce.demo.Dto.OrderDto;
import com.Commerce.demo.Models.Cart;
import com.Commerce.demo.Models.Order;
import com.Commerce.demo.Models.Product;
import com.Commerce.demo.Repositorys.CartRepository;
import com.Commerce.demo.Repositorys.OrderRepository;
import com.Commerce.demo.Repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Autowired
    public CartService(CartRepository cartRepository,ProductRepository productRepository,OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public CartDto GetCart(int customerId) {

        Cart cart = cartRepository.findByCustomerId(customerId);
        if(cart != null) {
            cart.calculateTotalPrice();
            return CartDto.fromEntity(cart);
        }else {
            return null;
        }

    }

    public CartDto updateCart(int customerId, CartDto cartDto) {
        Cart cart = cartRepository.findByCustomerId(customerId);

        if (cart != null) {
            cart.getProducts().clear();

            for (Integer productId : cartDto.getProductIds()) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found. "));

                cart.getProducts().add(product);
            }
            cart.calculateTotalPrice();
            cartRepository.save(cart);
            System.out.println("Updated Cart: " + cart);

            return CartDto.fromEntity(cart);
        } else {
            System.out.println("Cart not found for customer ID: " + customerId);
            return null;
        }
    }


    public CartDto emptyCart(int customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            cart.getProducts().clear();
            cart.setTotalPrice(BigDecimal.ZERO);
            cartRepository.save(cart);
            return CartDto.fromEntity(cart);
        } else {
            return null;
        }
    }

    public CartDto addProductToCart(int customerId, int productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

            cart.addProduct(product, quantity);
            cart.calculateTotalPrice();
            cartRepository.save(cart);
            return CartDto.fromEntity(cart);
        } else {
            return null;
        }
    }



    public CartDto removeProductFromCart(int customerId, int productId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: "));
            cart.getProducts().remove(product);
            cart.calculateTotalPrice();
            cartRepository.save(cart);
            return CartDto.fromEntity(cart);
        } else {
            return null;
        }
    }



    public List<OrderDto> getAllOrdersForCustomer(int customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);

        if (cart != null) {
            List<Order> orders = orderRepository.findByCart(cart);
            return orders.stream()
                    .map(OrderDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }



}
