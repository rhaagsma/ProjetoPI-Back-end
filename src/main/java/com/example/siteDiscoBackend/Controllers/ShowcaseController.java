package com.example.siteDiscoBackend.Controllers;

import com.example.siteDiscoBackend.Product.Product;
import com.example.siteDiscoBackend.Product.ProductRepository;
import com.example.siteDiscoBackend.Showcase.Showcase;
import com.example.siteDiscoBackend.Showcase.ShowcaseRepository;
import com.example.siteDiscoBackend.Showcase.ShowcaseRequestDTO;
import com.example.siteDiscoBackend.Showcase.ShowcaseResponseDTO;
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
@RequestMapping("/showcase")
public class ShowcaseController {
    @Autowired
    private ShowcaseRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Object> saveShowcase(@RequestBody @Valid ShowcaseRequestDTO data){
        Showcase showcaseData = new Showcase(data);

        Set<Product> products = data.products().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product Not found with Id: " + productId)))
                .collect(Collectors.toSet());

        showcaseData.setProducts(products);
        repository.save(showcaseData);

        return ResponseEntity.status(HttpStatus.OK).body("Showcase Saved Sucesfully!!");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<ShowcaseResponseDTO>> getAllShowcases(){
        List<ShowcaseResponseDTO> showcases = repository.findAll().stream().map(ShowcaseResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(showcases);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getShowcase(@PathVariable(value = "id") UUID id){
        Optional<Showcase> showcaseFound = repository.findById(id);

        if(showcaseFound.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Showcase Not Found");

        Showcase showcase = showcaseFound.get();
        ShowcaseResponseDTO showcaseResponseDTO = new ShowcaseResponseDTO(showcase);

        return ResponseEntity.status(HttpStatus.OK).body(showcaseResponseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateShowcase(@PathVariable(value = "id") UUID id,
                                                 @RequestBody @Valid ShowcaseRequestDTO data){
        Optional<Showcase> showcaseFound = repository.findById(id);

        if(showcaseFound.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Showcase Not Found");

        Set<Product> products = data.products().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product Not found with Id: " + productId)))
                .collect(Collectors.toSet());
        var showcase = showcaseFound.get();

        BeanUtils.copyProperties(data, showcase);

        showcase.setProducts(products);
        repository.save(showcase);

        return ResponseEntity.status(HttpStatus.OK).body(getShowcase(id));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteShowcase(@PathVariable(value = "id") UUID id){
        Optional<Showcase> showcaseFound = repository.findById(id);

        if(showcaseFound.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Showcase Not Found");

        repository.delete(showcaseFound.get());

        return ResponseEntity.status(HttpStatus.OK).body("Showcase Deleted Sucessfully!!");
    }
}
