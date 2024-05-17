package com.example.siteDiscoBackend.Controllers;

import com.example.siteDiscoBackend.Category.Category;
import com.example.siteDiscoBackend.Category.CategoryRepository;
import com.example.siteDiscoBackend.Category.CategoryRequestDTO;
import com.example.siteDiscoBackend.Category.CategoryResponseDTO;
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
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Object> saveCategory(@RequestBody @Valid CategoryRequestDTO data){
        Category categoryData = new Category(data);
        repository.save(categoryData);

        return ResponseEntity.status(HttpStatus.CREATED).body("Category Saved Sucessfully!!");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<CategoryResponseDTO> list = repository.findAll().stream().map(CategoryResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategorie(@PathVariable(value="id") UUID id){
        Optional<Category> categoryFound = repository.findById(id);

        if(categoryFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category Not Found");
        }

        Category category = categoryFound.get();
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category);

        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTO);
    }



    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable(value="id") UUID id,
                                                 @RequestBody @Valid CategoryRequestDTO categoryRequest){
        Optional<Category> categoryFound = repository.findById(id);

        if(categoryFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category Not Found");
        }

        var category = categoryFound.get();
        BeanUtils.copyProperties(categoryRequest, category);

        return ResponseEntity.status(HttpStatus.OK).body(repository.save(category));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value="id") UUID id){
        Optional<Category> categoryFound = repository.findById(id);

        if(categoryFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category Not Found");
        }

        repository.delete(categoryFound.get());

        return ResponseEntity.status(HttpStatus.OK).body("Category Deleted Sucessfully!!");
    }
}
