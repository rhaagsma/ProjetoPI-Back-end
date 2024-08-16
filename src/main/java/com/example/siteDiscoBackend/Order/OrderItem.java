package com.example.siteDiscoBackend.Order;

import com.example.siteDiscoBackend.Product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name="orderItem")
@Table(name="orderItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int quantity;

    public OrderItem(Order order, Product product, int quantity){
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }
}
