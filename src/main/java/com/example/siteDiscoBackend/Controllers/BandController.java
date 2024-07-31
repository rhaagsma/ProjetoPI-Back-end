package com.example.siteDiscoBackend.Controllers;

import com.example.siteDiscoBackend.Band.Band;
import com.example.siteDiscoBackend.Band.BandRepository;
import com.example.siteDiscoBackend.Band.BandRequestDTO;
import com.example.siteDiscoBackend.Band.BandResponseDTO;
import com.example.siteDiscoBackend.Genre.Genre;
import com.example.siteDiscoBackend.Genre.GenreRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("band")

public class BandController {
    @Autowired
    private BandRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Object> saveBand(@RequestBody BandRequestDTO data){
        Band bandData = new Band(data);

        // Para cada ID de gênero na solicitação, encontre o gênero correspondente no banco de dados
        Set<Genre> genres = data.genres().stream()
                .map(genreId -> genreRepository.findById(genreId)
                        .orElseThrow(() -> new RuntimeException("Genre not found with ID: " + genreId)))
                .collect(Collectors.toSet());//função para assosiação de band e genre

        bandData.setGenres(genres);//salva os generos
        repository.save(bandData);

        return ResponseEntity.status(HttpStatus.CREATED).body("Band Saved Sucessfully!!");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<BandResponseDTO>> getAllBands(){
        List<BandResponseDTO> list = repository.findAll().stream().map(BandResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBand(@PathVariable(value="id") UUID id){
        Optional<Band> bandFound = repository.findById(id);

        if(bandFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Band Not Found");
        }

        Band band = bandFound.get();
        BandResponseDTO bandResponseDTO = new BandResponseDTO(band);//tratamento para serialização recursiva
        //se retornarmos apenas a variavel optional ou a Band, há serialização recursiva, pois a cada band, retorna os generos, que retorna as bandas, que retorna o genero......
        //para isso, filtra-se as informações relevantes numa varivel ResponseDTO para retornar apenas a banda, sem serializar o generos

        return ResponseEntity.status(HttpStatus.OK).body(bandResponseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBand(@PathVariable(value="id") UUID id,
                                              @RequestBody @Valid BandRequestDTO bandRequest){
        Optional<Band> bandFound = repository.findById(id);

        if(bandFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Band Not Found");
        }

        var band = bandFound.get();
        BeanUtils.copyProperties(bandRequest, band);

        Set<Genre> genres = bandRequest.genres().stream()
                .map(genreId -> genreRepository.findById(genreId)
                        .orElseThrow(() -> new RuntimeException("Genre not found with ID: " + genreId)))
                .collect(Collectors.toSet());//função para assosiação de band e genre

        band.setGenres(genres);

        return ResponseEntity.status(HttpStatus.CREATED).body(getBand(id));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBand(@PathVariable(value="id") UUID id){
        Optional<Band> bandFound = repository.findById(id);

        if(bandFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Band Not Found");
        }

        repository.delete(bandFound.get());

        return ResponseEntity.status(HttpStatus.OK).body("Band Deleted Sucessfully!");
    }
}
