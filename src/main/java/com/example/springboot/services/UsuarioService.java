package com.example.springboot.services;

import com.example.springboot.dtos.usuarioRecordDto;
import com.example.springboot.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import com.example.springboot.models.usuarioModel;
import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public usuarioModel createUser(usuarioRecordDto usuarioRecordDto) {


        usuarioModel usuarioModel = new usuarioModel();

        usuarioModel.setNome(usuarioRecordDto.nome());
        usuarioModel.setEmail(usuarioRecordDto.email());
        usuarioModel.setSenha(usuarioRecordDto.senha());

        return usuarioRepository.save(usuarioModel);
        }
    public List<usuarioModel> getAllUsers(){
        return usuarioRepository.findAll();
    }

    public Optional<usuarioModel> findBynome(String nome){
        return usuarioRepository.findBynome(nome);
    }

}
