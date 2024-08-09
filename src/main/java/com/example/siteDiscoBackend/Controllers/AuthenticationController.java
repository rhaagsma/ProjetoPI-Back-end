package com.example.siteDiscoBackend.Controllers;

import com.example.siteDiscoBackend.Infra.Security.TokenService;
import com.example.siteDiscoBackend.User.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Already in Use");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, UserRole.CLIENT);

        repository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body("Sucessfully registered");
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<Object> registerAdmin(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login Already in Use");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, UserRole.ADMIN);

        repository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body("Sucessfully registered");
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }
}
