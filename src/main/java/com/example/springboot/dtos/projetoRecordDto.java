package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.springboot.models.usuarioModel;
import com.example.springboot.models.categoriaModel;
import com.example.springboot.models.cenaModel;

public record projetoRecordDto(@NotBlank @Size(min=2) String nome, usuarioModel usuario, categoriaModel categoria, cenaModel cenaInicial) {
}
