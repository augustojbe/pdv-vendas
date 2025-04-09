package io.github.pdv.sistema.pdv.controller;

import io.github.pdv.sistema.pdv.dto.ResponseDto;
import io.github.pdv.sistema.pdv.exeception.InvalidOperationException;
import io.github.pdv.sistema.pdv.exeception.NoItemException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AplicationAdviceController {

    @ExceptionHandler(NoItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto handleNoItemException(NoItemException ex){
        String msg = ex.getMessage();
        return new ResponseDto(msg);
    }

    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto handleInvalidOperationException(InvalidOperationException ex){
        String msg = ex.getMessage();
        return new ResponseDto(msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<String> erros = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMensage = error.getDefaultMessage();
            erros.add(errorMensage);
        });

        return new ResponseDto<>(erros);

    }

}
