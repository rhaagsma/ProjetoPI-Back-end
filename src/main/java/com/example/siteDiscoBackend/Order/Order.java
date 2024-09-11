package com.example.siteDiscoBackend.Order;

import com.example.siteDiscoBackend.Address.Address;
import com.example.siteDiscoBackend.Product.Product;
import com.example.siteDiscoBackend.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name="orders")
@Entity(name="orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private float totalPrice;
    private LocalDateTime date;

    public Order(OrderRequestDTO data, User user, Address address){
        this.user = user;
        this.totalPrice = data.totalPrice();
        this.date = data.date();
        this.address = address;
    }

    public void addProduct(Product product, int quantity){
        OrderItem item = new OrderItem(this, product, quantity);
        this.items.add(item);
    }
}
