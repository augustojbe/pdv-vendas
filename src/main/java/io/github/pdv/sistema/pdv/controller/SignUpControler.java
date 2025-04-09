package io.github.pdv.sistema.pdv.controller;

import io.github.pdv.sistema.pdv.dto.UserDto;
import io.github.pdv.sistema.pdv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/sign-up")
public class SignUpControler {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody UserDto user){
        try {
            user.setEnabled(true);
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);

        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }



}
