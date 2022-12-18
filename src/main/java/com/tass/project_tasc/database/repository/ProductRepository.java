package com.tass.project_tasc.database.repository;

import com.tass.project_tasc.database.entities.Product;
import com.tass.project_tasc.database.entities.myenums.ProductStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByStatus(ProductStatus status);
    Optional<Product> findByBarcode(String barcode);

}
