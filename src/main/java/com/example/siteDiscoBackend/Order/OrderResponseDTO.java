package com.example.siteDiscoBackend.Order;

import com.example.siteDiscoBackend.Band.Band;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record OrderResponseDTO(UUID id, float totalPrice, LocalDateTime date, UUID user, List<OrderItemDTO> items) {
    public OrderResponseDTO(Order order){
        this(order.getId(), order.getTotalPrice(), order.getDate(), order.getUser() != null ? order.getUser().getId() : null,
                order.getItems().stream().map(item -> new OrderItemDTO(
                        item.getProduct().getId(), item.getQuantity()
                )).collect(Collectors.toList()));
    }
}
