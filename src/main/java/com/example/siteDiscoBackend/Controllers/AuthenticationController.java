package com.example.siteDiscoBackend.Controllers;

import com.example.siteDiscoBackend.Services.TokenService;
import com.example.siteDiscoBackend.User.*;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("auth")

public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (User) auth.getPrincipal();

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token, user.getId()));//ta horrivel essa solução, mas blz, depois conserto
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Already in Use");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, UserRole.CLIENT);

        repository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body("Sucessfully registered");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/registerAdmin")
    public ResponseEntity<Object> registerAdmin(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Already in Use");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, UserRole.ADMIN);

        repository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body("Sucessfully registered");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable(value="id") UUID id){
        Optional<User> userFound = repository.findById(id);

        if(userFound.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");

        UserResponseDTO userResponseDTO = new UserResponseDTO(userFound.get());

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }



    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value="id") UUID id,
                                             @RequestBody @Valid RegisterDTO data){
        Optional<User> userFound = repository.findById(id);

        if(userFound.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");

        var user = userFound.get();

        BeanUtils.copyProperties(data, user);

        return ResponseEntity.status(HttpStatus.OK).body(repository.save(user));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value="id") UUID id){
        Optional<User> userFound = repository.findById(id);

        if(userFound.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");

        repository.delete(userFound.get());

        return ResponseEntity.status(HttpStatus.OK).body("User Deleted Sucessfully");
    }
}
