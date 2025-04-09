package io.github.pdv.sistema.pdv.service;

import io.github.pdv.sistema.pdv.dto.ProductSaleDto;
import io.github.pdv.sistema.pdv.dto.SaleDto;
import io.github.pdv.sistema.pdv.dto.info.ProductoInfoDto;
import io.github.pdv.sistema.pdv.dto.info.SaleInfoDto;
import io.github.pdv.sistema.pdv.entity.ItemSale;
import io.github.pdv.sistema.pdv.entity.Product;
import io.github.pdv.sistema.pdv.entity.Sale;
import io.github.pdv.sistema.pdv.entity.User;
import io.github.pdv.sistema.pdv.exeception.InvalidOperationException;
import io.github.pdv.sistema.pdv.exeception.NoItemException;
import io.github.pdv.sistema.pdv.repository.ItemSaleRepository;
import io.github.pdv.sistema.pdv.repository.ProductRepository;
import io.github.pdv.sistema.pdv.repository.SaleRepository;
import io.github.pdv.sistema.pdv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final SaleRepository saleRepository;

    private final ItemSaleRepository itemSaleRepository;

    public List<SaleInfoDto> findAll(){
        return saleRepository.findAll().stream()
                .map(sale -> getSaleInfo(sale))
                .collect(Collectors.toList());
    }

    private SaleInfoDto getSaleInfo(Sale sale) {

        var products = getProductInfo(sale.getItems());
        BigDecimal total = getTotal(products);

        return SaleInfoDto.builder()
                .user(sale.getUser().getName())
                .date(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .product(products)
                .total(total)
                .build();
    }

    private BigDecimal getTotal(List<ProductoInfoDto> products) {
        BigDecimal total = new BigDecimal(0);

        for (int i=0; i < products.size(); i++){
            ProductoInfoDto currentProduct = products.get(i);
            total = total.add(products.get(i).getPrice()
                    .multiply(new BigDecimal(currentProduct.getQuantity())));
        }
        return total;
    }

    private List<ProductoInfoDto> getProductInfo(List<ItemSale> items) {

        if (CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }
        return items.stream().map(
                        item -> ProductoInfoDto
                                .builder()
                                .id(item.getId())
                                .description(item.getProduct().getDescription())
                                .quantity(item.getQuantity())
                                .price(item.getProduct().getPrice())
                                .build()
                 ).collect(Collectors.toList());
    }

    @Transactional
    public long save(SaleDto sale) throws Exception {
        User user = userRepository.findById(sale.getUserid())
                .orElseThrow(() -> new NoItemException("Usuario não encontrado"));

        Sale newSale = new Sale();
        newSale.setUser(user);
        newSale.setDate(LocalDateTime.now());
        List<ItemSale> itemSales = getItemSale(sale.getItems());

       newSale = saleRepository.save(newSale);

       saveItemSale(itemSales, newSale);

       return newSale.getId();
    }

    private void saveItemSale(List<ItemSale> items, Sale newSale) {
        for (ItemSale item: items){
            item.setSale(newSale);
            itemSaleRepository.save(item);
        }
    }

    private List<ItemSale> getItemSale(List<ProductSaleDto> productSaleDtos) throws Exception{

        if(productSaleDtos.isEmpty()){
            throw new InvalidOperationException("Não é possivel adiciobar a venda sem item");
        }

        return productSaleDtos
                .stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductid()).orElseThrow(
                            () -> new NoItemException("Item de venda não encontrado"));

                    ItemSale itemSale = new ItemSale();
                    itemSale.setProduct(product);
                    itemSale.setQuantity(item.getQuantity());

                    if(product.getQuantity() == 0){
                        throw new NoItemException("Produto sem estoque");
                    } else if(product.getQuantity() < item.getQuantity()){
                        throw new InvalidOperationException(String.format("A quantidade de itens da vendas (%s) " +
                                "é maior do que a quantidade disponivel no estoque (%s)", item.getQuantity(), product.getQuantity()));
                    }

                    int total = product.getQuantity() - item.getQuantity();
                    product.setQuantity(total);
                    productRepository.save(product);

                    return itemSale;

                }).collect(Collectors.toList());

    }


    public SaleInfoDto getById(long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new NoItemException("Venda não encontrada"));
        return getSaleInfo(sale);
    }
}
