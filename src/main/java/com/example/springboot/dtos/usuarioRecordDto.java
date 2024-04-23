package com.example.springboot.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record usuarioRecordDto(@NotBlank @Size(min=2) String nome, @NotBlank @Email String email, @NotBlank String senha) {//data transfer object, forma pelo qual a aplicação irá transitar os dados(de maneira padronizada e mais estável)
}
