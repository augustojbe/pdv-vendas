package io.github.pdv.sistema.pdv.controller;

import io.github.pdv.sistema.pdv.dto.LoginDto;
import io.github.pdv.sistema.pdv.dto.ResponseDto;
import io.github.pdv.sistema.pdv.dto.TokenDto;
import io.github.pdv.sistema.pdv.security.CustomUserDetailService;
import io.github.pdv.sistema.pdv.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/login")
public class LoginController {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JwtService jwtService;

    @Value("${security.jwt.expiration}")
    private String expiration;

    @PostMapping
    public ResponseEntity post(@Valid @RequestBody LoginDto loginDto){

        try {
            userDetailService.verifyUserCredencials(loginDto);

            String token = jwtService.genereteToken(loginDto.getUsername());

            return new ResponseEntity<>(new TokenDto(token, expiration), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(new ResponseDto<>(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }

    }
}
