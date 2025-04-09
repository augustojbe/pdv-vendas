package io.github.pdv.sistema.pdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDto {

    private long userid;

    List<ProductSaleDto> items;

}
