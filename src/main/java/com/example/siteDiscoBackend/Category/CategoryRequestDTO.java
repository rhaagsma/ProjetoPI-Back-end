package com.example.siteDiscoBackend.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CategoryRequestDTO(@NotBlank @Size(min=2) String name, List<UUID> products) {
}
