package com.example.siteDiscoBackend.Order;

import com.example.siteDiscoBackend.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Order findByUser(User user);
}
