package com.example.siteDiscoBackend.Order;

import com.example.siteDiscoBackend.Band.Band;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record OrderResponseDTO(UUID id, UUID user, List<UUID> items) {
    public OrderResponseDTO(Order order){
        this(order.getId(), order.getUser() != null ? order.getUser().getId() : null,
                order.getItems().stream().map(OrderItem::getId).collect(Collectors.toList()));
    }
}
