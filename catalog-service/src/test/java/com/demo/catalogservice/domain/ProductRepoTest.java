package com.demo.catalogservice.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest(
        properties = {
            "spring.test.database.replace=NONE",
            "spring.datasource.url=jdbc:tc:postgresql:15-alpine:///db",
        })
@Sql("/test-data.sql")
public class ProductRepoTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void ShouldFindByCode() {
        Product product = productRepository.findByCode("P100").orElseThrow();
        assertThat(product.getCode().equals("P100"));
        assertThat(product.getName().equals("The Hunger Games"));
        assertThat(product.getDescription().equals("Winning will make you famous. Losing means certain death..."));
        assertThat(product.getPrice().compareTo(BigDecimal.valueOf(34.0)));
    }
}
