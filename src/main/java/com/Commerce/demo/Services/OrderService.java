package com.Commerce.demo.Services;

import com.Commerce.demo.Dto.OrderDto;
import com.Commerce.demo.Models.Cart;
import com.Commerce.demo.Models.CartItem;
import com.Commerce.demo.Models.Enums.OrderStatus;
import com.Commerce.demo.Models.Order;
import com.Commerce.demo.Models.Product;
import com.Commerce.demo.Repositorys.CartRepository;
import com.Commerce.demo.Repositorys.OrderRepository;
import com.Commerce.demo.Repositorys.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(CartRepository cartRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Cart placeOrder(int customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null && !cart.getCartItems().isEmpty()) { // Ensure cart is not empty
            // Create a new order
            Order order = new Order();

            // Calculate total price considering quantities
            BigDecimal totalPrice = BigDecimal.ZERO;

            for (CartItem cartItem : cart.getCartItems()) {
                Product product = cartItem.getProduct();
                int quantity = cartItem.getQuantity();
                totalPrice = totalPrice.add(product.getProductPrice().multiply(BigDecimal.valueOf(quantity)));
            }

            order.setTotalPrice(totalPrice); // Set the total price calculated
            order.setCart(cart);
            order.setStatus(OrderStatus.PENDING); // Default status

            // Add all products from the cart to the order with their respective quantities
            for (CartItem cartItem : cart.getCartItems()) {
                order.addProduct(cartItem.getProduct(), cartItem.getQuantity()); // Assuming addProduct accepts Product and quantity
            }

            orderRepository.save(order);

            // Clear the cart after placing the order
            cart.getCartItems().clear(); // Remove items from the cart
            cart.setTotalPrice(BigDecimal.ZERO); // Reset total price
            cartRepository.save(cart); // Persist changes to the cart

            return cart;
        } else {
            throw new IllegalArgumentException("Cart not found or is empty.");
        }
    }


    public Order getOrderForCode(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));
    }


}
