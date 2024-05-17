package com.example.siteDiscoBackend.Genre;

import com.example.siteDiscoBackend.Band.Band;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record GenreResponseDTO(UUID id, String name, List<UUID> bands) {
    public GenreResponseDTO(Genre genre){
        this(genre.getId(), genre.getName(),
                genre.getBands().stream().map(Band::getId).collect(Collectors.toList()));
    }
}
