package com.example.siteDiscoBackend.Order;

import java.util.UUID;

public record OrderResponseDTO(UUID id, UUID user) {
    public OrderResponseDTO(Order order){
        this(order.getId(), order.getUser() != null ? order.getUser().getId() : null);
    }
}
