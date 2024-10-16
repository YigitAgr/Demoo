package com.Commerce.demo.Dto;

import com.Commerce.demo.Models.Order;
import com.Commerce.demo.Models.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class OrderDto {
    private int id;
    private BigDecimal totalPrice;
    private List<Integer> productIds;
    private int cartId;

    public Order toEntity() {
        Order order = new Order();
        order.setId(this.id);
        order.setTotalPrice(this.totalPrice);
        return order;
    }

    public static OrderDto fromEntity(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalPrice(order.getTotalPrice());

        // Use the productQuantities map to get product IDs
        if (order.getProductQuantities() != null && !order.getProductQuantities().isEmpty()) {
            List<Integer> productIds = order.getProductQuantities().keySet().stream()
                    .map(Product::getId)
                    .collect(Collectors.toList());
            orderDto.setProductIds(productIds);
        }

        if (order.getCart() != null) {
            orderDto.setCartId(order.getCart().getId());
        }
        return orderDto;
    }
}
