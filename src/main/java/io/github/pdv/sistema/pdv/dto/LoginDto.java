package io.github.pdv.sistema.pdv.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "Campo Login é obrigatório")
    private String username;

    @NotBlank(message = "Campo Senha é obrigatório")
    private String password;
}
