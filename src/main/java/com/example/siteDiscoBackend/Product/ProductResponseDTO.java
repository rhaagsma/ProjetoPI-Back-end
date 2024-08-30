package com.example.siteDiscoBackend.Product;

import com.example.siteDiscoBackend.Band.Band;
import com.example.siteDiscoBackend.Category.Category;
import com.example.siteDiscoBackend.Showcase.Showcase;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProductResponseDTO(UUID id, String name, String image, String description, float price, int quantity, List<UUID> bands, UUID category, List<UUID> inShowcases) {
    public ProductResponseDTO(Product product){
        this(product.getId(), product.getName(), product.getImage(), product.getDescription(), product.getPrice(), product.getQuantity(),
                product.getBands().stream().map(Band::getId).collect(Collectors.toList()),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getInShowcases().stream().map(Showcase::getId).collect(Collectors.toList()));//acessa para checar se há ids de banda relacionado ao produto
    }
}
