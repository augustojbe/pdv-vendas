package io.github.pdv.sistema.pdv.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "O campo descrição do produto e obrigatório")
    private String description;

    @NotNull(message = "O campo preço e obrigatório")
    private BigDecimal price;

    @NotNull(message = "A quantidade do produto e obrigatório")
    private int quantity;
}
