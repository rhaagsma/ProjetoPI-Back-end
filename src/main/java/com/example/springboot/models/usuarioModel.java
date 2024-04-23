package com.example.springboot.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "usuarios")//cria tabelas no bd
public class usuarioModel extends RepresentationModel<usuarioModel> implements Serializable {
    //RepresentationModel implementa informações de navegação para qual se segue os principios de uma API RESTful
    //Serializable indica que o objeto pode ser convertido e desconvertido em uma sequencia de bytes, para facilitar a manipulação do mesmo no BD
    private static final long serialVersionUID = 1L;//define tipo de id

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)//automatiza atribuição de id (usuario não manipula seu id)
    private UUID idUsuario;//tipo UUID = tipo presente no framework para manipulação de id
    private String nome;
    private String email;
    private String senha;

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
