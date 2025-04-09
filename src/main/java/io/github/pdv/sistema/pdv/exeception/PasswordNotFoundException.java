package io.github.pdv.sistema.pdv.exeception;

public class PasswordNotFoundException extends RuntimeException{

    public PasswordNotFoundException(String message) {
        super(message);
    }
}
