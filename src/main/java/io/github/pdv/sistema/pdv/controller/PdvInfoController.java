package io.github.pdv.sistema.pdv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/info")
public class PdvInfoController {

    @GetMapping
    public ResponseEntity get(){
        return new ResponseEntity<>("PDV Info", HttpStatus.OK);
    }
}
