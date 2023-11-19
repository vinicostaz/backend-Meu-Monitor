package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RestController
@RequestMapping("food")
public class FoodController {

    @Autowired
    private FoodRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveFood(@RequestBody FoodRequestDTO data){
        Food foodData = new Food(data);
        repository.save(foodData);
        return;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<FoodResponseDTO> getAll(){

        List<FoodResponseDTO> foodList = repository.findAll().stream().map(FoodResponseDTO::new).toList();
        return foodList;
    }
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable Long id) {
        repository.deleteById(id);
    }
        
        @CrossOrigin(origins = "*", allowedHeaders = "*")
        @PutMapping("/{id}")
        public ResponseEntity<Void> updateFood(@PathVariable Long id, @RequestBody FoodRequestDTO data) {
            // Verifique se o ID fornecido existe antes de atualizar
            if (!repository.existsById(id)) {
                // Pode lançar uma exceção ou retornar um código de resposta indicando que o recurso não foi encontrado
                // Aqui, estou usando um exemplo simples retornando um código 404 Not Found
                throw new ResourceNotFoundException("Food with id " + id + " not found");
            }

            Food existingFood = repository.getById(id);
            existingFood.setTitle(data.title());
            existingFood.setImage(data.image());
            existingFood.setPrice(data.price());

            repository.save(existingFood);

            return ResponseEntity.noContent().build();
        }
        
}