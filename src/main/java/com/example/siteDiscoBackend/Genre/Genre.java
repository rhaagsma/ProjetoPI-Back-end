package com.example.siteDiscoBackend.Genre;

import com.example.siteDiscoBackend.Band.Band;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name="genres")
@Entity(name="genres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")

public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Band> bands = new HashSet<>();

    //getters e setters do lombok

    public Genre(GenreRequestDTO data){
        this.name = data.name();
    }
}
