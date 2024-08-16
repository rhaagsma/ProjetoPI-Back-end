package com.example.siteDiscoBackend.User;

import java.util.UUID;

public record LoginResponseDTO(String token, UUID id) {
}
