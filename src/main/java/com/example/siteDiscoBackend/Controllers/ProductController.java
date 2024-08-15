package com.example.siteDiscoBackend.Controllers;


import com.example.siteDiscoBackend.Band.Band;
import com.example.siteDiscoBackend.Band.BandRepository;
import com.example.siteDiscoBackend.Category.Category;
import com.example.siteDiscoBackend.Category.CategoryRepository;
import com.example.siteDiscoBackend.Product.Product;
import com.example.siteDiscoBackend.Product.ProductRepository;
import com.example.siteDiscoBackend.Product.ProductRequestDTO;
import com.example.siteDiscoBackend.Product.ProductResponseDTO;
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


//adicionar busca por categoria e banda?

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private BandRepository bandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody ProductRequestDTO data){
        Category category = categoryRepository.findById(data.category())
                            .orElseThrow(() -> new RuntimeException("Category Not Found With Id: " + data.category()));
        Product productData = new Product(data, category);

        Set<Band> bands = data.bands().stream()
                .map(bandId -> bandRepository.findById(bandId)
                        .orElseThrow(() -> new RuntimeException("Band not found with ID: " + bandId)))
                .collect(Collectors.toSet());

        productData.setBands(bands);
        repository.save(productData);

        return ResponseEntity.status(HttpStatus.CREATED).body("Product Saved Sucessfully!!");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<ProductResponseDTO> list = repository.findAll().stream().map(ProductResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable(value="id") UUID id){
        Optional<Product> productFound = repository.findById(id);

        if(productFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }

        Product product = productFound.get();
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(product);

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public int getQuantity(@PathVariable(value="id") UUID id){
        Optional<Product> productFound = repository.findById(id);

        if(productFound.isEmpty()){
            return -1;
        }

        return productFound.get().getQuantity();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id,
                                              @RequestBody @Valid ProductRequestDTO productRequest){
        Optional<Product> productFound = repository.findById(id);

        if(productFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }

        Category category = categoryRepository.findById(productRequest.category())
                .orElseThrow(() -> new RuntimeException("Category Not Found With Id: " + productRequest.category()));

        Set<Band> bands = productRequest.bands().stream()
                .map(bandId -> bandRepository.findById(bandId)
                        .orElseThrow(() -> new RuntimeException("Band not found with ID: " + bandId)))
                .collect(Collectors.toSet());

        var product = productFound.get();
        BeanUtils.copyProperties(productRequest, product);

        product.setBands(bands);
        product.setCategory(category);

        repository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(getProduct(id));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id){
        Optional<Product> productFound = repository.findById(id);

        if(productFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }

        repository.delete(productFound.get());

        return ResponseEntity.status(HttpStatus.OK).body("Product Deleted Sucessfully!");
    }


}
