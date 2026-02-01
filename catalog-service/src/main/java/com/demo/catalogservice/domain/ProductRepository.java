package com.demo.catalogservice.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<Product, Long> {
    // find by code
    Optional<Product> findByCode(String code);
}
