package com.example.siteDiscoBackend.Order;

import com.example.siteDiscoBackend.Product.Product;
import com.example.siteDiscoBackend.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name="orders")
@Entity(name="orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(OrderRequestDTO data, User user){
        this.user = user;
    }

    public void addProduct(Product product, int quantity){
        OrderItem item = new OrderItem(this, product, quantity);
        this.items.add(item);
    }
}
