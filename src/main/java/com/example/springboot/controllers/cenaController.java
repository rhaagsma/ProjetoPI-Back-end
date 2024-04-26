package com.example.springboot.controllers;

import com.example.springboot.dtos.cenaRecordDto;
import com.example.springboot.models.cenaModel;
import com.example.springboot.repositories.CenaRepository;

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
public class cenaController {

    @Autowired
    CenaRepository cenaRepository;

    @PostMapping("/cenas")
    public ResponseEntity<cenaModel> saveCena(@RequestBody @Valid cenaRecordDto cenaRecordDto){
        var cenaModel = new cenaModel();
        BeanUtils.copyProperties(cenaRecordDto, cenaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(cenaRepository.save(cenaModel));
    }

    @GetMapping("/cenas")
    public ResponseEntity<List<cenaModel>> getAllCenas(){
        List<cenaModel> cenasList = cenaRepository.findAll();
        if(!cenasList.isEmpty()){
            for(cenaModel cena : cenasList){
                UUID id = cena.getIdCena();
                cena.add(linkTo(methodOn(cenaController.class).getOneCena(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(cenasList);
    }

    @GetMapping("/cenas/{id}")
    public ResponseEntity<Object> getOneCena(@PathVariable(value="id") UUID id){
        Optional<cenaModel> cenaFound = cenaRepository.findById(id);
        if(cenaFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cena não encontrada");
        }
        cenaFound.get().add(linkTo(methodOn(cenaController.class).getAllCenas()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(cenaFound.get());
    }

    @PutMapping("/cenas/{id}")
    public ResponseEntity<Object> updateCena(@PathVariable(value="id") UUID id,
                                             @RequestBody @Valid cenaRecordDto cenaRecordDto){
        Optional<cenaModel> cenaFound = cenaRepository.findById(id);
        if(cenaFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cena não encontrada");
        }
        var cenaModel = cenaFound.get();
        BeanUtils.copyProperties(cenaRecordDto, cenaModel);
        return ResponseEntity.status(HttpStatus.OK).body(cenaRepository.save(cenaModel));
    }

    @DeleteMapping("/cenas/{id}")
    public ResponseEntity<Object> deleteCena(@PathVariable(value="id") UUID id){
        Optional<cenaModel> cenaFound = cenaRepository.findById(id);
        if(cenaFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cena não encontrada");
        }
        cenaRepository.delete(cenaFound.get());
        return ResponseEntity.status(HttpStatus.OK).body("Cena Deletada com sucesso");
    }
}
