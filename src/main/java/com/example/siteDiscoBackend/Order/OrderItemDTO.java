package com.example.siteDiscoBackend.Order;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemDTO(@NotNull UUID productId, @NotNull int quantity) {
}
