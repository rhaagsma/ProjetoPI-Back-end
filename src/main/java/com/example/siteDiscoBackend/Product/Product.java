package com.example.siteDiscoBackend.Product;

import com.example.siteDiscoBackend.Band.Band;
import com.example.siteDiscoBackend.Category.Category;
import com.example.siteDiscoBackend.Genre.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name="products")
@Entity(name="products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String image;
    private String description;
    private float price;

    private int quantity;

    @ManyToMany
    @JoinTable(
            name = "product_band",
            joinColumns = @JoinColumn(name="product_id"),
            inverseJoinColumns = @JoinColumn(name="band_id")
    )
    private Set<Band> bands = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(ProductRequestDTO data, Category category){
        this.name = data.name();
        this.image = data.image();
        this.description = data.description();
        this.price = data.price();
        this.quantity = data.quantity();
        this.category = category;
    }

}
