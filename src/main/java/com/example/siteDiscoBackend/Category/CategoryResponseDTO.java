package com.example.siteDiscoBackend.Category;

import com.example.siteDiscoBackend.Product.Product;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record CategoryResponseDTO(UUID id, String name, List<UUID> products) {
    public CategoryResponseDTO(Category category){
        this(category.getId(), category.getName()
                , category.getProducts().stream().map(Product::getId).collect(Collectors.toList())
        );
    }
}
