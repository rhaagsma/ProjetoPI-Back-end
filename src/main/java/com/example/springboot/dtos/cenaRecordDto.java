package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.springboot.models.cenaModel;
import com.example.springboot.models.projetoModel;

public record cenaRecordDto(@NotBlank @Size(min=2) String nome, projetoModel projeto, cenaModel escolha1, cenaModel escolha2) {
}
