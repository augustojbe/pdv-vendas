package io.github.pdv.sistema.pdv.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaleDto {

    @NotNull(message = "O item da venda é obrigtório")
    private long productid;

    @NotNull(message = "A quantidade do produto e obrigatório")
    private int quantity;
}
