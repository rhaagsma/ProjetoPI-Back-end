package com.example.siteDiscoBackend.User;

import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotNull String login, String email, String telephone, @NotNull String password) {
}
