package com.example.siteDiscoBackend.Showcase;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ShowcaseRequestDTO(String name, @NotNull List<UUID> products) {
}
