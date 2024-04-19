package com.example.springboot.repositories;

import com.example.springboot.models.usuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface usuarioRepository extends JpaRepository<usuarioModel, UUID> {//repositorio spring (contém funções para manipulações de dados no BD)
    //save, getAll, getById, put, ...
}
