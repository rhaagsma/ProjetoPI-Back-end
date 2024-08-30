package com.example.siteDiscoBackend.Showcase;

import com.example.siteDiscoBackend.Product.Product;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ShowcaseResponseDTO(UUID id, String name, List<UUID> products) {
    public ShowcaseResponseDTO(Showcase showcase){
        this(showcase.getId(), showcase.getName(),
                showcase.getProducts().stream().map(Product::getId).collect(Collectors.toList()));
    }
}
