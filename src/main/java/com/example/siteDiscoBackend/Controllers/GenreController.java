package com.example.siteDiscoBackend.Controllers;

import com.example.siteDiscoBackend.Genre.Genre;
import com.example.siteDiscoBackend.Genre.GenreRepository;
import com.example.siteDiscoBackend.Genre.GenreRequestDTO;
import com.example.siteDiscoBackend.Genre.GenreResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("genre")
public class GenreController {
    @Autowired
    private GenreRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Object> saveGenre(@RequestBody GenreRequestDTO data){
        Genre genreData = new Genre(data);
        repository.save(genreData);

        return ResponseEntity.status(HttpStatus.CREATED).body("Genre Saved Sucessfully!!");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> getAllGenres(){
        List<GenreResponseDTO> list = repository.findAll().stream().map(GenreResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getGenre(@PathVariable(value="id") UUID id){
        Optional<Genre> genreFound = repository.findById(id);

        if(genreFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genre Not Found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(genreFound);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateGenre(@PathVariable(value="id") UUID id,
                                              @RequestBody @Valid GenreRequestDTO genreRequest){
        Optional<Genre> genreFound = repository.findById(id);

        if(genreFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genre Not Found");
        }

        var genre = genreFound.get();
        BeanUtils.copyProperties(genreRequest, genre);

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(genre));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGenre(@PathVariable(value="id") UUID id){
        Optional<Genre> genreFound = repository.findById(id);

        if(genreFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genre Not Found");
        }

        repository.delete(genreFound.get());

        return ResponseEntity.status(HttpStatus.OK).body("Genre Deleted Sucessfully!");
    }

}
