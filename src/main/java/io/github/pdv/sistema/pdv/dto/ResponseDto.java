package io.github.pdv.sistema.pdv.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class ResponseDto<T> {

    private List<String> messages;

    public ResponseDto(List<String> messages) {
        this.messages = messages;
    }

    public ResponseDto(String messages) {
        this.messages = Arrays.asList(messages);
    }



}
