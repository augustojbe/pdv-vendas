package io.github.pdv.sistema.pdv.controller;

import io.github.pdv.sistema.pdv.dto.ProductDto;
import io.github.pdv.sistema.pdv.dto.ResponseDto;
import io.github.pdv.sistema.pdv.entity.Product;
import io.github.pdv.sistema.pdv.entity.User;
import io.github.pdv.sistema.pdv.repository.ProductRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/v1/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    private ModelMapper mapper = new ModelMapper();


    @GetMapping
    public ResponseEntity getAll(){
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity creatProduct( @Valid @RequestBody ProductDto product){
        try {
            return new ResponseEntity<>(productRepository
                    .save(mapper.map(product, Product.class)), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping()
    public  ResponseEntity productUpdate(@Valid @RequestBody ProductDto product){
        try{
            return new ResponseEntity<>(productRepository.save(mapper.map(product, Product.class)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity productDelete(@PathVariable("id") long id){

        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseDto("Produto removido com sucesso"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
