package com.example.siteDiscoBackend.Showcase;

import com.example.siteDiscoBackend.Product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name="showcases")
@Entity(name="showcases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Showcase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @ManyToMany
    @JoinTable(
            name="showcase_product",
            joinColumns = @JoinColumn(name="showcase_id"),
            inverseJoinColumns = @JoinColumn(name="product_id")
    )
    private Set<Product> products = new HashSet<>();

    public Showcase(ShowcaseRequestDTO data){
        this.name = data.name();
    }

    public void addProduct(Product product){
        this.products.add(product);
    }
}
