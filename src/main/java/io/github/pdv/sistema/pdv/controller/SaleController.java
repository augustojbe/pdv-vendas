package io.github.pdv.sistema.pdv.controller;

import io.github.pdv.sistema.pdv.dto.ResponseDto;
import io.github.pdv.sistema.pdv.dto.SaleDto;
import io.github.pdv.sistema.pdv.exeception.InvalidOperationException;
import io.github.pdv.sistema.pdv.exeception.NoItemException;
import io.github.pdv.sistema.pdv.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/v1/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public ResponseEntity getAll(){
        return new ResponseEntity<>(saleService.findAll(), HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") long id){
        try {
            return new ResponseEntity<>(saleService.getById(id), HttpStatus.OK);

        } catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping
    public ResponseEntity created(@Valid @RequestBody SaleDto saleDto){
        try {
            saleService.save(saleDto);
            return new ResponseEntity<>(new ResponseDto("Venda realizada com sucesso! "), HttpStatus.CREATED);

        } catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
