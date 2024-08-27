package com.example.siteDiscoBackend.User;

import com.example.siteDiscoBackend.Order.Order;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record UserResponseDTO(UUID id, String login, String email, String telephone, String password, UserRole role, List<UUID> orders) {
    public UserResponseDTO(User data){
        this(data.getId(), data.getLogin(), data.getEmail(), data.getTelephone(), data.getPassword(), data.getRole(),
                data.getOrders().stream().map(Order::getId).collect(Collectors.toList()));
    }
}
