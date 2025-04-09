package io.github.pdv.sistema.pdv.repository;

import io.github.pdv.sistema.pdv.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
