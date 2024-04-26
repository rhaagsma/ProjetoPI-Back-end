package com.example.springboot.controllers;

import com.example.springboot.dtos.categoriaRecordDto;
import com.example.springboot.models.categoriaModel;
import com.example.springboot.repositories.CategoriaRepository;

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
public class categoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @PostMapping("/categorias")
    public ResponseEntity<categoriaModel> saveCategoria(@RequestBody @Valid categoriaRecordDto categoriaRecordDto){
        var categoriaModel = new categoriaModel();
        BeanUtils.copyProperties(categoriaRecordDto, categoriaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoriaModel));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<categoriaModel>> getAllCategorias(){
        List<categoriaModel> categoriasList = categoriaRepository.findAll();
        if(!categoriasList.isEmpty()){
            for(categoriaModel categoria : categoriasList){
                UUID id = categoria.getIdCategoria();
                categoria.add(linkTo(methodOn(categoriaController.class).getOneCategoria(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoriasList);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Object> getOneCategoria(@PathVariable(value="id") UUID id){
        Optional<categoriaModel> categoriaFound = categoriaRepository.findById(id);
        if(categoriaFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada");
        }
        categoriaFound.get().add(linkTo(methodOn(categoriaController.class).getAllCategorias()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(categoriaFound.get());
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<Object> updateCategoria(@PathVariable(value="id") UUID id,
                                                  @RequestBody @Valid categoriaRecordDto categoriaRecordDto){
        Optional<categoriaModel> categoriaFound = categoriaRepository.findById(id);
        if(categoriaFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada");
        }
        var categoriaModel = categoriaFound.get();
        BeanUtils.copyProperties(categoriaRecordDto, categoriaModel);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoriaModel));
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Object> deleteCategoria(@PathVariable(value="id") UUID id){
        Optional<categoriaModel> categoriaFound = categoriaRepository.findById(id);
        if(categoriaFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada");
        }
        categoriaRepository.delete(categoriaFound.get());
        return ResponseEntity.status(HttpStatus.OK).body("Categoria Deletada com sucesso");
    }

}
