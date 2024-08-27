package com.example.siteDiscoBackend.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AddressRequestDTO(@NotBlank @Size(min=2) String street,
                                @NotNull int number, @NotBlank String city, @NotBlank String CEP,
                                String complement, String neighbourhood, @NotBlank String state,
                                @NotBlank String country,
                                @NotNull UUID user) {
}
