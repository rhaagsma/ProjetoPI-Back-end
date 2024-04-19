package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record categoriaRecordDto(@NotBlank @Size(min=2) String nome) {
}
