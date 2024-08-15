package com.example.siteDiscoBackend.Controllers;

import com.example.siteDiscoBackend.Order.Order;
import com.example.siteDiscoBackend.Order.OrderRepository;
import com.example.siteDiscoBackend.Order.OrderRequestDTO;
import com.example.siteDiscoBackend.Order.OrderResponseDTO;
import com.example.siteDiscoBackend.User.User;
import com.example.siteDiscoBackend.User.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderRepository repository;

    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Object> saveOrder(@RequestBody @Valid OrderRequestDTO data){
        User user = userRepository.findById(data.user())
                    .orElseThrow(() -> new RuntimeException("User Not Found With Id: " + data.user()));
        Order orderData = new Order(data, user);

        repository.save(orderData);

        return ResponseEntity.status(HttpStatus.OK).body("Order Saved Sucessfully!");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        List<OrderResponseDTO> list = repository.findAll().stream().map(OrderResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrder(@PathVariable(value="id") UUID id){
        Optional<Order> orderFound = repository.findById(id);

        if(orderFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        Order order  = orderFound.get();
        OrderResponseDTO orderRequestDTO = new OrderResponseDTO(order);

        return ResponseEntity.status(HttpStatus.OK).body(orderRequestDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable(value="id") UUID id,
                                              @RequestBody @Valid OrderRequestDTO orderRequest){
        Optional<Order> orderFound = repository.findById(id);

        if(orderFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        User user = userRepository.findById(orderRequest.user())
                .orElseThrow(() -> new RuntimeException("User Not Found With Id: " + orderRequest.user()));

        var order = orderFound.get();
        BeanUtils.copyProperties(orderRequest, order);

        order.setUser(user);

        repository.save(order);

        return ResponseEntity.status(HttpStatus.OK).body(getOrder(id));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> findUserOrders(@PathVariable(value="id") UUID id){
        Optional<Order> orderFound = repository.findById(id);

        if(orderFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        repository.delete(orderFound.get());

        return ResponseEntity.status(HttpStatus.OK).body("Order Deleted Sucessfully!");
    }
}
