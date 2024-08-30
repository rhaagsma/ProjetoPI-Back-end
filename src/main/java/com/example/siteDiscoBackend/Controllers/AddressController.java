package com.example.siteDiscoBackend.Controllers;

import com.example.siteDiscoBackend.Address.Address;
import com.example.siteDiscoBackend.Address.AddressRepository;
import com.example.siteDiscoBackend.Address.AddressRequestDTO;
import com.example.siteDiscoBackend.Address.AddressResponseDTO;
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
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressRepository repository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Object> saveAddress(@RequestBody @Valid AddressRequestDTO data){
        User user = userRepository.findById(data.user())
                .orElseThrow(() -> new RuntimeException("User Not Found With Id: " + data.user()));
        Address orderData = new Address(data, user);

        repository.save(orderData);

        return ResponseEntity.status(HttpStatus.OK).body("Address Saved Sucessfully!");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses(){
        List<AddressResponseDTO> list = repository.findAll().stream().map(AddressResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAddress(@PathVariable(value="id") UUID id){
        Optional<Address> addressFound = repository.findById(id);

        if(addressFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
        }

        Address order  = addressFound.get();
        AddressResponseDTO addressRequestDTO = new AddressResponseDTO(order);

        return ResponseEntity.status(HttpStatus.OK).body(addressRequestDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUserAddress(@PathVariable(value="id") UUID id){
        Optional<User> userFound = userRepository.findById(id);

        if(userFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userFound.get();
        List<Address> addresses = repository.findByUser(user);

        List<AddressResponseDTO> addressResponseDTOS = addresses.stream().map(AddressResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(addressResponseDTOS);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAddress(@PathVariable(value="id") UUID id,
                                              @RequestBody @Valid AddressRequestDTO addressRequest){
        Optional<Address> addressFound = repository.findById(id);

        if(addressFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
        }

        User user = userRepository.findById(addressRequest.user())
                .orElseThrow(() -> new RuntimeException("User Not Found With Id: " + addressRequest.user()));

        var address = addressFound.get();

        BeanUtils.copyProperties(addressRequest, address);

        address.setUser(user);

        repository.save(address);

        return ResponseEntity.status(HttpStatus.OK).body(getAddress(id));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAddress(@PathVariable(value="id") UUID id){
        Optional<Address> addressFound = repository.findById(id);

        if(addressFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
        }

        repository.delete(addressFound.get());

        return ResponseEntity.status(HttpStatus.OK).body("Address Deleted Sucessfully!");
    }
}
