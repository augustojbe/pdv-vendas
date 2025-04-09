package io.github.pdv.sistema.pdv.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoInfoDto {

    private long id;

    private String description;

    private int quantity;

    private BigDecimal price;
}
