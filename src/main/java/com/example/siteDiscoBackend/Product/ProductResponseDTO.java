package com.example.siteDiscoBackend.Product;

import com.example.siteDiscoBackend.Band.Band;
import com.example.siteDiscoBackend.Category.Category;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProductResponseDTO(UUID id, String name, String image, String description, float price, List<UUID> bands, UUID category) {
    public ProductResponseDTO(Product product){
        this(product.getId(), product.getName(), product.getImage(), product.getDescription(), product.getPrice(),
                product.getBands().stream().map(Band::getId).collect(Collectors.toList()),
                product.getCategory() != null ? product.getCategory().getId() : null);//acessa para checar se há ids de banda relacionado ao produto
    }
}
