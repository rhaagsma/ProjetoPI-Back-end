package com.example.springboot.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "projetos")
public class projetoModel extends RepresentationModel<projetoModel> implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID idProjeto;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoriaId")
    private categoriaModel categoria;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private usuarioModel usuario;

    @OneToOne
    @JoinColumn(name="cenaInicialId")
    private cenaModel cenaInicial;

    public UUID getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(UUID idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public categoriaModel getCategoria() {
        return categoria;
    }

    public void setCategoria(categoriaModel categoria) {
        this.categoria = categoria;
    }

    public usuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(usuarioModel usuario) {
        this.usuario = usuario;
    }

    public cenaModel getCenaInicial() {
        return cenaInicial;
    }

    public void setCenaInicial(cenaModel cenaInicial) {
        this.cenaInicial = cenaInicial;
    }
}
