package com.example.siteDiscoBackend.Order;

import com.example.siteDiscoBackend.User.User;
import jakarta.persistence.*;
import lombok.*;

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

    //algum atributo para os produtos

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(OrderRequestDTO data, User user){
        this.user = user;
    }
}
