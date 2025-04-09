package io.github.pdv.sistema.pdv.controller;

import io.github.pdv.sistema.pdv.dto.ResponseDto;
import io.github.pdv.sistema.pdv.dto.UserDto;
import io.github.pdv.sistema.pdv.entity.User;
import io.github.pdv.sistema.pdv.exeception.NoItemException;
import io.github.pdv.sistema.pdv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity getAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody UserDto user){
        try {
            user.setEnabled(true);
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);

        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody UserDto user){

       try {
           return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
       }catch (Exception ex){
           return new ResponseEntity<>(new ResponseDto(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id){
        try {

            userService.deleteById(id);
            return new ResponseEntity<>(new ResponseDto("Usuario deletado com sucesso"), HttpStatus.OK);

        } catch (EmptyResultDataAccessException e){
            return new ResponseEntity(new ResponseDto("Não foi posivel localizar o usuário!"), HttpStatus.BAD_REQUEST);

        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
