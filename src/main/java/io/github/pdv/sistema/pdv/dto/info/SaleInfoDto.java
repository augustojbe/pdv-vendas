package io.github.pdv.sistema.pdv.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleInfoDto {

    private String user;

    private String date;

    private BigDecimal total;

    private List<ProductoInfoDto> product;
}
