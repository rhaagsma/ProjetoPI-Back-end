package com.example.siteDiscoBackend.Controllers;

import com.example.siteDiscoBackend.Address.Address;
import com.example.siteDiscoBackend.Address.AddressRepository;
import com.example.siteDiscoBackend.Order.*;
import com.example.siteDiscoBackend.Product.Product;
import com.example.siteDiscoBackend.Product.ProductRepository;
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

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AddressRepository addressRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Object> saveOrder(@RequestBody @Valid OrderRequestDTO data){
        User user = userRepository.findById(data.user())
                    .orElseThrow(() -> new RuntimeException("User Not Found With Id: " + data.user()));

        Address address = addressRepository.findById(data.address())
                .orElseThrow(() -> new RuntimeException("Address Not Found With Id: " + data.address()));

        Order orderData = new Order(data, user, address);

        for (OrderItemDTO orderProduct : data.items()) {
            Product product = productRepository.findById(orderProduct.productId())
                    .orElseThrow(() -> new RuntimeException("Product Not Found With Id: " + orderProduct.productId()));
            orderData.addProduct(product, orderProduct.quantity());
        }

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
    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUserOrders(@PathVariable(value="id") UUID id){
        Optional<User> userFound = userRepository.findById(id);

        if(userFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userFound.get();
        List<Order> orders = repository.findByUser(user);

        List<OrderResponseDTO> orderResponseDTOS = orders.stream().map(OrderResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTOS);
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

        Address address = addressRepository.findById(orderRequest.address())
                .orElseThrow(() -> new RuntimeException("Address Not Found With Id: " + orderRequest.address()));

        var order = orderFound.get();

        order.setUser(user);
        order.setAddress(address);

        order.getItems().clear();

        for (OrderItemDTO orderItemDTO : orderRequest.items()) {
            Product product = productRepository.findById(orderItemDTO.productId())
                    .orElseThrow(() -> new RuntimeException("Product Not Found With Id: " + orderItemDTO.productId()));
            order.addProduct(product, orderItemDTO.quantity());
        }

        repository.save(order);

        return ResponseEntity.status(HttpStatus.OK).body(getOrder(id));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable(value="id") UUID id){
        Optional<Order> orderFound = repository.findById(id);

        if(orderFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        repository.delete(orderFound.get());

        return ResponseEntity.status(HttpStatus.OK).body("Order Deleted Sucessfully!");
    }
}
