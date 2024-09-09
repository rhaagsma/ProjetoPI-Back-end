package com.example.siteDiscoBackend.Address;

import com.example.siteDiscoBackend.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name="addresses")
@Entity(name="addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    //isso aqui ta horrivel mas to sem tempo de otimizar

    private String street;
    private int number;
    private String city;
    private String CEP;
    private String complement;
    private String neighbourhood;
    private String state;
    private String country;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Address(AddressRequestDTO data, User user){
        this.name = data.name();
        this.street = data.street();
        this.number = data.number();
        this.city = data.city();
        this.CEP = data.CEP();
        this.complement = data.complement();
        this.neighbourhood = data.neighbourhood();
        this.state = data.state();
        this.country = data.country();

        this.user = user;
    }
}
