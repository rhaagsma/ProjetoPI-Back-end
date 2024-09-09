package com.example.siteDiscoBackend.Band;

import com.example.siteDiscoBackend.Genre.Genre;
import com.example.siteDiscoBackend.Product.Product;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record BandResponseDTO(UUID id, String name, String image, String description, List<UUID> genres, List<UUID> products){
    public BandResponseDTO(Band band){
        this(band.getId(), band.getName(), band.getImage(), band.getDescription(),
                band.getGenres().stream().map(Genre::getId).collect(Collectors.toList()),
                band.getProducts().stream().map(Product::getId).collect(Collectors.toList()));
    }
}
