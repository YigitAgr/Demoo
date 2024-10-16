package com.Commerce.demo.Services;

import com.Commerce.demo.Dto.CartDto;
import com.Commerce.demo.Dto.OrderDto;
import com.Commerce.demo.Models.Cart;
import com.Commerce.demo.Models.CartItem;
import com.Commerce.demo.Models.Order;
import com.Commerce.demo.Models.Product;
import com.Commerce.demo.Repositorys.CartRepository;
import com.Commerce.demo.Repositorys.OrderRepository;
import com.Commerce.demo.Repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public CartDto getCart(int customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            cart.calculateTotalPrice();
            return CartDto.fromEntity(cart);
        } else {
            return null;
        }
    }

    public CartDto updateCart(int customerId, CartDto cartDto) {
        Cart cart = cartRepository.findByCustomerId(customerId);

        if (cart != null) {
            cart.getCartItems().clear(); // Clear existing cart items

            for (Integer productId : cartDto.getProductIds()) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found. "));

                // Assuming you want to add the product with a default quantity of 1
                cart.addProduct(product, 1);
            }
            cart.calculateTotalPrice();
            cartRepository.save(cart);
            return CartDto.fromEntity(cart);
        } else {
            return null;
        }
    }

    public CartDto emptyCart(int customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            for (CartItem cartItem : cart.getCartItems()) {
                Product product = cartItem.getProduct();
                int quantity = cartItem.getQuantity();
                product.setProductStock(product.getProductStock() + quantity);
            }

            cart.getCartItems().clear();
            cart.setTotalPrice(BigDecimal.ZERO);
            cartRepository.save(cart);
            return CartDto.fromEntity(cart);
        } else {
            return null;
        }
    }

    public CartDto addProductToCart(int customerId, int productId, int quantity) {
        if (quantity <= 0) {
            //its check the quantity of the product but when ı call this method in postman it doesnt return this.
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found. "));
            cart.addProduct(product, quantity);
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
            cart.removeProduct(product);
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

    private int getProductQuantityInCart(Cart cart, Product product) {
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProduct().equals(product)) {
                return cartItem.getQuantity();
            }
        }
        return 0; // Return 0 if the product is not found in the cart
    }
}
