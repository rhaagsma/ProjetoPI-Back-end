package com.example.siteDiscoBackend.Order;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record OrderRequestDTO(@NotNull UUID user, @NotNull List<OrderItemDTO> items) {
}
