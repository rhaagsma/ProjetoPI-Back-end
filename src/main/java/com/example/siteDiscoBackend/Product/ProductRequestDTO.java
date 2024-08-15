package com.example.siteDiscoBackend.Product;

import com.example.siteDiscoBackend.Category.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record ProductRequestDTO(@NotBlank @Size(min=2) String name, String image, String description, float price, int quantity,
                                List<UUID> bands, UUID category) {
}
