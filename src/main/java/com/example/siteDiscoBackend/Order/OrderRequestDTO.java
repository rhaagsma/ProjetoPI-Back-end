package com.example.siteDiscoBackend.Order;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderRequestDTO(@NotNull UUID user) {
}
