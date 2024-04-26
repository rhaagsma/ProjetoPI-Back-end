package com.example.springboot.controllers;

import com.example.springboot.dtos.projetoRecordDto;
import com.example.springboot.models.projetoModel;
import com.example.springboot.repositories.ProjetoRepository;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class projetoController {

    @Autowired
    ProjetoRepository projetoRepository;

    @PostMapping("/projetos")
    public ResponseEntity<projetoModel> saveProjeto(@RequestBody @Valid projetoRecordDto projetoRecordDto){
        var projetoModel = new projetoModel();
        BeanUtils.copyProperties(projetoRecordDto, projetoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(projetoRepository.save(projetoModel));
    }

    @GetMapping("/projetos")
    public ResponseEntity<List<projetoModel>> getAllProjetos(){
        List<projetoModel> projetosList = projetoRepository.findAll();
        if(!projetosList.isEmpty()){
            for(projetoModel projeto : projetosList){
                UUID id = projeto.getIdProjeto();
                projeto.add(linkTo(methodOn(projetoController.class).getOneProjeto(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(projetosList);
    }

    @GetMapping("/projetos/{id}")
    public ResponseEntity<Object> getOneProjeto(@PathVariable(value="id") UUID id){
        Optional<projetoModel> projetoFound = projetoRepository.findById(id);
        if(projetoFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        }
        projetoFound.get().add(linkTo(methodOn(projetoController.class).getAllProjetos()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(projetoFound.get());
    }

    @PutMapping("/projetos/{id}")
    public ResponseEntity<Object> updateProjeto(@PathVariable(value="id") UUID id,
                                                  @RequestBody @Valid projetoRecordDto projetoRecordDto){
        Optional<projetoModel> projetoFound = projetoRepository.findById(id);
        if(projetoFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        }
        var projetoModel = projetoFound.get();
        BeanUtils.copyProperties(projetoRecordDto, projetoModel);
        return ResponseEntity.status(HttpStatus.OK).body(projetoRepository.save(projetoModel));
    }

    @DeleteMapping("/projetos/{id}")
    public ResponseEntity<Object> deleteProjeto(@PathVariable(value="id") UUID id){
        Optional<projetoModel> projetoFound = projetoRepository.findById(id);
        if(projetoFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        }
        projetoRepository.delete(projetoFound.get());
        return ResponseEntity.status(HttpStatus.OK).body("Projeto Deletado com sucesso");
    }
}
