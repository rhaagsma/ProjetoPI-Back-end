package com.example.siteDiscoBackend.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddressResponseDTO(UUID id, UUID user, String street,
                                 int number, String city, String CEP,
                                 String complement, String neighbourhood, String state,
                                 String country) {
    public AddressResponseDTO(Address address){
        this(address.getId(), address.getUser() != null ? address.getUser().getId() : null,
                address.getStreet(), address.getNumber(), address.getCity(), address.getCEP(),
                address.getComplement(), address.getNeighbourhood(), address.getState(), address.getCountry());
    }
}
