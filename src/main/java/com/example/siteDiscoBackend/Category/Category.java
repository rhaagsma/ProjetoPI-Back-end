package com.example.siteDiscoBackend.Category;

//import com.example.siteDiscoBackend.Product.Product;
import com.example.siteDiscoBackend.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name="categories")
@Entity(name="categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")


public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();

    public Category(CategoryRequestDTO data){
        this.name = data.name();
    }
}
