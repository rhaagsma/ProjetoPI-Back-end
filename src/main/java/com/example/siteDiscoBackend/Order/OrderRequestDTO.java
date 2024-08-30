package com.example.siteDiscoBackend.Order;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderRequestDTO(@NotNull UUID user, float totalPrice,
                              LocalDateTime date, @NotNull List<OrderItemDTO> items) {
}
