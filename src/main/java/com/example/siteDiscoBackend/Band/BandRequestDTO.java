package com.example.siteDiscoBackend.Band;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record BandRequestDTO(@NotNull @Size(min=2) String name, String image, String description, List<UUID> genres, List<UUID> products) {
}
