package com.example.springboot.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "cenas")
public class cenaModel extends RepresentationModel<cenaModel> implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID idCena;
    private String nome;

    @ManyToOne
    @JoinColumn(name="projetoId")
    private projetoModel projeto;

    @ManyToOne
    @JoinColumn(name = "escolha1Id")
    private cenaModel escolha1;

    @ManyToOne
    @JoinColumn(name = "escolha2Id")
    private cenaModel escolha2;

    public UUID getIdCena() {
        return idCena;
    }

    public void setIdCena(UUID idCena) {
        this.idCena = idCena;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public projetoModel getProjeto() {
        return projeto;
    }

    public void setProjeto(projetoModel projeto) {
        this.projeto = projeto;
    }

    public cenaModel getEscolha1() {
        return escolha1;
    }

    public void setEscolha1(cenaModel escolha1) {
        this.escolha1 = escolha1;
    }

    public cenaModel getEscolha2() {
        return escolha2;
    }

    public void setEscolha2(cenaModel escolha2) {
        this.escolha2 = escolha2;
    }
}
