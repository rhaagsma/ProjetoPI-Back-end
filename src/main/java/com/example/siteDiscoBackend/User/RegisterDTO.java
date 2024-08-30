package com.example.siteDiscoBackend.User;

import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotNull String login, @NotNull String password, String email, String telephone) {
}
