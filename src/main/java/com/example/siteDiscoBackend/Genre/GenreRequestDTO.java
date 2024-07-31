package com.example.siteDiscoBackend.Genre;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record GenreRequestDTO(@NotNull String name, List<UUID> bands) {
}
